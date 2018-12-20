package AST;
import TYPES.*;
import pcomp.*;
import IR.*;


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

	public void logGraphviz()
	{
		if (initVal != null) initVal.logGraphviz();
		logNode(String.format("VarDec\n%s\n%s", varName, varTypeName));
		if (initVal != null) logEdge(initVal);
	}

	public Type SemantDeclaration() throws Exception
	{
		if (SymbolTable.findInScope(varName) != null) { throw new SemanticException("variable name already defined"); }
		if (SymbolTable.findTypeName(varName) != null) { throw new SemanticException("variable name defined as type"); }

		Type varType = SymbolTable.findTypeName(varTypeName);
		if (varType == null) { throw new SemanticException("variable type not defined"); }

		TypeClass classType = SymbolTable.findClass();
		boolean isSemantingClass = classType != null && classType.fields == null;

		Type initValType = initVal != null ? initVal.Semant() : null;
		if (initValType != null)
		{
			if (initValType == Type.NIL)
			{
				if (varType == Type.INT || varType == Type.STRING) { throw new SemanticException(); }
				SymbolTable.enter(varName, varType); //TODO: CHeck if neccessry
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

		SymbolTable.enter(varName, varType);
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

	public TempReg IRme()
	{
		IR.add(new IRcommand_Allocate(varName));
		
		if (initVal != null)
		{
			IR.add(new IRcommand_Store(varName, initVal.IRme()));
		}
		return null;
	}

}
