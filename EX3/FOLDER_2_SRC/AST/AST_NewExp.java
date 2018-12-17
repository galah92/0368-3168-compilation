package AST;
import TYPES.*;
import SymbolTable.*;

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
		logNode(String.format("NewExp\n%s", type));
        if (exp != null) logEdge(exp);
	}

	public Type SemantMe() throws Exception
	{
        if (exp != null && exp.SemantMe() != Type.INT) { throw new SemanticException(); }
        Type newExpType = SymbolTable.find(type);
        if (newExpType == null) { throw new SemanticException(); }
        return newExpType;
	}
}
