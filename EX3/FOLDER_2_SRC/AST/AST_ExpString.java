package AST;
import TYPES.*;

public class AST_ExpString extends AST_ExpPrimitive
{
	public String value;
	
	public AST_ExpString(String value)
	{
		this.value = value;
	}

	public void toGraphviz()
	{
		logNode(String.format("ExpString\n%s", value.replace('"','\'')));
	}
	public Type Semant() throws Exception
	{
		return Type.STRING;
	}
}
