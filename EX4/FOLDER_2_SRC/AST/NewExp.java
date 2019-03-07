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
        if (exp != null)  // array variable
        {
            IR.add(new IR.move(IRReg.a0, exp.toIR()));  // copy array size
        }
        else  // class variable
        {
            IR.add(new IR.li(IRReg.a0, numMembers));  // copy class size
        }
        IR.add(new IR.sll(IRReg.a0, IRReg.a0, 4));  // convert to size in bytes
        IR.add(new IR.sbrk());  // allocate heap memory, v0 contain the result
        if (exp == null)
        {
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
