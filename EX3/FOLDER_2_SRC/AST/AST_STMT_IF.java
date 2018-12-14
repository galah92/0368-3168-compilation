package AST;
import TYPES.*;
import SYMBOL_TABLE.*;

public class AST_STMT_IF extends AST_STMT
{
	public AST_EXP cond;
	public AST_STMT_LIST body;

	public AST_STMT_IF(AST_EXP cond, AST_STMT_LIST body)
	{
		SerialNumber = AST_Node_Serial_Number.getFresh();

		this.cond = cond;
		this.body = body;
	}

	public void PrintMe()
	{
		System.out.print("AST NODE STMT IF\n");

		if (cond != null) cond.PrintMe();
		if (body != null) body.PrintMe();

		AST_GRAPHVIZ.getInstance().logNode(SerialNumber, "IF (left)\nTHEN right");
		
		if (cond != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,cond.SerialNumber);
		if (body != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,body.SerialNumber);
	}

	public TYPE SemantMe() throws Exception
	{
		if (cond.SemantMe() != TYPE_INT.getInstance())
		{
			System.out.format(">> ERROR [%d:%d] condition inside IF is not integral\n",2,2);
		}

		SYMBOL_TABLE.getInstance().beginScope();
		body.SemantMe();
		SYMBOL_TABLE.getInstance().endScope();
		
		return null;
	}	
}
