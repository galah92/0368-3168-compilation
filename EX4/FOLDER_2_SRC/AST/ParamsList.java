package AST;
import TYPES.*;
import pcomp.*;

public class ParamsList extends Node
{
	public String paramTypeName;
	public String paramName;
	public ParamsList tail;

	public ParamsList(String paramTypeName, String paramName, ParamsList tail)
	{
		this.paramTypeName = paramTypeName;
		this.paramName = paramName;
		this.tail = tail;
	}

	public void logGraphviz()
	{
		if (tail != null) tail.logGraphviz();

		logNode(String.format("ParamsList\n%s\n%s", paramName, paramTypeName));
		if (tail != null) logEdge(tail);
	}

	public TypeList SemantDeclaration() throws Exception
	{
		Type paramType = SymbolTable.find(paramTypeName);
		if (paramType == null) { throw new SemanticException(); }
		return new TypeList(paramType, tail != null ? tail.SemantDeclaration() : null);
	}

	public void SemantBody() throws Exception
	{
		Type paramType = SymbolTable.find(paramTypeName);
		if (paramType == null) { throw new SemanticException(); }
		// if (SymbolTable.findInScope(paramName) != null) { throw new SemanticException(); }
		SymbolTable.enter(paramName, paramType);
		if (tail != null) tail.SemantBody();
	}

    public TypeList Semant() throws Exception
	{
		Type paramType = SymbolTable.find(paramTypeName);
		if (paramType == null) { throw new SemanticException(); }
		if (SymbolTable.findInScope(paramName) != null) { throw new SemanticException(); }
		SymbolTable.enter(paramName, paramType);
		return new TypeList(paramType, tail != null ? tail.Semant() : null);
	}
	
	@Override
	public TempReg toIR()
	{
		return null;
	}


}
