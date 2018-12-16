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

	public Type SemantDeclaration() throws Exception
	{
		if (SymbolTable.getInstance().findInScope(varName) != null) { throw new SemanticException(); }
		
		Type varType = SymbolTable.getInstance().find(varTypeName);
		if (varType == null) { throw new SemanticException(); }

		Type initValType = initVal != null ? initVal.SemantMe() : null;
		if (initValType != null)
		{
			if (varType instanceof TypeArray)
			{
				if (((TypeArray)varType).elementType != initValType) { throw new SemanticException(varType + ", " + initValType); }
			}
			else if (initValType instanceof TypeClass)
			{
				if (!((TypeClass)initValType).isInheritingFrom(varType.name)) { throw new SemanticException(varType + ", " + initValType); }
			}
			else
			{
				if (initValType != varType) { throw new SemanticException(); }
			}
		}

		SymbolTable.getInstance().enter(varName, varType);
		return new TypeClassVar(varType, varName);
	}

	public void SemantBody() throws Exception
	{
		// nothing to do here
	}

	public Type SemantMe() throws Exception
	{
		Type varType = SemantDeclaration();
		return varType;
	}
	
}
