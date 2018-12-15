package AST;
import TYPES.*;
import SYMBOL_TABLE.*;

public class AST_VarClassField extends AST_Var
{
	public AST_Var var;
	public String fieldName;
	
	public AST_VarClassField(AST_Var var, String fieldName)
	{
		this.var = var;
		this.fieldName = fieldName;
	}

	public void PrintMe()
	{
		var.PrintMe();
		AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("VarClassField\n%s", fieldName));
		AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, var.SerialNumber);
	}
	public TYPE SemantMe() throws Exception
	{
		TYPE varType = var.SemantMe();
		if (!(varType instanceof TYPE_CLASS)) { throw new Exception(); }
		TYPE_CLASS tc = (TYPE_CLASS)varType;
		for (TYPE_LIST it = tc.data_members; it != null; it = it.tail)
		{
			if (it.head.name == fieldName) { return it.head; }
		}
		throw new Exception();
	}
}
