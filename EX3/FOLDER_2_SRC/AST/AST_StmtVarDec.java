package AST;
import TYPES.*;

public class AST_StmtVarDec extends AST_Stmt
{
	public AST_VarDec var;
	
	public AST_StmtVarDec(AST_VarDec var)
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
