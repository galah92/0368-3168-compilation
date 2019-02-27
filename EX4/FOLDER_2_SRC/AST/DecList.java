package AST;

import pcomp.*;


public class DecList extends Node
{
	public Dec value;
	public DecList next;

	public DecList(Dec value, DecList next)
	{
		this.value = value;
		this.next = next;
	}

	public void logGraphviz()
	{
		if (value != null) value.logGraphviz();
		if (next != null) next.logGraphviz();
		logNode("DecList");
		if (value != null) logEdge(value);
		if (next != null) logEdge(next);
	}

	public TypeList Semant() throws Exception
	{
		return new TypeList(value.Semant(), next != null ? next.Semant() : null);
	}

	@Override
	public TempReg toIR()
	{
		if (value != null) value.toIR();
		if (next != null) next.toIR();
		return null;
	}
}
