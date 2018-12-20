package AST;
import TYPES.*;
import pcomp.*;
import IR.*;


public class FuncDec extends ClassField
{
	public String retTypeName;
	public String funcName;
	public ParamsList params;
	public StmtList body;
	public TypeFunc funcType;
	
	public FuncDec(String retTypeName, String funcName, ParamsList params, StmtList body)
	{
		this.retTypeName = retTypeName;
		this.funcName = funcName;
		this.params = params;
		this.body = body;
	}

	public void logGraphviz()
	{
		if (params != null) params.logGraphviz();
		if (body != null) body.logGraphviz();

		logNode(String.format("FuncDec\n%s\n%s", retTypeName, funcName));
		if (params != null) logEdge(params);
		if (body != null) logEdge(body);
	}

	public TypeFunc SemantDeclaration() throws Exception
	{
		Type retType = retTypeName.equals(Type.VOID.name) ? Type.VOID : SymbolTable.find(retTypeName);
		TypeList paramsTypes = params != null ? params.SemantDeclaration(): null;
		Type func = SymbolTable.findInScope(funcName);
		if (func == null)
		{
			func = SymbolTable.find(funcName);
			if (func != null && (func instanceof TypeClass)) { throw new SemanticException(); }
		} else {
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
		if (body != null) { body.Semant(); 
		}
		SymbolTable.endScope();
	}

	public TypeFunc Semant() throws Exception
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

	public TempReg IRme()
	{
		IR.add(new IRcommand_Label("main"));		
		if (body != null) body.IRme();
		return null;
	}

}
