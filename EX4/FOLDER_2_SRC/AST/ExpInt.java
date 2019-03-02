package AST;

import pcomp.*;


public class ExpInt extends ExpPrimitive
{
	private int value;
	
	public ExpInt(int value)
	{
		this.value = value;
	}

	public void logGraphviz()
	{
		logNode(String.format("ExpInt\n%s", value));
	}
	
	public Type Semant() throws Exception
	{
		return Type.INT;
	}

	public TempReg toIR()
	{
		TempReg reg = new TempReg();
		IR.add(new IR.li(reg, value));
		return reg;
	}
}
