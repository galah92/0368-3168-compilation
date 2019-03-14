package AST;


import pcomp.*;


public class ExpNil extends Exp
{

    @Override
    public void logGraphviz()
    {
        logNode(String.format("ExpNil"));
    }
    
    @Override
    public Type Semant() throws Exception
    {
        return Type.NIL;
    }

    @Override
	public IRReg toIR()
	{
        IRReg reg = new IRReg.TempReg();
        IR.add(new IR.move(reg, IRReg.zero));
        return reg;
	}
}
