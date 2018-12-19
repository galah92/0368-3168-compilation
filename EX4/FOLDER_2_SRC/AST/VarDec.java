package AST;
import TYPES.*;
import SymbolStack.*;

public class VarDec extends ClassField
{
	public String varTypeName;
	public String varName;
	public Exp initVal;

	public VarDec(String varTypeName, String varName, Exp initVal)
	{
        this.varTypeName = varTypeName;
		this.varName = varName;
		this.initVal = initVal;
	}

	public void toGraphviz()
	{
		if (initVal != null) initVal.toGraphviz();
		logNode(String.format("VarDec\n%s\n%s", varName, varTypeName));
		if (initVal != null) logEdge(initVal);
	}

	public Type SemantDeclaration() throws Exception
	{
		if (SymbolStack.findInScope(varName) != null) { throw new SemanticException("variable name already defined"); }
		if (SymbolStack.findTypeName(varName) != null) { throw new SemanticException("variable name defined as type"); }

		Type varType = SymbolStack.findTypeName(varTypeName);
		if (varType == null) { throw new SemanticException("variable type not defined"); }

		TypeClass classType = SymbolStack.findClass();
		boolean isSemantingClass = classType != null && classType.fields == null;

		Type initValType = initVal != null ? initVal.Semant() : null;
		if (initValType != null)
		{
			if (initValType == Type.NIL)
			{
				if (varType == Type.INT || varType == Type.STRING) { throw new SemanticException(); }
				SymbolStack.enter(varName, varType); //TODO: CHeck if neccessry
				return new TypeClassVar(varType, varName);
			}
			if (isSemantingClass) // we're in the middle of ClassDec
			{
				if (!(initVal instanceof ExpPrimitive)) { throw new SemanticException(varType + ", " + initValType); }
			}
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

		SymbolStack.enter(varName, varType);
		return new TypeClassVar(varType, varName);
	}

	public void SemantBody() throws Exception
	{
		// nothing to do here
	}

	public Type Semant() throws Exception
	{
		Type varType = SemantDeclaration();
		return varType;
	}

}
