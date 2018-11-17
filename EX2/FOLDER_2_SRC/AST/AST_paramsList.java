package AST;

public class AST_paramsList extends AST_Node
{
	public String id1;
	public String id2;
	public AST_paramsList tail;

	public AST_paramsList(String id1, String id2, AST_paramsList tail)
	{
		this.id1 = id1;
		this.id2 = id2;
		this.tail = tail;
		if (tail != null) System.out.print("====================== paramList -> ID ID COMMA paramList\n");
		if (tail == null) System.out.print("====================== paramList -> ID ID     \n");
	}

	public void PrintMe()
	{
		System.out.println("AST_paramsList");
		System.out.println("ID(" + id1 + ")");
		System.out.println("ID(" + id2 + ")");
		if (tail != null) tail.PrintMe();
		AST_GRAPHVIZ.getInstance().logNode(SerialNumber, "paramsList\n");
		AST_GRAPHVIZ.getInstance().logNode(SerialNumber, id1);
		AST_GRAPHVIZ.getInstance().logNode(SerialNumber, id2);
		if (tail != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, tail.SerialNumber);
	}
	
}
