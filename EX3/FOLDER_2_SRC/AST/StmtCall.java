package AST;
import TYPES.*;

public class StmtCall extends Stmt
{
	public ExpCall callExp;

	public StmtCall(ExpCall callExp)
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
