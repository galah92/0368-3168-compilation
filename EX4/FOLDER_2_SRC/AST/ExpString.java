package AST;

import pcomp.*;


public class ExpString extends ExpPrimitive
{

    public String value;
    
    public ExpString(String value)
    {
        this.value = value;
    }

    @Override
    public void logGraphviz()
    {
        logNode(String.format("ExpString\n%s", value.replace('"', '\'')));
    }

    @Override
    public Type Semant() throws Exception
    {
        return Type.STRING;
    }

    @Override
	public TempReg toIR()
	{
		TempReg reg = new TempReg();
        IR.add(new IR.stringLiteral(reg, value));
        return reg;
	}
    
}
