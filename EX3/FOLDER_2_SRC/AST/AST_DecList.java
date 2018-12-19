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

	public void toGraphviz()
	{
		if (head != null) head.toGraphviz();
		if (tail != null) tail.toGraphviz();

		logNode("DecList");
		if (head != null) logEdge(head);
		if (tail != null) logEdge(tail);
	}

	public Type Semant() throws Exception
	{
		if (head != null) head.Semant();
		if (tail != null) tail.Semant();
		return null;
	}
}
