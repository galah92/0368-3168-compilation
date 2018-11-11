package AST;

public class AST_paramsList extends AST_Node
{
	public String head;
	public AST_paramsList tail;

	public AST_paramsList(String head, AST_paramsList tail)
	{
		this.head = head;
		this.tail = tail;
	}

	public void PrintMe()
	{
		System.out.println("AST_paramsList");
		if (head != null) System.out.println("ID(" + head + ")");
		if (tail != null) tail.PrintMe();
		AST_GRAPHVIZ.getInstance().logNode(SerialNumber, "paramsList\n");
		if (head != null) AST_GRAPHVIZ.getInstance().logNode(SerialNumber, head);
		if (tail != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, tail.SerialNumber);
	}
	
}
