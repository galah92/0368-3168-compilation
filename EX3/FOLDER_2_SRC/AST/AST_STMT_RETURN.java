package AST;
import TYPES.*;

public class AST_STMT_RETURN extends AST_STMT
{
	public AST_EXP exp;

	public AST_STMT_RETURN(AST_EXP exp)
	{
		SerialNumber = AST_Node_Serial_Number.getFresh();
		this.exp = exp;
	}

	public void PrintMe()
	{
		if (exp != null) exp.PrintMe();

		AST_GRAPHVIZ.getInstance().logNode(SerialNumber, "RETURN");
		if (exp != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, exp.SerialNumber);
	}
	public TYPE SemantMe() throws Exception
	{
		return exp != null ? exp.SemantMe() : TYPE_VOID.getInstance();
	}

}
