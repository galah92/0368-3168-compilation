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
	public TYPE SemantMe() throws Exception
	{
		TYPE_FUNCTION funcType = SymbolTable.getInstance().findFunc();
		if (funcType == null) { throw new SemanticException(); }
		TYPE expType = exp != null ? exp.SemantMe() : TYPE_VOID.getInstance();
		if (funcType.retType != expType) { throw new SemanticException(); }
		return expType;
	}

}
