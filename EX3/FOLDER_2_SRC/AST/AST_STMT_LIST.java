package AST;
import TYPES.*;

public class AST_STMT_LIST extends AST_Node
{
	public AST_STMT head;
	public AST_STMT_LIST tail;

	public AST_STMT_LIST(AST_STMT head,AST_STMT_LIST tail)
	{
		SerialNumber = AST_Node_Serial_Number.getFresh();

		if (tail != null) System.out.print("====================== stmts -> stmt stmts\n");
		if (tail == null) System.out.print("====================== stmts -> stmt      \n");

		this.head = head;
		this.tail = tail;
	}

	public void PrintMe()
	{
		System.out.print("AST NODE STMT LIST\n");

		if (head != null) head.PrintMe();
		if (tail != null) tail.PrintMe();

		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			"STMT\nLIST\n");
		
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
