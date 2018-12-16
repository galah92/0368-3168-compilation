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

		logNode("ASSIGN\nleft := right\n");
		logEdge(var);
		logEdge(exp);
	}
	public Type SemantMe() throws Exception
	{
		Type varType = null;
		Type expType = null;
		
		if (var != null) varType = var.SemantMe();
		if (exp != null) expType = exp.SemantMe();
		
		if (expType == null) return null;

		// if (exp instanceof AST_NewExp)
		// {
		// 	// if RHS is array, then expType is the integral Type,
		// 	// so we must compare it with the integral Type of LHS
		// 	Type integralVarType = varType instanceof TypeArray ? ((TypeArray)varType).elementType : varType;
		// 	if (expType instanceof TypeClass)
		// 	{
		// 		if (!((TypeClass)expType).isInheritingFrom(integralVarType.name)) { throw new SemanticException(integralVarType + ", " + expType); }
		// 	}
		// 	else
		// 	{
		// 		if (expType != integralVarType) { throw new SemanticException(integralVarType + ", " + expType); }
		// 	}
		// }
		// else if (varType != expType) { throw new SemanticException(varType.name + ", " + expType.name); }


		Type integralVarType = (exp instanceof AST_NewExp && varType instanceof TypeArray) ? ((TypeArray)varType).elementType : varType;
		if (expType instanceof TypeClass)
		{
			if (!((TypeClass)expType).isInheritingFrom(integralVarType.name)) { throw new SemanticException(integralVarType + ", " + expType); }
		}
		else
		{
			if (expType != integralVarType) { throw new SemanticException(integralVarType + ", " + expType); }
		}


		return varType;
	}
}
