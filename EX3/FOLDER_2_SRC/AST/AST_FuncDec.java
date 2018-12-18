package AST;
import TYPES.*;

import SymbolTable.*;

public class AST_FuncDec extends AST_ClassField
{
	public String retTypeName;
	public String funcName;
	public AST_ParamsList params;
	public AST_StmtList body;
	
	public AST_FuncDec(String retTypeName, String funcName, AST_ParamsList params, AST_StmtList body)
	{
		this.retTypeName = retTypeName;
		this.funcName = funcName;
		this.params = params;
		this.body = body;
	}

	public void PrintMe()
	{
		if (params != null) params.PrintMe();
		if (body != null) body.PrintMe();

		logNode(String.format("FuncDec\n%s\n%s", retTypeName, funcName));
		if (params != null) logEdge(params);
		if (body != null) logEdge(body);
	}

	public TypeFunc SemantDeclaration() throws Exception
	{
		Type retType = retTypeName.equals(Type.VOID.name) ? Type.VOID : SymbolTable.find(retTypeName);

		TypeList paramsTypes = params != null ? params.SemantDeclaration(): null;
		Type func = SymbolTable.findInScope(funcName);
		func = func == null ? SymbolTable.find(funcName) : func;
		if (func != null)
		{
			OverrideFuncDecCheck(func, retType, paramsTypes);
		}
		TypeFunc funcType = new TypeFunc(retType, funcName, paramsTypes);
		SymbolTable.enter(funcName, funcType);
		return funcType;
	}

	public void SemantBody() throws Exception
	{
		SymbolTable.beginScope(Type.Scope.FUNC);
		if (params != null) params.SemantBody();
		if (body != null) { body.SemantMe(); }
		SymbolTable.endScope();
	}

	public TypeFunc SemantMe() throws Exception
	{
		TypeFunc funcType = SemantDeclaration();
		SemantBody();
		return funcType;
	}

	public void OverrideFuncDecCheck(Type func, Type retType, TypeList argsTypes) throws Exception
	{
		if (!(func instanceof TypeFunc)) { throw new SemanticException(); }
		TypeFunc overloadedFuncType = (TypeFunc)func;
		if (!(SymbolTable.isInScope(Type.Scope.CLASS))) { throw new SemanticException(); }
		if (overloadedFuncType.retType != retType) { throw new SemanticException(); }
		if (!(overloadedFuncType.isValidArgs(argsTypes))) { throw new SemanticException(); }
	}

}
