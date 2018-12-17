package AST;

public class AST_decList extends AST_Node
{

	public AST_dec head;
	public AST_decList tail;

	public AST_decList(AST_dec head, AST_decList tail)
	{
		this.head = head;
		this.tail = tail;
        System.out.println("decList");
	}

	public void PrintMe()
	{
		System.out.println("AST_decList");
		if (head != null) head.PrintMe();
		if (tail != null) tail.PrintMe();
		AST_GRAPHVIZ.getInstance().logNode(SerialNumber, "decList\n");
		if (head != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, head.SerialNumber);
		if (tail != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, tail.SerialNumber);
	}
	
}
