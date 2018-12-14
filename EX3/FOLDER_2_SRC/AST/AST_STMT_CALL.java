package AST;
import TYPES.*;

public class AST_STMT_CALL extends AST_STMT
{
	public AST_EXP_CALL callExp;

	public AST_STMT_CALL(AST_EXP_CALL callExp)
	{
		SerialNumber = AST_Node_Serial_Number.getFresh();

		this.callExp = callExp;
	}

	public void PrintMe()
	{
		callExp.PrintMe();

		AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("STMT\nCALL"));
		AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, callExp.SerialNumber);
	}

	public TYPE SemantMe() throws Exception
	{
		return callExp.SemantMe();
	}

}
