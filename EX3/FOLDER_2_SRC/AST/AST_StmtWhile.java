package AST;
import TYPES.*;
import SYMBOL_TABLE.*;

public class AST_StmtWhile extends AST_Stmt
{
	public AST_Exp cond;
	public AST_StmtList body;

	public AST_StmtWhile(AST_Exp cond,AST_StmtList body)
	{
		this.cond = cond;
		this.body = body;
	}

	public TYPE SemantMe() throws Exception
	{
		if (cond.SemantMe() != TYPE_INT.getInstance()) { throw new Exception(); }
		SYMBOL_TABLE.getInstance().beginScope();
		body.SemantMe();
		SYMBOL_TABLE.getInstance().endScope();
		return null;
	}

}
