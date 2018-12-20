package AST;
import TYPES.*;
import pcomp.*;
import IR.*;

public class ExpInt extends ExpPrimitive
{
	public int value;
	
	public ExpInt(int value)
	{
		this.value = value;
	}

	public void logGraphviz()
	{
		logNode(String.format("ExpInt\n%s",value));
	}
	
	public Type Semant() throws Exception
	{
		return Type.INT;
	}

	public TempReg toIR()
	{
		TempReg t = new TempReg();
		IR.add(new IRcommandConstInt(t,value));
		return t;
	}
}
