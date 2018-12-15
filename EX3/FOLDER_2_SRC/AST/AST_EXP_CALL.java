package AST;
import TYPES.*;
import SYMBOL_TABLE.*;

public class AST_EXP_CALL extends AST_EXP
{
	public String funcName;
	public AST_EXP_LIST params;

	public AST_EXP_CALL(String funcName, AST_EXP_LIST params)
	{
		SerialNumber = AST_Node_Serial_Number.getFresh();

		this.funcName = funcName;
		this.params = params;
	}

	public void PrintMe()
	{
		if (params != null) params.PrintMe();

		AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("CALL(%s)\nWITH", funcName));
		AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, params.SerialNumber);
	}

	public TYPE SemantMe() throws Exception
	{
		TYPE funcRetType = SYMBOL_TABLE.getInstance().find(funcName);
		if (funcRetType == null) { throw new Exception(); }
		params.SemantMe();
		return funcRetType;
	}
}
