package AST;
import TYPES.*;
import SymbolTable.*;

public class StmtIf extends Stmt
{
	public Exp cond;
	public StmtList body;

	public StmtIf(Exp cond, StmtList body)
	{
		this.cond = cond;
		this.body = body;
	}

	public void toGraphviz()
	{
		if (cond != null) cond.toGraphviz();
		if (body != null) body.toGraphviz();

		logNode("StmtIf");
		
		if (cond != null) logEdge(cond);
		if (body != null) logEdge(body);
	}

	public Type Semant() throws Exception
	{
		if (cond.Semant() != Type.INT) { throw new SemanticException(); }

		SymbolTable.beginScope(Type.Scope.IF);
		body.Semant();
		SymbolTable.endScope();
		
		return null;
	}	
}
