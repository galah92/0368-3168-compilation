package AST;
import TYPES.*;
import SymbolTable.*;

public class AST_StmtWhile extends AST_Stmt
{
	public AST_Exp cond;
	public AST_StmtList body;

	public AST_StmtWhile(AST_Exp cond,AST_StmtList body)
	{
		this.cond = cond;
		this.body = body;
	}

	public void PrintMe()
	{
		if (cond != null) cond.PrintMe();
		if (body != null) body.PrintMe();

		logNode("StmtWhile");
		
		if (cond != null) logEdge(cond);
		if (body != null) logEdge(body);
	}

	public Type SemantMe() throws Exception
	{
		if (cond.SemantMe() != TypeInt.getInstance()) { throw new SemanticException(); }
		SymbolTable.getInstance().beginScope();
		body.SemantMe();
		SymbolTable.getInstance().endScope();
		return null;
	}

}
