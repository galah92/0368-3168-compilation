package AST;
import TYPES.*;
import SYMBOL_TABLE.*;

public class AST_ParamsList extends AST_Node
{
	public String paramTypeName;
	public String paramName;
	public AST_ParamsList tail;

	public AST_ParamsList(String paramTypeName, String paramName, AST_ParamsList tail)
	{
		this.paramTypeName = paramTypeName;
		this.paramName = paramName;
		this.tail = tail;
	}

	public void PrintMe()
	{
		if (tail != null) tail.PrintMe();

		AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("ParamsList\n%s\n%s", paramName, paramTypeName));
		if (tail != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, tail.SerialNumber);
	}

    public TYPE_LIST SemantMe() throws Exception
	{
        TYPE paramType = SYMBOL_TABLE.getInstance().find(paramTypeName);
		if (paramType == null) { throw new Exception(); }
		if (SYMBOL_TABLE.getInstance().findInScope(paramName) != null) { throw new Exception(); }
		SYMBOL_TABLE.getInstance().enter(paramName, paramType);
		return new TYPE_LIST(paramType, tail != null ? tail.SemantMe() : null);
	}
}
