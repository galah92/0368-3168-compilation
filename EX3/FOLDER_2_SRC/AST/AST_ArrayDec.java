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
		logNode(String.format("ID1\n...->%s", arrName));
		logNode(String.format("ID2\n...->%s", arrTypeName));
	}

	public Type SemantMe() throws Exception
	{
		if (!SymbolTable.isGlobalScope()) { throw new SemanticException(); }
		if (SymbolTable.find(arrName) != null) { throw new SemanticException(); }
		Type arrType = SymbolTable.find(arrTypeName);
		if (arrType == null) { throw new SemanticException(); }
		SymbolTable.enter(arrName, new TypeArray(arrType, arrName));
		return null;
	}

}
