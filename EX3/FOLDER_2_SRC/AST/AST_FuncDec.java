package AST;
import TYPES.*;
import SYMBOL_TABLE.*;

public class AST_FuncDec extends AST_ClassField
{
	public String retTypeName;
	public String funcName;
	public AST_ParamsList params;
	public AST_STMT_LIST body;
	
	public AST_FuncDec(String retTypeName, String funcName, AST_ParamsList params, AST_STMT_LIST body)
	{
		this.retTypeName = retTypeName;
		this.funcName = funcName;
		this.params = params;
		this.body = body;
	}

	public void PrintMe()
	{
		if (params != null) params.PrintMe();
		if (body != null) body.PrintMe();

		AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("FuncDec\n%s\n%s", retTypeName, funcName));
		if (params != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, params.SerialNumber);
		if (body != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, body.SerialNumber);
	}

	public TYPE SemantMe() throws Exception
	{
		TYPE retType = SYMBOL_TABLE.getInstance().find(retTypeName);
		if (retType == null) { throw new Exception(); }
		TYPE_LIST paramsTypes = params != null ? params.SemantMe(): null;
		SYMBOL_TABLE.getInstance().beginScope();
		if (body != null) { body.SemantMe(); }
		SYMBOL_TABLE.getInstance().endScope();
		SYMBOL_TABLE.getInstance().enter(funcName, new TYPE_FUNCTION(retType, funcName, paramsTypes));
		return null;
	}

}
