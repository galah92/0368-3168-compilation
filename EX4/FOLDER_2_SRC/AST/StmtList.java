package AST;


import pcomp.*;

public class StmtList extends Node
{
	public Stmt head;
	public StmtList tail;

	public StmtList(Stmt head,StmtList tail)
	{
		this.head = head;
		this.tail = tail;
	}

	public void logGraphviz()
	{
		if (head != null) head.logGraphviz();
		if (tail != null) tail.logGraphviz();
		logNode("StmtList");
		if (head != null) logEdge(head);
		if (tail != null) logEdge(tail);
	}
	
	public TypeList Semant() throws Exception
	{
		return new TypeList(head.Semant(), tail != null ? tail.Semant() : null);
	}

	@Override
	public TempReg toIR()
	{
		if (head != null) head.toIR();
		if (tail != null) tail.toIR();
		return null;
	}
}
