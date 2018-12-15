package AST;
import TYPES.*;

public class AST_EXP_LIST extends AST_Node
{
	public AST_EXP head;
	public AST_EXP_LIST tail;

	public AST_EXP_LIST(AST_EXP head, AST_EXP_LIST tail)
	{
		this.head = head;
		this.tail = tail;
	}
	
	public void PrintMe()
	{
		if (head != null) head.PrintMe();
		if (tail != null) tail.PrintMe();

		AST_GRAPHVIZ.getInstance().logNode(SerialNumber, "EXP\nLIST\n");

		if (head != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, head.SerialNumber);
		if (tail != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, tail.SerialNumber);
	}

	public TYPE SemantMe() throws Exception
	{
		if (head != null) head.SemantMe();
		if (tail != null) tail.SemantMe();
		return null;
	}
}
