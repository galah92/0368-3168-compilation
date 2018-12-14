package AST;
import TYPES.*;

public class AST_EXP_VAR_SUBSCRIPT extends AST_EXP_VAR
{
	public AST_EXP_VAR var;
	public AST_EXP subscript;

	public AST_EXP_VAR_SUBSCRIPT(AST_EXP_VAR var, AST_EXP subscript)
	{
		System.out.print("====================== var -> var [ exp ]\n");
		this.var = var;
		this.subscript = subscript;
	}

	public void PrintMe()
	{
		System.out.print("AST NODE SUBSCRIPT VAR\n");

		if (var != null) var.PrintMe();
		if (subscript != null) subscript.PrintMe();
	}

	public TYPE SemantMe() throws Exception
	{
		if (subscript != null && subscript.SemantMe() != TYPE_INT.getInstance())
		{
			System.out.format(">> ERROR [%d:%d] %s type is not int\n",6,6,subscript);
			throw new Exception();
		}
		return null;
	}

}
