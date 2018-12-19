package AST;
import TYPES.*;
import SymbolTable.*;

public class VarClassField extends Var
{
	public Var className;
	public String fieldName;
	
	public VarClassField(Var className, String fieldName)
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
