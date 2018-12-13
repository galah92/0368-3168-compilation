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
		TYPE t;
		TYPE returnType = null;
		TYPE_LIST type_list = null;

		returnType = SYMBOL_TABLE.getInstance().find(returnTypeName);
		if (returnType == null)
		{
			System.out.format(">> ERROR [%d:%d] non existing return type %s\n",6,6,returnType);				
		}

		SYMBOL_TABLE.getInstance().beginScope();
		for (AST_TYPE_NAME_LIST it = params; it  != null; it = it.tail)
		{
			t = SYMBOL_TABLE.getInstance().find(it.head.type);
			if (t == null)
			{
				System.out.format(">> ERROR [%d:%d] non existing type %s\n",2,2,it.head.type);				
			}
			else
			{
				type_list = new TYPE_LIST(t,type_list);
				SYMBOL_TABLE.getInstance().enter(it.head.name,t);
			}
		}
		body.SemantMe();
		SYMBOL_TABLE.getInstance().endScope();
		
		SYMBOL_TABLE.getInstance().enter(name, new TYPE_FUNCTION(returnType, name, type_list));
		return null;
	}
	
}
