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
