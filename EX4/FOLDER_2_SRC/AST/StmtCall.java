package AST;
import TYPES.*;
import pcomp.*;

public class StmtCall extends Stmt
{
	public ExpCall callExp;

	public StmtCall(ExpCall callExp)
	{
		this.callExp = callExp;
	}

	public void logGraphviz()
	{
		callExp.logGraphviz();
		logNode(String.format("StmtCall"));
		logEdge(callExp);
	}

	public Type Semant() throws Exception
	{
		return callExp.Semant();
	}

}
