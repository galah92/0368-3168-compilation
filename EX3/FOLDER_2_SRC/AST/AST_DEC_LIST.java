package AST;
import TYPES.*;

public class AST_DEC_LIST extends AST_Node
{
	public AST_DEC head;
	public AST_DEC_LIST tail;

	public AST_DEC_LIST(AST_DEC head,AST_DEC_LIST tail)
	{
		this.head = head;
		this.tail = tail;
	}

	public void PrintMe()
	{
		if (head != null) head.PrintMe();
		if (tail != null) tail.PrintMe();

		AST_GRAPHVIZ.getInstance().logNode(SerialNumber, "DecList");
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
