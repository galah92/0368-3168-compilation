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
		ASTGraphviz.logNode(SerialNumber, String.format("StmtCall"));
		ASTGraphviz.logEdge(SerialNumber, callExp.SerialNumber);
	}

	public Type SemantMe() throws Exception
	{
		return callExp.SemantMe();
	}

}
