package AST;

import pcomp.*;


public class ParamsList extends Node
{
	public String paramTypeName;
	public String paramName;
	public ParamsList next;

	public ParamsList(String paramTypeName, String paramName, ParamsList next)
	{
		this.paramTypeName = paramTypeName;
		this.paramName = paramName;
		this.next = next;
	}

	public void logGraphviz()
	{
		if (next != null) next.logGraphviz();

		logNode(String.format("ParamsList\n%s\n%s", paramName, paramTypeName));
		if (next != null) logEdge(next);
	}

	public TypeList SemantDeclaration() throws Exception
	{
		Type paramType = SymbolTable.find(paramTypeName);
		if (paramType == null) { throw new SemanticException(); }
		return new TypeList(paramType, next != null ? next.SemantDeclaration() : null);
	}

	public void SemantBody() throws Exception
	{
		Type paramType = SymbolTable.find(paramTypeName);
		if (paramType == null) { throw new SemanticException(); }
		// if (SymbolTable.findInScope(paramName) != null) { throw new SemanticException(); }
		SymbolTable.enter(paramName, paramType);
		if (next != null) next.SemantBody();
	}

    public TypeList Semant() throws Exception
	{
		Type paramType = SymbolTable.find(paramTypeName);
		if (paramType == null) { throw new SemanticException(); }
		if (SymbolTable.findInScope(paramName) != null) { throw new SemanticException(); }
		SymbolTable.enter(paramName, paramType);
		return new TypeList(paramType, next != null ? next.Semant() : null);
	}
	
	@Override
	public TempReg toIR()
	{
		return null;
	}


}
