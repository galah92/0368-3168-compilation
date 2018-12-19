package AST;
import TYPES.*;

public class ExpList extends Node
{
	public Exp head;
	public ExpList tail;

	public ExpList(Exp head, ExpList tail)
	{
		this.head = head;
		this.tail = tail;
	}
	
	public void toGraphviz()
	{
		if (head != null) head.toGraphviz();
		if (tail != null) tail.toGraphviz();
		logNode("ExpList");
		if (head != null) logEdge(head);
		if (tail != null) logEdge(tail);
	}

	public TypeList Semant() throws Exception
	{
		return new TypeList(head.Semant(), tail != null ? tail.Semant() : null);
	}
}
