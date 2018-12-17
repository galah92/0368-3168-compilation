package AST;

public class AST_cFieldList extends AST_Node
{

	public AST_cField head;
	public AST_cFieldList tail;

	public AST_cFieldList(AST_cField head, AST_cFieldList tail)
	{
		this.head = head;
		this.tail = tail;
	}

	public void PrintMe()
	{
		System.out.println("AST_cFieldList");
		if (head != null) head.PrintMe();
		if (tail != null) tail.PrintMe();
		AST_GRAPHVIZ.getInstance().logNode(SerialNumber, "cFieldList\n");
		if (head != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, head.SerialNumber);
		if (tail != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, tail.SerialNumber);
	}
	
}
