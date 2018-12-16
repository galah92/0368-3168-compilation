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

		AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("FuncDec\n%s\n%s", retTypeName, funcName));
		if (params != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, params.SerialNumber);
		if (body != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, body.SerialNumber);
	}

	public TypeFunc SemantDeclaration() throws Exception
	{
		// TODO: check for method overloading
		// specifically, we need to throw if:
		// names are equals && (params are different || retType is different)

		Type retType = SymbolTable.getInstance().find(retTypeName);
		if (retType == null) { throw new SemanticException(); }

		TypeList paramsTypes = params != null ? params.SemantDeclaration(): null;

		TypeFunc funcType = new TypeFunc(retType, funcName, paramsTypes);
		SymbolTable.getInstance().enter(funcName, funcType);
		return funcType;
	}

	public void SemantBody() throws Exception
	{
		SymbolTable.getInstance().beginScope();
		if (params != null) params.SemantBody();
		if (body != null) { body.SemantMe(); }
		SymbolTable.getInstance().endScope();
	}

	public TypeFunc SemantMe() throws Exception
	{
		TypeFunc funcType = SemantDeclaration();
		SemantBody();
		return funcType;
	}

}
