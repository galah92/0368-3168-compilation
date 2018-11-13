package AST;

public class AST_EXP_BINOP extends AST_EXP
{
	int OP;
	public AST_EXP left;
	public AST_EXP right;

	public AST_EXP_BINOP(AST_EXP left,AST_EXP right,int OP)
	{
		/* SET A UNIQUE SERIAL NUMBER */
		SerialNumber = AST_Node_Serial_Number.getFresh();

		System.out.println("==== exp -> exp BINOP exp");

		this.left = left;
		this.right = right;
		this.OP = OP;
	}

	public void PrintMe()
	{
		String sOP="";
		switch(OP){
			case 0:
				sOP = "+";
				break;
			case 1 :
				sOP = "-";
				break;
			case 2:
				sOP = "*";
				break;
			case 3:
				sOP = "/";
				break;
			case 4:
				sOP = "<";
				break;
			case 5:
				sOP = ">";
				break;
			case 6:
				sOP = "=";
				break;
			default:
				sOP = "";
		}
	
		System.out.println("AST NODE BINOP EXP");
		if (left != null) left.PrintMe();
		if (right != null) right.PrintMe();

		AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("BINOP(%s)", sOP));

		if (left  != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, left.SerialNumber);
		if (right != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, right.SerialNumber);
	}
}
