package AST;

public class AST_EXP_NIL extends AST_EXP
{

	public AST_EXP_NIL()
	{
		SerialNumber = AST_Node_Serial_Number.getFresh();
		System.out.format("====================== exp -> NIL\n");
	}

	public void PrintMe()
	{
		System.out.format("AST NODE NIL\n");
		AST_GRAPHVIZ.getInstance().logNode(SerialNumber, "NIL");
	}
}
