package AST;
import TYPES.*;
import SymbolStack.*;

public class StmtWhile extends Stmt
{
	public Exp cond;
	public StmtList body;

	public StmtWhile(Exp cond, StmtList body)
	{
		this.cond = cond;
		this.body = body;
	}

	public void logGraphviz()
	{
		if (cond != null) cond.logGraphviz();
		if (body != null) body.logGraphviz();

		logNode("StmtWhile");
		
		if (cond != null) logEdge(cond);
		if (body != null) logEdge(body);
	}

	public Type Semant() throws Exception
	{
		if (cond.Semant() != Type.INT) { throw new SemanticException(); }
		SymbolStack.beginScope(Type.Scope.WHILE);
		body.Semant();
		SymbolStack.endScope();
		return null;
	}

}
