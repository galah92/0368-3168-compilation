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
        }
        else  // class instance
        {
            IR.add(new IR.li(IRReg.a0, numMembers));  // copy class size
            IR.add(new IR.addi(IRReg.a0, IRReg.a0, 1));  // first element is vtable
            IR.add(new IR.sll(IRReg.a0, IRReg.a0, 4));  // convert to size in bytes
            IR.add(new IR.sbrk());  // allocate heap memory, v0 contain the result
            if (classType.methods.size() == 0)
            {
                IR.add(new IR.sw(IRReg.zero, IRReg.v0, 0));  // store zero as vtable address
		        return IRReg.v0;
            }
            else  // where there are methods there is a vtable
            {
                String vtableLabel = String.format("_%s_vtable", classType.className);
                IRReg vtable = new IRReg.TempReg();
                IR.add(new IR.la(vtable, vtableLabel));  // get vtable
                IR.add(new IR.sw(vtable, IRReg.v0, 0));  // store vtable as first element
            }
            for (int i = 0; i < numMembers; i++)  // init all members
            {
                Object initVal = classType.initVals.get(i);
                if (initVal instanceof Integer)
                {
                    IR.add(new IR.li(IRReg.a0, (Integer)initVal));
                }
                else if (initVal instanceof String)
                {
                    IR.add(new IR.la(IRReg.a0, (String)initVal));
                }
                IR.add(new IR.sw(IRReg.a0, IRReg.v0, (i + 1) * 4));
            }
        }
		return IRReg.v0;
	}
    
}
