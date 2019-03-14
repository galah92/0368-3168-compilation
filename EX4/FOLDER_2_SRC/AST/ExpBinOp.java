package AST;

import pcomp.*;


public class ExpBinOp extends Exp
{

    public char op;
    public Exp left;
    public Exp right;
    public boolean isIntsExpessions;
    public boolean isStringsExpessions;

    public ExpBinOp(Exp left, Exp right, char op)
    {
        this.left = left;
        this.right = right;
        this.op = op;
    }

    @Override
    public void logGraphviz()
    {
        left.logGraphviz();
        right.logGraphviz();
        logNode(String.format("ExpBinOp\n%s", op));
        logEdge(left);
        logEdge(right);
    }

    @Override
    public Type Semant() throws Exception
    {
        Type t1 = left.Semant();
        Type t2 = right.Semant();
        isStringsExpessions = t1 == Type.STRING && t2 == Type.STRING;

        if (t1 == Type.INT && t2 == Type.INT)
        {
            isIntsExpessions = true;
            return Type.INT;
        }
        else if (op == '+' && isStringsExpessions)
        {
            return Type.STRING;
        }
        else if (op == '=')
        {
            if (t1 == t2) { return Type.INT; }
            if ((t1 == Type.NIL || t2 == Type.NIL))
            {
                if (t1 instanceof TypeClass || t1 instanceof TypeArray) { return Type.INT; }
                if (t2 instanceof TypeClass || t2 instanceof TypeArray) { return Type.INT; }
                throw new SemanticException();
            }
            if (t1 instanceof TypeClass && t1 instanceof TypeClass)
            {
                if (((TypeClass)t1).isInheritingFrom((TypeClass)t2)) { return Type.INT; }
                if (((TypeClass)t2).isInheritingFrom((TypeClass)t1)) { return Type.INT; }
                throw new SemanticException("comparison between classes without inheritance relation");
            }
        }
        throw new SemanticException("invalid binary operation");
    }

