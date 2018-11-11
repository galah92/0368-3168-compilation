package AST;

public class AST_funcDec extends AST_EXP
{
	public String retType;
	public String funcName;
	public AST_paramsList params;
	public AST_STMT_LIST stmts;
	
	public AST_funcDec(String retType, String funcName, AST_paramsList params, AST_STMT_LIST stmts)
	{
		this.retType = retType;
		this.funcName = funcName;
		this.params = params;
		this.stmts = stmts;
	}

	public void PrintMe()
	{
		System.out.format("AST_funcDec\n");

		AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("ID(%s)" ,retType));
		AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("ID(%s)" ,funcName));
		AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, params.SerialNumber);
		AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, stmts.SerialNumber);
	}
}
