package AST;


import pcomp.*;

public class StmtList extends Node
{
	public Stmt value;
	public StmtList next;

	public StmtList(Stmt value, StmtList next)
	{
		this.value = value;
		this.next = next;
	}

	public void logGraphviz()
	{
		if (value != null) value.logGraphviz();
		if (next != null) next.logGraphviz();
		logNode("StmtList");
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
