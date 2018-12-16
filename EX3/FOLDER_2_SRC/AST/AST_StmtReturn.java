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

		AST_GRAPHVIZ.getInstance().logNode(SerialNumber, "StmtReturn");
		if (exp != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, exp.SerialNumber);
	}
	public Type SemantMe() throws Exception
	{
		TypeFunc funcType = SymbolTable.getInstance().findFunc();
		if (funcType == null) { throw new SemanticException(); }
		Type expType = exp != null ? exp.SemantMe() : TypeVoid.getInstance();
		if (funcType.retType != expType) { throw new SemanticException(); }
		return expType;
	}

}
