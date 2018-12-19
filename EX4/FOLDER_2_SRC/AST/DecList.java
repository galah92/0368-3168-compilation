package AST;
import TYPES.*;

public class DecList extends Node
{
	public Dec head;
	public DecList tail;

	public DecList(Dec head, DecList tail)
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
