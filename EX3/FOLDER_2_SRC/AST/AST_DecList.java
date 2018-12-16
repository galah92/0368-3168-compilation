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

	public void PrintMe()
	{
		if (head != null) head.PrintMe();
		if (tail != null) tail.PrintMe();

		ASTGraphviz.logNode(SerialNumber, "DecList");
		if (head != null) ASTGraphviz.logEdge(SerialNumber, head.SerialNumber);
		if (tail != null) ASTGraphviz.logEdge(SerialNumber, tail.SerialNumber);
	}

	public Type SemantMe() throws Exception
	{
		if (head != null) head.SemantMe();
		if (tail != null) tail.SemantMe();
		return null;
	}
}
