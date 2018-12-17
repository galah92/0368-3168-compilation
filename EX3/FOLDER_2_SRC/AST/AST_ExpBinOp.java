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

		if (t1 == Type.INT && t2 == Type.INT)
		{
			return Type.INT;
		}
		else if ((op == '+') && (t1 == Type.STRING) && (t2 == Type.STRING))
		{
			return Type.STRING;
		}
		else if (op == '=')
		{
			if (t1 == t2) { return Type.INT; }
			if ((t1 == null || t2 == null))
			{
				if (t1 instanceof TypeClass || t1 instanceof TypeArray) { return Type.INT; }
				if (t2 instanceof TypeClass || t2 instanceof TypeArray) { return Type.INT; }
				throw new SemanticException();
			}
			if (t1 instanceof TypeClass && t1 instanceof TypeClass)
			{
				if (((TypeClass)t1).isInheritingFrom(t2.name)) { return Type.INT; }
				if (((TypeClass)t2).isInheritingFrom(t1.name)) { return Type.INT; }
				throw new SemanticException(t1 + ", " + t2);
			}
		}
		throw new SemanticException(t1 + ", " + t2);
	}

}
