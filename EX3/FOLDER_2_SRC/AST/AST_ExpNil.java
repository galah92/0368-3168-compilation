package AST;
import TYPES.*;

public class AST_ExpNil extends AST_ExpPrimitive
{
	public void toGraphviz()
	{
		logNode(String.format("ExpNil"));
	}
	
	public Type Semant() throws Exception
	{
        return Type.NIL;
	}
}
