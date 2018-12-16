package AST;
import TYPES.*;

public class AST_ExpInt extends AST_ExpPrimitive
{
	public int value;
	
	public AST_ExpInt(int value)
	{
		this.value = value;
	}

	public void PrintMe()
	{
		logNode(String.format("ExpInt\n%s",value));
	}
	
	public Type SemantMe() throws Exception
	{
		return TypeInt.getInstance();
	}
}
