package AST;

import pcomp.*;


public class ExpBinOp extends Exp
{
	public char op;
	public Exp left;
	public Exp right;
	public boolean isStringsExpessions;

	public ExpBinOp(Exp left, Exp right, char op)
	{
		this.left = left;
		this.right = right;
		this.op = op;
	}

	@Override
	public void logGraphviz()
	{
		if (left != null) left.logGraphviz();
		if (right != null) right.logGraphviz();
		logNode(String.format("ExpBinOp\n%s", op));
		if (left  != null) logEdge(left);
		if (right != null) logEdge(right);
	}

	@Override
	public Type Semant() throws Exception
	{
		Type t1 = left.Semant();
		Type t2 = right.Semant();
		isStringsExpessions = t1 == Type.STRING && t2 == Type.STRING;

		if (t1 == Type.INT && t2 == Type.INT)
		{
			return Type.INT;
		}
		else if (op == '+' && isStringsExpessions)
		{
			return Type.STRING;
		}
		else if (op == '=')
		{
			if (t1 == t2) { return Type.INT; }
			if ((t1 == Type.NIL || t2 == Type.NIL))
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

	@Override
	public TempReg toIR()
	{
		TempReg t1 = left.toIR();
		TempReg t2 = right.toIR();
		TempReg dst = new TempReg();

		if (isStringsExpessions)
		{
			if (op == '=') { System.out.println("Strings comparison not supported yet"); }
			if (op == '+') { System.out.println("Strings concatination not supported yet"); }
		}
		
		if (op == '+')
		{
			IR.add(new IRcommand_Binop_Add_Integers(dst, t1, t2));
		}
		if (op == '*')
		{
			IR.add(new IRcommand_Binop_Mul_Integers(dst, t1, t2));
		}
		if (op == '=')
		{
			IR.add(new IRcommand_Binop_EQ_Integers(dst, t1, t2));
		}
		if (op == '<')
		{
			IR.add(new IRcommand_Binop_LT_Integers(dst, t1, t2));
		}
		return dst;
	}

}
