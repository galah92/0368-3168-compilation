package AST;
import TYPES.*;

public class AST_StmtVarDec extends AST_STMT
{
	public AST_VarDec var;
	
	public AST_StmtVarDec(AST_VarDec var)
	{
		this.var = var;
	}
	
	public void PrintMe()
	{
		var.PrintMe();
		AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("StmtVarDec"));
		AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,var.SerialNumber);
	}

	public TYPE SemantMe() throws Exception
	{
		return var.SemantMe();
	}
	
}
