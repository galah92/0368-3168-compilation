package AST;
import TYPES.*;

public class AST_StmtAssign extends AST_STMT
{
	public AST_Var var;
	public AST_EXP exp;

	public AST_StmtAssign(AST_Var var, AST_EXP exp)
	{
		this.var = var;
		this.exp = exp;
	}

	public void PrintMe()
	{
		if (var != null) var.PrintMe();
		if (exp != null) exp.PrintMe();

		AST_GRAPHVIZ.getInstance().logNode(SerialNumber, "ASSIGN\nleft := right\n");
		AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, var.SerialNumber);
		AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, exp.SerialNumber);
	}
	public TYPE SemantMe() throws Exception
	{
		TYPE t1 = null;
		TYPE t2 = null;
		
		if (var != null) t1 = var.SemantMe();
		if (exp != null) t2 = exp.SemantMe();
		
		if (t1 != t2) { throw new Exception(); }
		return null;
	}
}
