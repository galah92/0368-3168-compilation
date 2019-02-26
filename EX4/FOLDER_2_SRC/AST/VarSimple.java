package AST;

import pcomp.*;
import IR.*;

public class VarSimple extends Var
{
	public String varName;
	
	public VarSimple(String varName)
	{
		this.varName = varName;
	}

	public void logGraphviz()
	{
		logNode(String.format("VarSimple\n(%s)", varName));
	}
	
	public Type Semant() throws Exception
	{
		return SymbolTable.find(varName);
	}

	@Override
	public TempReg toIR()
	{
		TempReg t = new TempReg();
		IR.add(new IRcommand_Load(t,varName));
		return t;
	}
}
