package AST;
import TYPES.*;
import SYMBOL_TABLE.*;

public class AST_VarDec extends AST_ClassField
{
	public String varTypeName;
	public String varName;
	public AST_Exp initVal;
	
	public AST_VarDec(String varTypeName, String varName, AST_Exp initVal)
	{
        this.varTypeName = varTypeName;
		this.varName = varName;
		this.initVal = initVal;
	}

	public void PrintMe()
	{
		if (initVal != null) initVal.PrintMe();
		AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("VarDec\n%s\n%s", varName, varTypeName));
		if (initVal != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, initVal.SerialNumber);
	}

	public TYPE SemantDeclaration() throws Exception
	{
		if (SYMBOL_TABLE.getInstance().findInScope(varName) != null) { throw new Exception(); }
		
		TYPE varType = SYMBOL_TABLE.getInstance().find(varTypeName);

		if (varType == null) { throw new Exception(); }
		SYMBOL_TABLE.getInstance().enter(varName, varType);
		return new TYPE_CLASS_VAR_DEC(varType, varName);
	}

	public TYPE SemantMe() throws Exception
	{
		TYPE varType = SemantDeclaration();
		return varType;
	}
	
}
