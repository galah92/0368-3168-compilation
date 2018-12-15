package AST;
import TYPES.*;
import SYMBOL_TABLE.*;

public class AST_ArrayDec extends AST_DEC
{
	public String arrName;
	public String arrTypeName;

	public AST_ArrayDec(String arrName, String arrTypeName)
	{
		this.arrName = arrName;
		this.arrTypeName = arrTypeName;
	}

	public void PrintMe()
	{
		AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("ID1\n...->%s", arrName));
		AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("ID2\n...->%s", arrTypeName));
	}

	public TYPE SemantMe() throws Exception
	{
		if (!SYMBOL_TABLE.getInstance().isGlobalScope()) { throw new Exception(); }
		if (SYMBOL_TABLE.getInstance().find(arrName) != null) { throw new Exception(); }
		TYPE arrType = SYMBOL_TABLE.getInstance().find(arrTypeName);
		if (arrType == null) { throw new Exception(); }
		SYMBOL_TABLE.getInstance().enter(arrName, new TYPE_ARRAY(arrType, arrName));
		return null;
	}

}
