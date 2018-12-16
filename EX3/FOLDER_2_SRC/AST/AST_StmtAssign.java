package AST;
import TYPES.*;

public class AST_StmtAssign extends AST_Stmt
{
	public AST_Var var;
	public AST_Exp exp;

	public AST_StmtAssign(AST_Var var, AST_Exp exp)
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
		TYPE varType = null;
		TYPE expType = null;
		
		if (var != null) varType = var.SemantMe();
		if (exp != null) expType = exp.SemantMe();
		
		if (expType == null) return null;

		if (exp instanceof AST_NewExp)
		{
			// if RHS is array, then expType is the integral type,
			// so we must compare it with the integral type of LHS
			TYPE integralVarType = varType instanceof TYPE_ARRAY ? ((TYPE_ARRAY)varType).elementType : varType;
			if (integralVarType != expType) { throw new SemanticException(integralVarType + ", " + expType); }
		}
		else if (varType != expType) { throw new SemanticException(varType + ", " + expType); }

		return varType;
	}
}
