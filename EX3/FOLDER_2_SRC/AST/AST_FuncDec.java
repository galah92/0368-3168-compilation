package AST;
import TYPES.*;
import SymbolTable.*;


public class AST_FuncDec extends AST_ClassField
{
	public String retTypeName;
	public String funcName;
	public AST_ParamsList params;
	public AST_StmtList body;
	public TypeFunc funcType;
	
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
		if (func != null)
		{
			if (!(func instanceof TypeFunc)) { throw new SemanticException(); }
			if (!(SymbolTable.isInScope(Type.Scope.CLASS))) { throw new SemanticException(); }
			TypeClass currentClass = SymbolTable.findClass();
			TypeClass funcClass = ((TypeFunc)func).cls;
			if (currentClass == funcClass) { throw new SemanticException(); } // func declerated before
			OverrideFuncDecCheck(func, retType, paramsTypes);
		}
		funcType = new TypeFunc(retType, funcName, paramsTypes, SymbolTable.findClass());
		SymbolTable.enter(funcName, funcType);
		return funcType;
	}

	public void SemantBody() throws Exception
	{
		SymbolTable.beginScope(Type.Scope.FUNC);
		SymbolTable.enter(funcName, funcType);
		if (params != null) params.SemantBody();
		if (body != null) { body.SemantMe(); 
		}
		SymbolTable.endScope();
	}

	public TypeFunc SemantMe() throws Exception
	{
		funcType = SemantDeclaration();
		SemantBody();
		return funcType;
	}

	public void OverrideFuncDecCheck(Type func, Type retType, TypeList argsTypes) throws Exception
	{
		TypeFunc overloadedFuncType = (TypeFunc)func;
		if (overloadedFuncType.retType != retType) { throw new SemanticException(); }
		if (!(overloadedFuncType.isValidArgs(argsTypes))) { throw new SemanticException(); }
	}

}
