package AST;
import TYPES.*;

public abstract class AST_Node
{
	public int SerialNumber;
	
	public void PrintMe()
	{
		System.out.print("AST NODE UNKNOWN\n");
	}

	public TYPE SemantMe() throws Exception
	{
		return null;
	}
}
