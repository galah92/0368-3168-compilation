package AST;
import TYPES.*;

public class AST_ExpList extends AST_Node
{
	public AST_Exp head;
	public AST_ExpList tail;

	public AST_ExpList(AST_Exp head, AST_ExpList tail)
	{
		this.head = head;
		this.tail = tail;
	}
	
	public void PrintMe()
	{
		if (head != null) head.PrintMe();
		if (tail != null) tail.PrintMe();

		logNode("ExpList");

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
