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
	}

	public void PrintMe()
	{
		System.out.format("FIELD\nNAME\n(___.%s)\n",fieldName);


		if (var != null) var.PrintMe();

		AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("FIELD\nVAR\n___.%s", fieldName));
		if (var != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, var.SerialNumber);
	}
	public TYPE SemantMe() throws Exception
	{
		TYPE t = null;
		TYPE_CLASS tc = null;
		
		if (var != null) t = var.SemantMe();
		
		if (t.isClass() == false)
		{
			System.out.format(">> ERROR [%d:%d] access %s field of a non-class variable\n",6,6,fieldName);
			throw new Exception();
		}
		else
		{
			tc = (TYPE_CLASS) t;
		}
		for (TYPE_LIST it=tc.data_members;it != null;it=it.tail)
		{
			if (it.head.name == fieldName) { return it.head; }
		}
		System.out.format(">> ERROR [%d:%d] field %s does not exist in class\n",6,6,fieldName);							
		throw new Exception();
	}
}
