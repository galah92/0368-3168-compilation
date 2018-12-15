package AST;
import TYPES.*;

public class AST_ExpList extends AST_Node
{
	public AST_EXP head;
	public AST_ExpList tail;

	public AST_ExpList(AST_EXP head, AST_ExpList tail)
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
