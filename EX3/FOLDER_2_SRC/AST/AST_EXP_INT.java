package AST;

import TYPES.*;

public class AST_EXP_INT extends AST_EXP
{
	public int value;
	
	public AST_EXP_INT(int value)
	{
		this.value = value;
	}

	public void PrintMe()
	{
		AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("INT(%d)",value));
	}
	
	public TYPE SemantMe() throws Exception
	{
		return TYPE_INT.getInstance();
	}
}
