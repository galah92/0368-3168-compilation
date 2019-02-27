package AST;

import pcomp.*;

public class StmtVarDec extends Stmt
{
	public VarDec var;
	
	public StmtVarDec(VarDec var)
	{
		this.var = var;
	}
	
	public void logGraphviz()
	{
		var.logGraphviz();
		logNode(String.format("StmtVarDec"));
		logEdge(var);
	}

	public Type Semant() throws Exception
	{
		TypeFunc func = SymbolTable.findFunc();
		if (func != null) { func.numLocals++; }
		return var.Semant();
	}

	@Override
	public TempReg toIR()
	{
		return var.toIR();
	}
	
}
