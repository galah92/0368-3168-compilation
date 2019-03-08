package AST;

import pcomp.*;


public class ExpBinOp extends Exp
{

    public char op;
    public Exp left;
    public Exp right;
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
                throw new SemanticException(t1 + ", " + t2);
            }
        }
        throw new SemanticException(t1 + ", " + t2);
    }

    @Override
    public IRReg toIR()
    {
        IRReg leftReg = left.toIR();
        IRReg rightReg = right.toIR();
        IRReg dst = new IRReg.TempReg();

        if (isStringsExpessions)
        {
            switch (op)
            {
            case '+':
                System.out.println("Strings concatination not supported yet");
                break;
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
        }
        else
        {
            switch (op)
            {
            case '+':
                IR.add(new IR.add(dst, leftReg, rightReg));
                break;
            case '-':
                IR.add(new IR.sub(dst, leftReg, rightReg));
                break;
            case '*':
                IR.add(new IR.mul(dst, leftReg, rightReg));
                break;
            case '/':
                IR.add(new IR.div(dst, leftReg, rightReg));
                break;
            case '<':
                String ltEndLabel = IR.uniqueLabel("lt_end");
                IR.add(new IR.li(dst, 1));
                IR.add(new IR.blt(leftReg, rightReg, ltEndLabel));
                IR.add(new IR.li(dst, 0));
                IR.add(new IR.label(ltEndLabel));
                break;
                // implementation without dst register - we utilize leftReg
                // String ltTrueLabel = IR.uniqueLabel("lt_true");
                // String ltEndLabel = IR.uniqueLabel("lt_end");
                // IR.add(new IR.blt(leftReg, rightReg, ltTrueLabel));
                // IR.add(new IR.li(leftReg, 0));
                // IR.add(new IR.jump(ltEndLabel));
                // IR.add(new IR.label(ltTrueLabel));
                // IR.add(new IR.li(leftReg, 1));
                // IR.add(new IR.label(ltEndLabel));
                // return leftReg;
            case '>':
                String gtEndLabel = IR.uniqueLabel("gt_end");
                IR.add(new IR.li(dst, 1));
                IR.add(new IR.bgt(leftReg, rightReg, gtEndLabel));
                IR.add(new IR.li(dst, 0));
                IR.add(new IR.label(gtEndLabel));
                break;
            case '=':
                String eqEndLabel = IR.uniqueLabel("eq_end");
                IR.add(new IR.li(dst, 1));
                IR.add(new IR.beq(leftReg, rightReg, eqEndLabel));
                IR.add(new IR.li(dst, 0));
                IR.add(new IR.label(eqEndLabel));
                break;
            }
        }
        return dst;
    }

}
