package AST;
import TYPES.*;
import SymbolTable.*;

public class StmtWhile extends Stmt
{
	public Exp cond;
	public StmtList body;

	public StmtWhile(Exp cond, StmtList body)
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
