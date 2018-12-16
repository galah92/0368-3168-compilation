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

		AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("ExpBinOp\n%s", op));
		if (left  != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, left.SerialNumber);
		if (right != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, right.SerialNumber);
	}

	public TYPE SemantMe() throws Exception
	{
		TYPE t1 = left.SemantMe();
		TYPE t2 = right.SemantMe();

		if (t1 == TYPE_INT.getInstance() && t2 == TYPE_INT.getInstance())
		{
			return TYPE_INT.getInstance();
		}
		else if ((op == '+') && (t1 == TYPE_STRING.getInstance()) && (t2 == TYPE_STRING.getInstance()))
		{
			return TYPE_STRING.getInstance();
		}
		else if (op == '=')
		{
			if (t1 == t2) { return t1; }
			if ((t1 == null || t2 == null))
			{
				if (t1 instanceof TYPE_CLASS || t1 instanceof TYPE_ARRAY) { return TYPE_INT.getInstance(); }
				if (t2 instanceof TYPE_CLASS || t2 instanceof TYPE_ARRAY) { return TYPE_INT.getInstance(); }
				throw new Exception();
			}
		}
		throw new Exception(t1.name + ", " + t2.name);
	}

}
