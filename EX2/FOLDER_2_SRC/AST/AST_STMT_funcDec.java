package AST;

public class AST_STMT_funcDec extends AST_STMT
{
	/***************/
	/*  var := exp */
	/***************/
	public AST_funcDec fDec;

	public AST_STMT_funcDec(AST_funcDec fDec)
	{
		this.fDec = fDec;
	}

	/*********************************************************/
	/* The printing message for an assign statement AST node */
	/*********************************************************/
	public void PrintMe()
	{
		fDec.PrintMe();
		AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, fDec.SerialNumber); 
	}
}
