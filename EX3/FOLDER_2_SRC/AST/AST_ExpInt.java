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
		AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("INT(%d)",value));
	}
	
	public TYPE SemantMe() throws Exception
	{
		return TYPE_INT.getInstance();
	}
}
