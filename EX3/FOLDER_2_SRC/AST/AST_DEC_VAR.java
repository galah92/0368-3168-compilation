package AST;
import TYPES.*;
import SYMBOL_TABLE.*;

public class AST_DEC_VAR extends AST_DEC
{
	public String type;
	public String name;
	public AST_EXP initialValue;
	
	public AST_DEC_VAR(String type,String name, AST_EXP initialValue)
	{
		SerialNumber = AST_Node_Serial_Number.getFresh();

		this.type = type;
		this.name = name;
		this.initialValue = initialValue;
	}

	public void PrintMe()
	{
		if (initialValue != null) System.out.format("VAR-DEC(%s):%s := initialValue\n",name,type);
		if (initialValue == null) System.out.format("VAR-DEC(%s):%s                \n",name,type);

		if (initialValue != null) initialValue.PrintMe();

		AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("VAR\nDEC(%s)\n:%s", name, type));

		if (initialValue != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,initialValue.SerialNumber);		
			
	}

	public TYPE SemantMe() throws Exception
	{
		TYPE t = SYMBOL_TABLE.getInstance().find(type);
		if (t == null)
		{
			System.out.format(">> ERROR [%d:%d] non existing type %s\n",2,2, type);
			throw new Exception();
		}	
		if (SYMBOL_TABLE.getInstance().findInScope(name) != null)
		{
			System.out.format(">> ERROR [%d:%d] variable %s already exists in scope\n",2,2, name);
			throw new Exception();
		}
		SYMBOL_TABLE.getInstance().enter(name, t);
		return null;		
	}
	
}
