package AST;
import TYPES.*;
import SymbolTable.*;

public class AST_StmtWhile extends AST_Stmt
{
	public AST_Exp cond;
	public AST_StmtList body;

	public AST_StmtWhile(AST_Exp cond, AST_StmtList body)
	{
		this.cond = cond;
		this.body = body;
	}

	public void toGraphviz()
	{
		if (cond != null) cond.toGraphviz();
		if (body != null) body.toGraphviz();

		logNode("StmtWhile");
		
		if (cond != null) logEdge(cond);
		if (body != null) logEdge(body);
	}

	public Type Semant() throws Exception
	{
		if (cond.Semant() != Type.INT) { throw new SemanticException(); }
		SymbolTable.beginScope(Type.Scope.WHILE);
		body.Semant();
		SymbolTable.endScope();
		return null;
	}

}
