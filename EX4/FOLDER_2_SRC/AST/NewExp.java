package AST;

import pcomp.*;


public class NewExp extends Exp
{

    String typeName;
    Exp exp;
    int numMembers;
    TypeClass classType;

    public NewExp(String typeName, Exp exp)
    {
		this.typeName = typeName;
        this.exp = exp;
    }

    @Override
    public void logGraphviz()
	{
        if (exp != null) exp.logGraphviz();
		logNode(String.format("NewExp\n%s", typeName));
        if (exp != null) logEdge(exp);
	}

    @Override
	public Type Semant() throws Exception
	{
        Type newExpType = SymbolTable.find(typeName);
        if (newExpType == null) { throw new SemanticException("symbol is not a valid type: " + typeName); }

        if (exp != null) // new array
        {
            if (exp.Semant() != Type.INT) { throw new SemanticException("subscript expression is not of type int"); }
        }
        else // new class
        {
            if (newExpType == Type.INT || newExpType == Type.STRING) { throw new SemanticException(); }
            classType = (TypeClass)newExpType;
            numMembers = ((TypeClass)newExpType).members.size();
        }

        return newExpType;
	}

    @Override
	public IRReg toIR()
	{
        if (exp != null)  // array instance
        {
            IRReg sizeReg = exp.toIR();
            IR.add(new IR.move(IRReg.a0, sizeReg));  // copy array sizez
            IR.add(new IR.addi(IRReg.a0, IRReg.a0, 1));  // first element is size
            IR.add(new IR.sll(IRReg.a0, IRReg.a0, 4));  // convert to size in bytes
            IR.add(new IR.sbrk());  // allocate heap memory, v0 contain the result
            IR.add(new IR.sw(sizeReg, IRReg.v0, 0));  // store size as first element
            
            // TODO: zero all array elements
            // String arrInitLoopLabel = IR.uniqueLabel("arr_init_loop");
            // String arrInitEndLabel = IR.uniqueLabel("arr_init_end");
            // IRReg itReg = new IRReg.TempReg();
            // IR.add(new IR.add(sizeReg, IRReg.v0, IRReg.a0));
            // IR.add(new IR.addi(itReg, IRReg.v0, 4));
            // IR.add(new IR.label(arrInitLoopLabel));
            // IR.add(new IR.sw(IRReg.zero, itReg, 0));
            // IR.add(new IR.addi(itReg, itReg, 4));
            // IR.add(new IR.blt(itReg, sizeReg, arrInitLoopLabel));
        }
        else  // class instance
        {
            IR.add(new IR.li(IRReg.a0, numMembers));  // copy class size
            IR.add(new IR.sll(IRReg.a0, IRReg.a0, 4));  // convert to size in bytes
            IR.add(new IR.sbrk());  // allocate heap memory, v0 contain the result
            IRReg tmpReg = new IRReg.TempReg();
            for (int i = 0; i < numMembers; i++)  // init all members values
            {
                IR.add(new IR.li(tmpReg, classType.initVals.get(i)));
                IR.add(new IR.sw(tmpReg, IRReg.v0, i * 4));
            }
        }
		return IRReg.v0;
	}
    
}
