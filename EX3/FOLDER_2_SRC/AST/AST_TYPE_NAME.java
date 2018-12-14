package AST;

import TYPES.*;
import SYMBOL_TABLE.*;

public class AST_TYPE_NAME extends AST_Node
{
	public String type;
	public String name;
	
	public AST_TYPE_NAME(String type,String name)
	{
		SerialNumber = AST_Node_Serial_Number.getFresh();
	
		this.type = type;
		this.name = name;
	}

	public void PrintMe()
	{
		System.out.format("NAME(%s):TYPE(%s)\n",name,type);

		AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("NAME:TYPE\n%s:%s", name, type));
	}

	public TYPE SemantMe() throws Exception
	{
		TYPE t = SYMBOL_TABLE.getInstance().find(type);
		if (t == null)
		{
			System.out.format(">> ERROR [%d:%d] non existing type %s\n",2,2, type);
			throw new Exception();
		}
		if (SYMBOL_TABLE.getInstance().find(name) != null)
		{
			System.out.format(">> ERROR [%d:%d] variable %s already exists in scope\n",2,2, name);
			throw new Exception();
		}
		SYMBOL_TABLE.getInstance().enter(name, t);
		return t;
	}	
}
