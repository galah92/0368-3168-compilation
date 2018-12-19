package AST;
import TYPES.*;

public class AST_ExpInt extends AST_ExpPrimitive
{
	public int value;
	
	public AST_ExpInt(int value)
	{
		this.value = value;
	}

	public void toGraphviz()
	{
		logNode(String.format("ExpInt\n%s",value));
	}
	
	public Type Semant() throws Exception
	{
		return Type.INT;
	}
}
