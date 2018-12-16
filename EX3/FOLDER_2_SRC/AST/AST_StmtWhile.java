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

		AST_GRAPHVIZ.getInstance().logNode(SerialNumber, "StmtWhile");
		
		if (cond != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, cond.SerialNumber);
		if (body != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, body.SerialNumber);
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
