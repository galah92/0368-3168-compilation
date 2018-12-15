package AST;
import TYPES.*;

public class AST_STMT_DEC_VAR extends AST_STMT
{
	public AST_VarDec var;
	
	public AST_STMT_DEC_VAR(AST_VarDec var)
	{
		this.var = var;
	}
	
	public void PrintMe()
	{
		var.PrintMe();

		AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("STMT\nDEC\nVAR"));		
		AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,var.SerialNumber);		
	}

	public TYPE SemantMe() throws Exception
	{
		return var.SemantMe();
	}
	
}
