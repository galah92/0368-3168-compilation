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
		AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("NAME:TYPE\n%s:%s", name, type));
	}

	public TYPE SemantMe() throws Exception
	{
		TYPE t = SYMBOL_TABLE.getInstance().find(type);
		if (t == null) { throw new Exception(); }
		if (SYMBOL_TABLE.getInstance().findInScope(name) != null) { throw new Exception(); }
		SYMBOL_TABLE.getInstance().enter(name, t);
		return t;
	}	
}
