package AST;

public abstract class AST_Node
{
	/* The serial number is for debug purposes */
	/* In particular, it can help in creating  */
	/* a graphviz dot format of the AST ...    */
	public int SerialNumber = AST_Node_Serial_Number.getFresh();
	
	/* The default message for an unknown AST node */
	public void PrintMe()
	{
		System.out.println("AST NODE UNKNOWN");
	}
}
