package AST;

public class AST_STMT_RETURN extends AST_STMT
{
	public AST_EXP exp;

	/*******************/
	/*  CONSTRUCTOR(S) */
	/*******************/
	public AST_STMT_RETURN(AST_EXP exp)
    {
		SerialNumber = AST_Node_Serial_Number.getFresh();
		this.exp = exp;
	}

	public void PrintMe(){
		System.out.format("AST_STMT_RETURN\n");
        if (exp != null) exp.PrintMe();
		AST_GRAPHVIZ.getInstance().logNode(SerialNumber, "RETURN EXP\n");        
		AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, exp.SerialNumber);
	}

}