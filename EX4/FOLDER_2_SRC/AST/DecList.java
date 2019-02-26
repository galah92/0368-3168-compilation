package AST;
import TYPES.*;
import pcomp.*;
import IR.*;


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

	public Type Semant() throws Exception
	{
		if (head != null) head.Semant();
		if (tail != null) tail.Semant();
		return null;
	}

	@Override
	public TempReg toIR()
	{
		if (head != null) head.toIR();
		if (tail != null) tail.toIR();
		return null;
	}
}