    @Override
    public IRReg toIR()
    {
        IRReg leftReg = left.toIR();
        IRReg rightReg = right.toIR();

        if (isStringsExpessions)
        {
            IRReg dst = new IRReg.TempReg();
            switch (op)
            {
            case '+':
                IRReg tempReg = new IRReg.TempReg();
                IRReg charReg = new IRReg.TempReg();
                // strlen for left string
                String strlenLoopLabel = IR.uniqueLabel("strlen_loop");
                String strlenEndLabel = IR.uniqueLabel("strlen_end");
                IR.add(new IR.move(tempReg, leftReg));
                IR.add(new IR.label(strlenLoopLabel));
                IR.add(new IR.lb(charReg, tempReg, 0));
                IR.add(new IR.beq(charReg, IRReg.zero, strlenEndLabel));
                IR.add(new IR.addi(tempReg, tempReg, 1));
                IR.add(new IR.jump(strlenLoopLabel));
                IR.add(new IR.label(strlenEndLabel));
                IR.add(new IR.sub(IRReg.a0, tempReg, leftReg));
                // strlen for right string
                strlenLoopLabel = IR.uniqueLabel("strlen_loop");
                strlenEndLabel = IR.uniqueLabel("strlen_end");
                IR.add(new IR.move(tempReg, rightReg));
                IR.add(new IR.label(strlenLoopLabel));
                IR.add(new IR.lb(charReg, tempReg, 0));
                IR.add(new IR.beq(charReg, IRReg.zero, strlenEndLabel));
                IR.add(new IR.addi(tempReg, tempReg, 1));
                IR.add(new IR.jump(strlenLoopLabel));
                IR.add(new IR.label(strlenEndLabel));
                IR.add(new IR.sub(tempReg, tempReg, rightReg));
                IR.add(new IR.add(IRReg.a0, IRReg.a0, tempReg));
                // malloc
                IR.add(new IR.addi(IRReg.a0, IRReg.a0, 1));  // null terminated
                IR.add(new IR.sbrk());
                IR.add(new IR.move(tempReg, IRReg.v0));
                IR.add(new IR.move(dst, IRReg.v0));
                // copy left string
                String strcpyLoopLabel = IR.uniqueLabel("strcpy_loop");
                String strcpyEndLabel = IR.uniqueLabel("strcpy_end");
                IR.add(new IR.label(strcpyLoopLabel));
                IR.add(new IR.lb(charReg, leftReg, 0));
                IR.add(new IR.beq(charReg, IRReg.zero, strcpyEndLabel));
                IR.add(new IR.sb(charReg, tempReg, 0));
                IR.add(new IR.addi(tempReg, tempReg, 1));
                IR.add(new IR.addi(leftReg, leftReg, 1));
                IR.add(new IR.jump(strcpyLoopLabel));
                IR.add(new IR.label(strcpyEndLabel));
                // copy right string
                strcpyLoopLabel = IR.uniqueLabel("strcpy_loop");
                strcpyEndLabel = IR.uniqueLabel("strcpy_end");
                IR.add(new IR.label(strcpyLoopLabel));
                IR.add(new IR.lb(charReg, rightReg, 0));
                IR.add(new IR.beq(charReg, IRReg.zero, strcpyEndLabel));
                IR.add(new IR.sb(charReg, tempReg, 0));
                IR.add(new IR.addi(tempReg, tempReg, 1));
                IR.add(new IR.addi(rightReg, rightReg, 1));
                IR.add(new IR.jump(strcpyLoopLabel));
                IR.add(new IR.label(strcpyEndLabel));
                // null terminating
                IR.add(new IR.sb(IRReg.zero, tempReg, 0));
                return dst;
            case '=':
                String strcmpLoopLabel = IR.uniqueLabel("strcmp_loop");
                String strcmpFalseLabel = IR.uniqueLabel("strcmp_false");
                String strcmpEndLabel = IR.uniqueLabel("strcmp_end");
                IR.add(new IR.li(dst, 0));  // assume equality
                IR.add(new IR.label(strcmpLoopLabel));
                IRReg leftVal = new IRReg.TempReg();
                IRReg rightVal = new IRReg.TempReg();
                IR.add(new IR.lb(leftVal, leftReg, 0));
                IR.add(new IR.lb(rightVal, rightReg, 0));
                IR.add(new IR.bne(leftVal, rightVal, strcmpFalseLabel));
                IR.add(new IR.beq(leftVal, IRReg.zero, strcmpEndLabel));
                IR.add(new IR.addi(leftReg, leftReg, 1));
                IR.add(new IR.addi(rightReg, rightReg, 1));
                IR.add(new IR.jump(strcmpLoopLabel));
                IR.add(new IR.label(strcmpFalseLabel));
                IR.add(new IR.li(dst, 1));
                IR.add(new IR.label(strcmpEndLabel));
                break;
            }
            return dst;
        }
        else
        {
            switch (op)
            {
            case '+':
                IR.add(new IR.add(leftReg, leftReg, rightReg));
                break;
            case '-':
                IR.add(new IR.sub(leftReg, leftReg, rightReg));
                break;
            case '*':
                IR.add(new IR.mul(leftReg, leftReg, rightReg));
                break;
            case '/':
                IR.add(new IR.beq(rightReg, IRReg.zero, "exit_division_by_zero"));
                IR.add(new IR.div(leftReg, leftReg, rightReg));
                break;
            case '<':
                IR.add(new IR.slt(leftReg, leftReg, rightReg));
                break;
            case '>':
                IR.add(new IR.sub(leftReg, IRReg.zero, leftReg));  // negate
                IR.add(new IR.sub(rightReg, IRReg.zero, rightReg));  // negate
                IR.add(new IR.slt(leftReg, leftReg, rightReg));
                break;
            case '=':
                IR.add(new IR.sub(leftReg, leftReg, rightReg));
                IR.add(new IR.sltu(leftReg, IRReg.zero, leftReg));  // 1 if not equal
                IR.add(new IR.xori(leftReg, leftReg, 1));  // 0 becomes 1, 1 becomes 0
                break;
            }
            if (isIntsExpessions)
            {
                String overflowEndLabel = IR.uniqueLabel("int_underflow_end");
                IR.add(new IR.li(rightReg, -32768));  // -2^15
                IR.add(new IR.bgt(leftReg, rightReg, overflowEndLabel));
                IR.add(new IR.move(leftReg, rightReg));
                IR.add(new IR.label(overflowEndLabel));

                overflowEndLabel = IR.uniqueLabel("int_overflow_end");
                IR.add(new IR.li(rightReg, 32767));  // 2^15 - 1
                IR.add(new IR.blt(leftReg, rightReg, overflowEndLabel));
                IR.add(new IR.move(leftReg, rightReg));
                IR.add(new IR.label(overflowEndLabel));
            }
            return leftReg;
        }
    }

}
