package AST;
import TYPES.*;

public class AST_ExpNil extends AST_ExpPrimitive
{
	public void PrintMe()
	{
		logNode(String.format("ExpNil"));
	}
	
	public Type Semant() throws Exception
	{
        return Type.NIL;
	}
}
