package AST;
import TYPES.*;
import SymbolTable.*;

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

		logNode("StmtIf");
		
		if (cond != null) logEdge(cond);
		if (body != null) logEdge(body);
	}

	public Type SemantMe() throws Exception
	{
		if (cond.SemantMe() != Type.INT) { throw new SemanticException(); }

		SymbolTable.beginScope(Type.Scope.IF);
		body.SemantMe();
		SymbolTable.endScope();
		
		return null;
	}	
}
