package AST;
import TYPES.*;

public class AST_StmtCall extends AST_Stmt
{
	public AST_ExpCall callExp;

	public AST_StmtCall(AST_ExpCall callExp)
	{
		this.callExp = callExp;
	}

	public void PrintMe()
	{
		callExp.PrintMe();
		AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("StmtCall"));
		AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, callExp.SerialNumber);
	}

	public Type SemantMe() throws Exception
	{
		return callExp.SemantMe();
	}

}
