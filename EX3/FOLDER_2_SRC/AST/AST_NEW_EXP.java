package AST;
import TYPES.*;

public class AST_NEW_EXP extends AST_EXP
{

    String type;
    AST_EXP exp;

    public AST_NEW_EXP(String type, AST_EXP exp)
    {
        SerialNumber = AST_Node_Serial_Number.getFresh();
		this.type = type;
        this.exp = exp;
    }

    public AST_NEW_EXP(String type)
    {
        SerialNumber = AST_Node_Serial_Number.getFresh();
		this.type = type;
        this.exp = null;
    }

    public void PrintMe()
	{
		System.out.format("AST NODE NEW_EXP( %d )\n", type);

		AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("INT(%d)", type));
        if (exp != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, exp.SerialNumber);
	}

	public TYPE SemantMe() throws Exception
	{
        // TODO: complete. a new TYPE_NEW_EXP might be necessary
		return null;
	}
}
