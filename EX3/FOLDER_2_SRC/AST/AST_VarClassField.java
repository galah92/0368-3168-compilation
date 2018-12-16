package AST;
import TYPES.*;
import SYMBOL_TABLE.*;

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
		AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("VarClassField\n%s", fieldName));
		AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, className.SerialNumber);
	}
	public TYPE SemantMe() throws Exception
	{
		TYPE t = className.SemantMe();
		if (!(t instanceof TYPE_CLASS)) { throw new SemanticException(); }
		TYPE_CLASS typeClass = (TYPE_CLASS)t;
		
		return typeClass.getVarField(fieldName);
		// for (TYPE_LIST it = typeClass.fields; it != null; it = it.tail)
		// {
		// 	if (it.head instanceof TYPE_CLASS_VAR_DEC)
		// 	{
		// 		TYPE_CLASS_VAR_DEC classVarType = (TYPE_CLASS_VAR_DEC)it.head;
		// 		if (fieldName.equals(classVarType.name)) { return classVarType.varType; }
		// 	}
		// }
		// throw new SemanticException();
	}
}
