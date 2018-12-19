package AST;
import TYPES.*;
import SymbolStack.*;

public class StmtVarDec extends Stmt
{
	public VarDec var;
	
	public StmtVarDec(VarDec var)
	{
		this.var = var;
	}
	
	public void toGraphviz()
	{
		var.toGraphviz();
		logNode(String.format("StmtVarDec"));
		logEdge(var);
	}

	public Type Semant() throws Exception
	{
		return var.Semant();
	}
	
}
