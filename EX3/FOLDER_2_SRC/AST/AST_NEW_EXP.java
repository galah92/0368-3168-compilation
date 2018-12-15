package AST;
import TYPES.*;

public class AST_NEW_EXP extends AST_EXP
{

    String type;
    AST_EXP exp;

    public AST_NEW_EXP(String type, AST_EXP exp)
    {
		this.type = type;
        this.exp = exp;
    }

    public AST_NEW_EXP(String type)
    {
		this.type = type;
        this.exp = null;
    }

    public void PrintMe()
	{
		AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("INT(%d)", type));
        if (exp != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, exp.SerialNumber);
	}

	public TYPE SemantMe() throws Exception
	{
        // TODO: complete. a new TYPE_NEW_EXP might be necessary
		return null;
	}
}
