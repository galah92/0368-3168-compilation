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

		AST_GRAPHVIZ.getInstance().logNode(SerialNumber, "STMT\nLIST\n");
		
		if (head != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,head.SerialNumber);
		if (tail != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,tail.SerialNumber);
	}
	
	public TYPE SemantMe() throws Exception
	{
		if (head != null) head.SemantMe();
		if (tail != null) tail.SemantMe();
		return null;
	}
}
