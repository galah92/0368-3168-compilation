package AST;
import TYPES.*;

public class StmtList extends Node
{
	public Stmt head;
	public StmtList tail;

	public StmtList(Stmt head,StmtList tail)
	{
		this.head = head;
		this.tail = tail;
	}

	public void toGraphviz()
	{
		if (head != null) head.toGraphviz();
		if (tail != null) tail.toGraphviz();

		logNode("STMT\nLIST\n");
		
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
