package AST;

import TYPES.*;
import SYMBOL_TABLE.*;

public class AST_EXP_VAR_FIELD extends AST_EXP_VAR
{
	public AST_EXP_VAR var;
	public String fieldName;
	
	public AST_EXP_VAR_FIELD(AST_EXP_VAR var,String fieldName)
	{
		SerialNumber = AST_Node_Serial_Number.getFresh();
		this.var = var;
		this.fieldName = fieldName;
	}

	public void PrintMe()
	{
		if (var != null) var.PrintMe();

		AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("FIELD\nVAR\n___.%s", fieldName));
		if (var != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, var.SerialNumber);
	}
	public TYPE SemantMe() throws Exception
	{
		TYPE t = var.SemantMe();
		if (!t.isClass()) { throw new Exception(); }
		TYPE_CLASS tc = (TYPE_CLASS)t;
		for (TYPE_LIST it = tc.data_members; it != null; it = it.tail)
		{
			if (it.head.name == fieldName) { return it.head; }
		}
		throw new Exception();
	}
}
