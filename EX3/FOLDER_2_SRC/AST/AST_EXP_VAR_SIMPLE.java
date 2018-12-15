package AST;
import TYPES.*;
import SYMBOL_TABLE.*;

public class AST_EXP_VAR_SIMPLE extends AST_EXP_VAR
{
	public String name;
	
	public AST_EXP_VAR_SIMPLE(String name)
	{
		this.name = name;
	}

	public void PrintMe()
	{
		AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("SIMPLE\nVAR\n(%s)", name));
	}
	public TYPE SemantMe() throws Exception
	{
		return SYMBOL_TABLE.getInstance().find(name);
	}
}
