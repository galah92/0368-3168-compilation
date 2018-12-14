package AST;
import TYPES.*;
import SYMBOL_TABLE.*;

public class AST_EXP_VAR_SIMPLE extends AST_EXP_VAR
{
	public String name;
	
	public AST_EXP_VAR_SIMPLE(String name)
	{
		SerialNumber = AST_Node_Serial_Number.getFresh();

		System.out.format("====================== var -> ID( %s )\n",name);
		this.name = name;
	}

	public void PrintMe()
	{
		System.out.format("AST NODE SIMPLE VAR( %s )\n",name);

		AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("SIMPLE\nVAR\n(%s)", name));
	}
	public TYPE SemantMe() throws Exception
	{
		return SYMBOL_TABLE.getInstance().find(name);
	}
}
