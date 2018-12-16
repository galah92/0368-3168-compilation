package AST;
import TYPES.*;

public class AST_ExpNil extends AST_ExpPrimitive
{
	public void PrintMe()
	{
		ASTGraphviz.logNode(SerialNumber, String.format("ExpNil"));
	}
	
	public Type SemantMe() throws Exception
	{
        return null; // TODO: should probably replace it with TypeVoid or something alike
	}
}
