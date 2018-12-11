package AST;

import TYPES.*;

public class AST_DEC_LIST extends AST_Node
{
	public AST_DEC head;
	public AST_DEC_LIST tail;

	public AST_DEC_LIST(AST_DEC head,AST_DEC_LIST tail)
	{
		SerialNumber = AST_Node_Serial_Number.getFresh();

		this.head = head;
		this.tail = tail;
	}

	public TYPE SemantMe()
	{		
		if (head != null) head.SemantMe();
		if (tail != null) tail.SemantMe();
		return null;	
	}

	public void PrintMe()
	{
		System.out.print("AST NODE DEC LIST\n");

		if (head != null) head.PrintMe();
		if (tail != null) tail.PrintMe();

		AST_GRAPHVIZ.getInstance().logNode(SerialNumber, "DEC\nLIST\n");
		if (head != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, head.SerialNumber);
		if (tail != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, tail.SerialNumber);
	}
}
