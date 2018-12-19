package AST;
import TYPES.*;

public class AST_StmtCall extends AST_Stmt
{
	public AST_ExpCall callExp;

	public AST_StmtCall(AST_ExpCall callExp)
	{
		this.callExp = callExp;
	}

	public void toGraphviz()
	{
		callExp.toGraphviz();
		logNode(String.format("StmtCall"));
		logEdge(callExp);
	}

	public Type Semant() throws Exception
	{
		return callExp.Semant();
	}

}
