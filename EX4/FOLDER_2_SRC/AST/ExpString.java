package AST;
import TYPES.*;

public class ExpString extends ExpPrimitive
{
	public String value;
	
	public ExpString(String value)
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
