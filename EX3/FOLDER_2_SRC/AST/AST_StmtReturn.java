package AST;
import TYPES.*;
import SymbolTable.*;

public class AST_StmtReturn extends AST_Stmt
{
	public AST_Exp exp;

	public AST_StmtReturn(AST_Exp exp)
	{
		this.exp = exp;
	}

	public void PrintMe()
	{
		if (exp != null) exp.PrintMe();
		logNode("StmtReturn");
		if (exp != null) logEdge(exp);
	}
	public Type SemantMe() throws Exception
	{
		TypeFunc funcType = SymbolTable.findFunc();
		if (funcType == null) { throw new SemanticException(); }
		Type expType = exp != null ? exp.SemantMe() : Type.VOID;
		if (funcType.retType != expType) { throw new SemanticException(funcType.retType + ", " + expType); }
		return expType;
	}

}
