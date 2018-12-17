package AST;
import TYPES.*;

public class AST_DecList extends AST_Node
{
	public AST_Dec head;
	public AST_DecList tail;

	public AST_DecList(AST_Dec head, AST_DecList tail)
	{
		this.head = head;
		this.tail = tail;
	}

	public void PrintMe()
	{
		if (head != null) head.PrintMe();
		if (tail != null) tail.PrintMe();

		logNode("DecList");
		if (head != null) logEdge(head);
		if (tail != null) logEdge(tail);
	}

	public Type SemantMe() throws Exception
	{
		if (head != null) head.SemantMe();
		if (tail != null) tail.SemantMe();
		return null;
	}
}
