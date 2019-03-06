package AST;

import pcomp.*;


public class DecList extends Node
{
	public Dec head;
	public DecList tail;

	public DecList(Dec head, DecList tail)
	{
		this.head = head;
		this.tail = tail;
	}

	public void logGraphviz()
	{
		if (head != null) head.logGraphviz();
		if (tail != null) tail.logGraphviz();
		logNode("DecList");
		if (head != null) logEdge(head);
		if (tail != null) logEdge(tail);
	}

	public TypeList Semant() throws Exception
	{
		return new TypeList(head.Semant(), tail != null ? tail.Semant() : null);
	}

	@Override
	public IRReg toIR()
	{
		head.toIR();
		if (tail != null) tail.toIR();
		return null;
	}
}
