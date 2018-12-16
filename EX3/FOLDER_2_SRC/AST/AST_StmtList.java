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

		ASTGraphviz.logNode(SerialNumber, "STMT\nLIST\n");
		
		if (head != null) ASTGraphviz.logEdge(SerialNumber,head.SerialNumber);
		if (tail != null) ASTGraphviz.logEdge(SerialNumber,tail.SerialNumber);
	}
	
	public Type SemantMe() throws Exception
	{
		if (head != null) head.SemantMe();
		if (tail != null) tail.SemantMe();
		return null;
	}
}
