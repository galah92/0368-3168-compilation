package AST;
import TYPES.*;

public class AST_EXP_VAR_SUBSCRIPT extends AST_EXP_VAR
{
	public AST_EXP_VAR var;
	public AST_EXP subscript;

	public AST_EXP_VAR_SUBSCRIPT(AST_EXP_VAR var, AST_EXP subscript)
	{
		this.var = var;
		this.subscript = subscript;
	}

	public void PrintMe()
	{
		if (var != null) var.PrintMe();
		if (subscript != null) subscript.PrintMe();
	}

	public TYPE SemantMe() throws Exception
	{
		if (subscript != null && subscript.SemantMe() != TYPE_INT.getInstance()) { throw new Exception(); }
		return null;
	}

}
