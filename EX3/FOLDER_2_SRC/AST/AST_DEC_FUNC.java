package AST;

import TYPES.*;
import SYMBOL_TABLE.*;

public class AST_DEC_FUNC extends AST_DEC
{
	public String returnTypeName;
	public String name;
	public AST_TYPE_NAME_LIST params;
	public AST_STMT_LIST body;
	
	public AST_DEC_FUNC(String returnTypeName, String name, AST_TYPE_NAME_LIST params, AST_STMT_LIST body)
	{
		SerialNumber = AST_Node_Serial_Number.getFresh();

		this.returnTypeName = returnTypeName;
		this.name = name;
		this.params = params;
		this.body = body;
	}

	public void PrintMe()
	{
		System.out.format("FUNC(%s):%s\n",name,returnTypeName);

		if (params != null) params.PrintMe();
		if (body != null) body.PrintMe();
		
		AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("FUNC(%s)\n:%s\n", name, returnTypeName));
		
		if (params != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, params.SerialNumber);
		if (body != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, body.SerialNumber);
	}

	public TYPE SemantMe()
	{
		TYPE returnType = SYMBOL_TABLE.getInstance().find(returnTypeName);
		if (returnType == null)
		{
			System.out.format(">> ERROR [%d:%d] non existing return type %s\n",6,6, returnType);
		}
		TYPE_LIST paramsTypes = params != null ? params.SemantMe(): null;
		SYMBOL_TABLE.getInstance().beginScope();
		if (body != null) { body.SemantMe(); }
		SYMBOL_TABLE.getInstance().endScope();
		SYMBOL_TABLE.getInstance().enter(name, new TYPE_FUNCTION(returnType, name, paramsTypes));
		return null;
	}
	
}
