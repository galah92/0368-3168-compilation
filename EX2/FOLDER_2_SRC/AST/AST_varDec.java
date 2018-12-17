package AST;

public class AST_varDec extends AST_Node
{
	public String id1;
	public String id2;
	public AST_EXP exp;
	public AST_newExp newExp;
	
	public AST_varDec(String id1, String id2)
	{
		this.id1 = id1;
		this.id2 = id2;
	}
	
	public AST_varDec(String id1, String id2, AST_EXP exp)
	{
		this.id1 = id1;
		this.id2 = id2;
		this.exp = exp;
	}
	
	public AST_varDec(String id1, String id2, AST_newExp newExp)
	{
		this.id1 = id1;
		this.id2 = id2;
		this.newExp = newExp;
	}

	public void PrintMe()
	{
		System.out.println("AST_varDec");

		System.out.format("ID1(%s)\n", id1);
		System.out.format("ID2(%s)\n", id2);
		if (exp != null) { exp.PrintMe(); }
		if (newExp != null) { newExp.PrintMe(); }

		AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("ID1...->%s\nID2...->%s", id1, id2));
		if (exp != null) { AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, exp.SerialNumber); }
		if (newExp != null) { AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, newExp.SerialNumber); }
	}
}
