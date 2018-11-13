package AST;

public class AST_expList extends AST_Node
{

	public AST_EXP head;
	public AST_expList tail;

	public AST_expList(AST_EXP head, AST_expList tail)
	{
		this.head = head;
		this.tail = tail;
        System.out.println("expList");
	}

	public void PrintMe()
	{
		System.out.println("AST_expList");
		if (head != null) head.PrintMe();
		if (tail != null) tail.PrintMe();
		AST_GRAPHVIZ.getInstance().logNode(SerialNumber, "expList\n");
		if (head != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, head.SerialNumber);
		if (tail != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, tail.SerialNumber);
	}
	
}
