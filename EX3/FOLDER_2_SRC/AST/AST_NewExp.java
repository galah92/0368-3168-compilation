package AST;
import TYPES.*;
import SymbolTable.*;

public class AST_NewExp extends AST_Exp
{
    String Type;
    AST_Exp exp;

    public AST_NewExp(String Type, AST_Exp exp)
    {
		this.Type = Type;
        this.exp = exp;
    }

    public void PrintMe()
	{
		logNode(String.format("NewExp\n%s", Type));
        if (exp != null) logEdge(exp);
	}

	public Type SemantMe() throws Exception
	{
        if (exp != null && exp.SemantMe() != TypeInt.getInstance()) { throw new SemanticException(); }
        Type newExpType = SymbolTable.getInstance().find(Type);
        if (newExpType == null) { throw new SemanticException(); }
        return newExpType;
	}
}
