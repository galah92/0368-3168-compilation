package AST;
import TYPES.*;

public class AST_ExpBinOp extends AST_Exp
{
	char op;
	public AST_Exp left;
	public AST_Exp right;

	public AST_ExpBinOp(AST_Exp left, AST_Exp right, char op)
	{
		this.left = left;
		this.right = right;
		this.op = op;
	}

	public void PrintMe()
	{
		if (left != null) left.PrintMe();
		if (right != null) right.PrintMe();

		logNode(String.format("ExpBinOp\n%s", op));
		if (left  != null) logEdge(left);
		if (right != null) logEdge(right);
	}

	public Type SemantMe() throws Exception
	{
		Type t1 = left.SemantMe();
		Type t2 = right.SemantMe();

		if (t1 == TypeInt.getInstance() && t2 == TypeInt.getInstance())
		{
			return TypeInt.getInstance();
		}
		else if ((op == '+') && (t1 == TypeString.getInstance()) && (t2 == TypeString.getInstance()))
		{
			return TypeString.getInstance();
		}
		else if (op == '=')
		{
			if (t1 == t2) { return t1; }
			if ((t1 == null || t2 == null))
			{
				if (t1 instanceof TypeClass || t1 instanceof TypeArray) { return TypeInt.getInstance(); }
				if (t2 instanceof TypeClass || t2 instanceof TypeArray) { return TypeInt.getInstance(); }
				throw new SemanticException();
			}
		}
		throw new SemanticException(t1 + ", " + t2);
	}

}
