package AST;
import TYPES.*;

public class AST_STMT_CALL extends AST_STMT
{
	public AST_ExpCall callExp;

	public AST_STMT_CALL(AST_ExpCall callExp)
	{
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
