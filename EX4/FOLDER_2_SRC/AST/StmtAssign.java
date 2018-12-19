package AST;
import TYPES.*;
import SymbolStack.*;

public class StmtAssign extends Stmt
{
	public Var var;
	public Exp exp;

	public StmtAssign(Var var, Exp exp)
	{
		this.var = var;
		this.exp = exp;
	}

	public void toGraphviz()
	{
		if (var != null) var.toGraphviz();
		if (exp != null) exp.toGraphviz();

		logNode("ASSIGN\nleft := right\n");
		logEdge(var);
		logEdge(exp);
	}
	public Type Semant() throws Exception
	{
		Type varType = null;
		Type expType = null;
		
		if (var != null) varType = var.Semant();
		if (exp != null) expType = exp.Semant();
		
		if (expType == Type.NIL)
		{
			if (varType == Type.INT || varType == Type.STRING) { throw new SemanticException(); }
			return varType;
		}

		Type integralVarType = (exp instanceof NewExp && varType instanceof TypeArray) ? ((TypeArray)varType).elementType : varType;
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
