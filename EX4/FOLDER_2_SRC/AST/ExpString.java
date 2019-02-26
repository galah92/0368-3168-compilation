package AST;

import pcomp.*;


public class ExpString extends ExpPrimitive
{
    public String value;
    
    public ExpString(String value)
    {
        this.value = value;
    }

    public void logGraphviz()
    {
        logNode(String.format("ExpString\n%s", value.replace('"','\'')));
    }

    public Type Semant() throws Exception
    {
        return Type.STRING;
    }

    @Override
	public TempReg toIR()
	{
		// TODO: implement
		return null;
	}
}
