package AST;
import TYPES.*;
import SYMBOL_TABLE.*;

public class AST_ARRAY_DEC extends AST_Node
{
	public String arrName;
	public String arrType;

	public AST_ARRAY_DEC(String arrName, String arrType)
	{
		SerialNumber = AST_Node_Serial_Number.getFresh();
		System.out.format("arrayDec -> ARRAY ID(%s) EQ ID(%s) LBRACK RBRACK\n", arrName, arrType);
		this.arrName = arrName;
		this.arrType = arrType;
	}

	public void PrintMe()
	{
		System.out.println("AST ARRAY DEC");

		System.out.format("ID1(%s)\n", arrName);
		System.out.format("ID2(%s)\n", arrType);

		AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("ID1\n...->%s", arrName));
		AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("ID2\n...->%s", arrType));
	}

	public TYPE SemantMe()
	{
		if (SYMBOL_TABLE.getInstance().find(arrName) != null)
		{
			System.out.format(">> ERROR [%d:%d] variable %s already exists in scope\n",2,2,arrName);
		}
		TYPE elementType = SYMBOL_TABLE.getInstance().find(arrType);
		if (elementType == null)
		{
			System.out.format(">> ERROR [%d:%d] non existing type %s\n",6,6,arrType);
		}
		
		SYMBOL_TABLE.getInstance().enter(arrName, new TYPE_ARRAY(elementType, arrName));
		return null;
	}

}
