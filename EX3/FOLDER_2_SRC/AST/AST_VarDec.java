package AST;
import TYPES.*;
import SymbolTable.*;

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
		if (SymbolTable.getInstance().findInScope(varName) != null) { throw new SemanticException(); }
		
		TYPE varType = SymbolTable.getInstance().find(varTypeName);
		if (varType == null) { throw new SemanticException(); }

		TYPE initValType = initVal != null ? initVal.SemantMe() : null;
		if (initValType != null)
		{
			if (varType instanceof TYPE_ARRAY)
			{
				if (((TYPE_ARRAY)varType).elementType != initValType) { throw new SemanticException(varType + ", " + initValType); }
			}
			else if (initValType instanceof TYPE_CLASS)
			{
				if (!((TYPE_CLASS)initValType).isInheritingFrom(varType.name)) { throw new SemanticException(varType + ", " + initValType); }
			}
			else
			{
				if (initValType != varType) { throw new SemanticException(); }
			}
		}

		SymbolTable.getInstance().enter(varName, varType);
		return new TYPE_CLASS_VAR_DEC(varType, varName);
	}

	public void SemantBody() throws Exception
	{
		// nothing to do here
	}

	public TYPE SemantMe() throws Exception
	{
		TYPE varType = SemantDeclaration();
		return varType;
	}
	
}
