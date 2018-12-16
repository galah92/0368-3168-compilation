package AST;
import TYPES.*;

public class AST_VarArrayElement extends AST_Var
{
	public AST_Var var;
	public AST_Exp index;

	public AST_VarArrayElement(AST_Var var, AST_Exp index)
	{
		this.var = var;
		this.index = index;
	}

	public void PrintMe()
	{
		var.PrintMe();
		index.PrintMe();
		AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("VarArrayElement\n%s", index));
		AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, var.SerialNumber);
	}

	public Type SemantMe() throws Exception
	{
		if (index.SemantMe() != TypeInt.getInstance()) { throw new SemanticException(); }
		Type arrType = var.SemantMe();
		if (!(arrType instanceof TypeArray)) { throw new SemanticException(); }
		return ((TypeArray)arrType).elementType;
	}

}
