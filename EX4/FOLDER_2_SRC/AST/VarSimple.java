package AST;
import TYPES.*;
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

	public TempReg IRme()
	{
		TempReg t = new TempReg();
		IR.add(new IRcommand_Load(t,varName));
		return t;
	}
}
