package AST;
import TYPES.*;

public abstract class AST_Node
{
	public int SerialNumber = AST_Node_Serial_Number.getFresh();
	
	public void PrintMe()
	{
		System.out.print("AST NODE UNKNOWN\n");
	}

	public TYPE SemantMe() throws Exception
	{
		return null;
	}
}
