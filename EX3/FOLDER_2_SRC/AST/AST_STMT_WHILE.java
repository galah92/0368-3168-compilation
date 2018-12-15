package AST;
import TYPES.*;
import SYMBOL_TABLE.*;

public class AST_STMT_WHILE extends AST_STMT
{
	public AST_EXP cond;
	public AST_STMT_LIST body;

	public AST_STMT_WHILE(AST_EXP cond,AST_STMT_LIST body)
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
