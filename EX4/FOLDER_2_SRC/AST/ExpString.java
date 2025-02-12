package AST;

import pcomp.*;


public class ExpString extends Exp
{

    public final String value;
    public String label;
    
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
        label = IR.uniqueLabel("string_literal");
        return Type.STRING;
    }

    @Override
	public IRReg toIR()
	{
		IRReg reg = new IRReg.TempReg();
        IR.add(new IR.stringLiteral(value, label));
        IR.add(new IR.la(reg, label));
        return reg;
	}
    
}
