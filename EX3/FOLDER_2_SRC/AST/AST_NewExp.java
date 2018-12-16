package AST;
import TYPES.*;
import SYMBOL_TABLE.*;

public class AST_NewExp extends AST_Exp
{
    String type;
    AST_Exp exp;

    public AST_NewExp(String type, AST_Exp exp)
    {
		this.type = type;
        this.exp = exp;
    }

    public void PrintMe()
	{
		AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("NewExp\n%s", type));
        if (exp != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, exp.SerialNumber);
	}

	public TYPE SemantMe() throws Exception
	{
        if (exp != null && exp.SemantMe() != TYPE_INT.getInstance()) { throw new SemanticException(); }
        TYPE newExpType = SYMBOL_TABLE.getInstance().find(type);
        if (newExpType == null) { throw new SemanticException(); }
        return newExpType;
	}
}
