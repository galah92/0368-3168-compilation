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

	public void PrintMe()
	{
		className.PrintMe();
		ASTGraphviz.logNode(SerialNumber, String.format("VarClassField\n%s", fieldName));
		ASTGraphviz.logEdge(SerialNumber, className.SerialNumber);
	}
	public Type SemantMe() throws Exception
	{
		Type t = className.SemantMe();
		if (!(t instanceof TypeClass)) { throw new SemanticException(); }
		TypeClass typeClass = (TypeClass)t;
		return typeClass.getVarField(fieldName);
	}
}
