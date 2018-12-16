package AST;
import TYPES.*;
import SymbolTable.*;

public class AST_ArrayDec extends AST_Dec
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

	public Type SemantMe() throws Exception
	{
		if (!SymbolTable.getInstance().isGlobalScope()) { throw new SemanticException(); }
		if (SymbolTable.getInstance().find(arrName) != null) { throw new SemanticException(); }
		Type arrType = SymbolTable.getInstance().find(arrTypeName);
		if (arrType == null) { throw new SemanticException(); }
		SymbolTable.getInstance().enter(arrName, new TypeArray(arrType, arrName));
		return null;
	}

}
