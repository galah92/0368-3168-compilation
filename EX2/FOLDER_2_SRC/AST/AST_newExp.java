package AST;

public class AST_newExp extends AST_Node
{
	public String id;
	public AST_EXP exp;
	
	public AST_newExp(String id)
	{
		SerialNumber = AST_Node_Serial_Number.getFresh();

//		System.out.format("exp -> INT( %d )\n", value);

		this.id = id;
		this.exp = null;
	}
	
	public AST_newExp(String id, AST_EXP exp)
	{
		SerialNumber = AST_Node_Serial_Number.getFresh();

//		System.out.format("exp -> INT( %d )\n", value);

		this.id = id;
		this.exp = exp;
	}

	public void PrintMe()
	{
//		System.out.format("AST NODE INT( %d )\n",value);

		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			id);
		if (exp != null) {
			AST_GRAPHVIZ.getInstance().logEdge(
				SerialNumber,
				exp.SerialNumber);
		}
	}
}
