package AST;
import TYPES.*;
import SYMBOL_TABLE.*;

public class AST_StmtIf extends AST_Stmt
{
	public AST_Exp cond;
	public AST_StmtList body;

	public AST_StmtIf(AST_Exp cond, AST_StmtList body)
	{
		this.cond = cond;
		this.body = body;
	}

	public void PrintMe()
	{
		if (cond != null) cond.PrintMe();
		if (body != null) body.PrintMe();

		AST_GRAPHVIZ.getInstance().logNode(SerialNumber, "StmtIf");
		
		if (cond != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,cond.SerialNumber);
		if (body != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,body.SerialNumber);
	}

	public TYPE SemantMe() throws Exception
	{
		if (cond.SemantMe() != TYPE_INT.getInstance()) { throw new SemanticException(); }

		SYMBOL_TABLE.getInstance().beginScope();
		body.SemantMe();
		SYMBOL_TABLE.getInstance().endScope();
		
		return null;
	}	
}
