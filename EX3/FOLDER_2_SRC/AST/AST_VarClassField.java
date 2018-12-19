package AST;
import TYPES.*;
import SymbolTable.*;

public class AST_VarClassField extends AST_Var
{
	public AST_Var className;
	public String fieldName;
	
	public AST_VarClassField(AST_Var className, String fieldName)
	{
		this.className = className;
		this.fieldName = fieldName;
	}

	public void toGraphviz()
	{
		className.toGraphviz();
		logNode(String.format("VarClassField\n%s", fieldName));
		logEdge(className);
	}
	public Type Semant() throws Exception
	{
		Type t = className.Semant();
		if (!(t instanceof TypeClass)) { throw new SemanticException(); }
		TypeClass typeClass = (TypeClass)t;
		return typeClass.getVarField(fieldName);
	}
}
