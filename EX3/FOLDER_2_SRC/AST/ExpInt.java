package AST;
import TYPES.*;

public class ExpInt extends ExpPrimitive
{
	public int value;
	
	public ExpInt(int value)
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
