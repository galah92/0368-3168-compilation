package AST;
import TYPES.*;
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
		return var.Semant();
	}
	
}
