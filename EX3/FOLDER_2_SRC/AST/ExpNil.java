package AST;
import TYPES.*;

public class ExpNil extends ExpPrimitive
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
