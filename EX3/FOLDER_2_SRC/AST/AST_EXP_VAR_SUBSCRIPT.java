package AST;

public class AST_EXP_VAR_SUBSCRIPT extends AST_EXP_VAR
{
	public AST_EXP_VAR var;
	public AST_EXP subscript;

	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_EXP_VAR_SUBSCRIPT(AST_EXP_VAR var,AST_EXP subscript)
	{
		System.out.print("====================== var -> var [ exp ]\n");
		this.var = var;
		this.subscript = subscript;
	}

	/*****************************************************/
	/* The printing message for a subscript var AST node */
	/*****************************************************/
	public void PrintMe()
	{
		/*************************************/
		/* AST NODE TYPE = AST SUBSCRIPT VAR */
		/*************************************/
		System.out.print("AST NODE SUBSCRIPT VAR\n");

		/****************************************/
		/* RECURSIVELY PRINT VAR + SUBSRIPT ... */
		/****************************************/
		if (var != null) var.PrintMe();
		if (subscript != null) subscript.PrintMe();
	}

	public TYPE SemantMe(){
		TYPE t;
		if (subscript != null) t = subscript.SemantMe();
		if(t != TYPE_INT.getInstance()){
			System.out.format(">> ERROR [%d:%d] %s type is not int\n",6,6,subscript);
			System.exit(0);
		}
	}

}
