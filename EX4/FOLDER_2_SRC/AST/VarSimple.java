package AST;

import pcomp.*;


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
		Type varType = SymbolTable.find(varName);
		if (varType == null) { throw new SemanticException("unknown identifier: " + varName); }
		return varType;
	}

	@Override
	public TempReg toIR()
	{
		TempReg t = new TempReg();
		IR.add(new IRComm_Load(t, varName));
		return t;
	}
}
