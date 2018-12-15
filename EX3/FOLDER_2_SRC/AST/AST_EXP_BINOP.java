package AST;
import TYPES.*;

public class AST_EXP_BINOP extends AST_EXP
{
	int OP;
	public AST_EXP left;
	public AST_EXP right;

	public AST_EXP_BINOP(AST_EXP left, AST_EXP right, int OP)
	{
		this.left = left;
		this.right = right;
		this.OP = OP;
	}

	public void PrintMe()
	{
		String sOP="";
		if (OP == 0) { sOP = "+"; }
		if (OP == 1) { sOP = "-"; }
		if (OP == 2) { sOP = "*"; }
		if (OP == 3) { sOP = "/"; }
		if (OP == 4) { sOP = "<"; }
		if (OP == 5) { sOP = ">"; }
		if (OP == 6) { sOP = "="; }

		if (left != null) left.PrintMe();
		if (right != null) right.PrintMe();

		AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("BINOP(%s)", sOP));
		if (left  != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, left.SerialNumber);
		if (right != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, right.SerialNumber);
	}

	public TYPE SemantMe() throws Exception
	{
		TYPE t1 = null;
		TYPE t2 = null;

		if (left  != null) t1 = left.SemantMe();
		if (right != null) t2 = right.SemantMe();

		if (t1 == TYPE_INT.getInstance() && t2 == TYPE_INT.getInstance())
		{
			return TYPE_INT.getInstance();
		}
		if ((this.OP == 0) && (t1 == TYPE_STRING.getInstance()) && (t2 == TYPE_STRING.getInstance()))
		{
			return TYPE_STRING.getInstance();
		}
		throw new Exception();
	}

}
