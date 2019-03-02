package AST;

import pcomp.*;


public class NewExp extends Exp
{

    String typeName;
    Exp exp;

    public NewExp(String typeName, Exp exp)
    {
		this.typeName = typeName;
        this.exp = exp;
    }

    @Override
    public void logGraphviz()
	{
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
        }

        return newExpType;
	}

    @Override
	public TempReg toIR()
	{
        TempReg dstReg = new TempReg();
        if (exp != null)
        {
            IR.add(new IR.heapAlloc(dstReg, exp.toIR()));
        }
        else
        {
            System.out.println("allocating classes not supported yet");
        }
		return dstReg;
	}
    
}
