package AST;
import TYPES.*;
import SymbolStack.*;


public class ArrayDec extends Dec
{
	public String arrName;
	public String arrTypeName;

	public ArrayDec(String arrName, String arrTypeName)
	{
		this.arrName = arrName;
		this.arrTypeName = arrTypeName;
	}

	public void toGraphviz()
	{
		logNode(String.format("ArrayDec\n%s\n%s", arrName, arrTypeName));
	}

	public Type Semant() throws Exception
	{
		if (!SymbolStack.isGlobalScope()) { throw new SemanticException(); }
		if (SymbolStack.find(arrName) != null) { throw new SemanticException(); }
		Type arrType = SymbolStack.findTypeName(arrTypeName);
		if (arrType == null) { throw new SemanticException(); }
		SymbolStack.enter(arrName, new TypeArray(arrType, arrName));
		return null;
	}

}
