package AST;
import TYPES.*;
import SYMBOL_TABLE.*;

public class AST_DEC_CLASS extends AST_DEC
{
	public String name;
	public String base;
	public AST_TYPE_NAME_LIST dataMembers;
	
	public AST_DEC_CLASS(String name, String base, AST_TYPE_NAME_LIST dataMembers)
	{
		SerialNumber = AST_Node_Serial_Number.getFresh();
		this.name = name;
		this.base = base;
		this.dataMembers = dataMembers;
	}

	public void PrintMe()
	{
		if (dataMembers != null) dataMembers.PrintMe();	
		AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("CLASS\n%s",name));
		AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, dataMembers.SerialNumber);
	}
	
	public TYPE SemantMe() throws Exception
	{
		TYPE_CLASS baseType = null;
		if (base != null)
		{
			TYPE t = SYMBOL_TABLE.getInstance().find(base);
			if (!(t instanceof TYPE_CLASS)) { throw new Exception(); }
			baseType = (TYPE_CLASS)t;
		}

		if (!SYMBOL_TABLE.getInstance().isGlobalScope()) { throw new Exception(); }
		SYMBOL_TABLE.getInstance().beginScope();
		TYPE_LIST dataMembersTypes = dataMembers.SemantMe();
		SYMBOL_TABLE.getInstance().endScope();

		SYMBOL_TABLE.getInstance().enter(name, new TYPE_CLASS(baseType, name, dataMembersTypes));
		return null;		
	}
}
