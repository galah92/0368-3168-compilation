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
	}
}
