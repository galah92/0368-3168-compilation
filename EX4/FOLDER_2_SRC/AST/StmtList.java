package AST;

import IR.*;
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

		logNode("STMT\nLIST\n");
		
		if (head != null) logEdge(head);
		if (tail != null) logEdge(tail);
	}
	
	public Type Semant() throws Exception
	{
		if (head != null) head.Semant();
		if (tail != null) tail.Semant();
		return null;
	}

	public TempReg toIR()
	{
		if (head != null) head.toIR();
		if (tail != null) tail.toIR();
		
		return null;
	}
}
