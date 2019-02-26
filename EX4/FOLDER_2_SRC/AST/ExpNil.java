package AST;


import pcomp.*;


public class ExpNil extends ExpPrimitive
{

    public void logGraphviz()
    {
        logNode(String.format("ExpNil"));
    }
    
    public Type Semant() throws Exception
    {
        return Type.NIL;
    }

    @Override
	public TempReg toIR()
	{
        return TempReg.ZeroReg;
	}
}
