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
		ASTGraphviz.logNode(SerialNumber, String.format("ExpString\n%s", value.replace('"','\'')));
	}
	public Type SemantMe() throws Exception
	{
		return TypeString.getInstance();
	}
}
