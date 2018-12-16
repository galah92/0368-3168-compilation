package AST;
import TYPES.*;
import SymbolTable.*;

public class AST_ParamsList extends AST_Node
{
	public String paramTypeName;
	public String paramName;
	public AST_ParamsList tail;

	public AST_ParamsList(String paramTypeName, String paramName, AST_ParamsList tail)
	{
		this.paramTypeName = paramTypeName;
		this.paramName = paramName;
		this.tail = tail;
	}

	public void PrintMe()
	{
		if (tail != null) tail.PrintMe();

		ASTGraphviz.logNode(SerialNumber, String.format("ParamsList\n%s\n%s", paramName, paramTypeName));
		if (tail != null) ASTGraphviz.logEdge(SerialNumber, tail.SerialNumber);
	}

	public TypeList SemantDeclaration() throws Exception
	{
		Type paramType = SymbolTable.getInstance().find(paramTypeName);
		if (paramType == null) { throw new SemanticException(); }
		return new TypeList(paramType, tail != null ? tail.SemantDeclaration() : null);
	}

	public void SemantBody() throws Exception
	{
		Type paramType = SymbolTable.getInstance().find(paramTypeName);
		if (paramType == null) { throw new SemanticException(); }
		if (SymbolTable.getInstance().findInScope(paramName) != null) { throw new SemanticException(); }
		SymbolTable.getInstance().enter(paramName, paramType);
		if (tail != null) tail.SemantBody();
	}

    public TypeList SemantMe() throws Exception
	{
		Type paramType = SymbolTable.getInstance().find(paramTypeName);
		if (paramType == null) { throw new SemanticException(); }
		if (SymbolTable.getInstance().findInScope(paramName) != null) { throw new SemanticException(); }
		SymbolTable.getInstance().enter(paramName, paramType);
		return new TypeList(paramType, tail != null ? tail.SemantMe() : null);
	}
}
