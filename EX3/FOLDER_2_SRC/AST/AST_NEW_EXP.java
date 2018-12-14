package AST;
import TYPES.*;

public abstract class AST_NEW_EXP extends AST_Node
{

    String name;
    AST_EXP exp;

    public AST_NEW_EXP(String name, AST_EXP exp)
    {
        SerialNumber = AST_Node_Serial_Number.getFresh();
		this.name = name;
        this.exp = exp;
    }

    public void PrintMe()
	{
		System.out.format("AST NODE NEW_EXP( %d )\n", name);

		AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("INT(%d)", name));
        if (exp != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, exp.SerialNumber);
	}

	public TYPE SemantMe()
	{
        // TODO: complete. a new TYPE_NEW_EXP might be necessary
		return null;
	}
}
