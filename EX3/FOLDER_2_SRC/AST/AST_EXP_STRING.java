package AST;
import TYPES.*;

public class AST_EXP_STRING extends AST_EXP
{
	public String value;
	
	public AST_EXP_STRING(String value)
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
