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

    public void toGraphviz()
	{
		logNode(String.format("NewExp\n%s", type));
        if (exp != null) logEdge(exp);
	}

	public Type Semant() throws Exception
	{
        // type must be known
        Type newExpType = SymbolTable.find(type);
        if (newExpType == null) { throw new SemanticException(); }

        if (exp != null) // new array
        {
            if (exp.Semant() != Type.INT) { throw new SemanticException(); }
        }
        else // new class
        {
            if (newExpType == Type.INT || newExpType == Type.STRING) { throw new SemanticException(); }
        }

        return newExpType;
	}
}
