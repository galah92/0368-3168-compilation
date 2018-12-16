package AST;
import TYPES.*;

public class AST_StmtList extends AST_Node
{
	public AST_Stmt head;
	public AST_StmtList tail;

	public AST_StmtList(AST_Stmt head,AST_StmtList tail)
	{
		this.head = head;
		this.tail = tail;
	}

	public void PrintMe()
	{
		if (head != null) head.PrintMe();
		if (tail != null) tail.PrintMe();

		logNode("STMT\nLIST\n");
		
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
