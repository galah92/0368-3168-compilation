package AST;
import TYPES.*;
import SYMBOL_TABLE.*;

public class AST_DEC_CLASS extends AST_DEC
{
	public String name;
	public AST_TYPE_NAME_LIST data_members;
	
	public AST_DEC_CLASS(String name, AST_TYPE_NAME_LIST data_members)
	{
		SerialNumber = AST_Node_Serial_Number.getFresh();
	
		this.name = name;
		this.data_members = data_members;
	}

	public void PrintMe()
	{
		System.out.format("CLASS DEC = %s\n",name);
		if (data_members != null) data_members.PrintMe();
		
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("CLASS\n%s",name));
		
		AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, data_members.SerialNumber);		
	}
	
	public TYPE SemantMe() throws Exception
	{
		SYMBOL_TABLE.getInstance().beginScope();
		TYPE_LIST dataMembersTypes = data_members.SemantMe();
		SYMBOL_TABLE.getInstance().endScope();
		SYMBOL_TABLE.getInstance().enter(name, new TYPE_CLASS(null, name, dataMembersTypes));
		return null;		
	}
}
