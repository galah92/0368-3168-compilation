package AST;
import TYPES.*;

public class AST_ExpString extends AST_ExpPrimitive
{
	public String value;
	
	public AST_ExpString(String value)
	{
		this.value = value;
	}

	public void PrintMe()
	{
		AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("STRING\n%s", value.replace('"','\'')));
	}
	public TYPE SemantMe() throws Exception
	{
		return TYPE_STRING.getInstance();
	}
}
