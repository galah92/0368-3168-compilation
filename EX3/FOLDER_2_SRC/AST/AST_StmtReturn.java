package AST;
import TYPES.*;

public class AST_StmtReturn extends AST_Stmt
{
	public AST_Exp exp;

	public AST_StmtReturn(AST_Exp exp)
	{
		this.exp = exp;
	}

	public void PrintMe()
	{
		if (exp != null) exp.PrintMe();

		AST_GRAPHVIZ.getInstance().logNode(SerialNumber, "StmtReturn");
		if (exp != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, exp.SerialNumber);
	}
	public TYPE SemantMe() throws Exception
	{
		return exp != null ? exp.SemantMe() : TYPE_VOID.getInstance();
	}

}
