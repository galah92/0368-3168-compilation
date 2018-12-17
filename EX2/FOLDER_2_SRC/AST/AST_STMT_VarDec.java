package AST;

public class AST_STMT_VarDec extends AST_STMT
{
	/***************/
	/*  var := exp */
	/***************/
	public AST_varDec varDec;

	public AST_STMT_VarDec(AST_varDec varDec)
	{
		this.varDec = varDec;
	}

	/*********************************************************/
	/* The printing message for an assign statement AST node */
	/*********************************************************/
	public void PrintMe()
	{
		 varDec.PrintMe();
		AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, varDec.SerialNumber); 
	}
}
