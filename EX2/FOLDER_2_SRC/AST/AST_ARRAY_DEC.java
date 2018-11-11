package AST;

public class AST_ARRAY_DEC extends AST_Node
{
	public String id1;
	public String id2;
	
	public AST_ARRAY_DEC(String id1, String id2)
	{
		SerialNumber = AST_Node_Serial_Number.getFresh();

		System.out.format("arrayDec -> ARRAY ID(%s) EQ ID(%s) LBRACK RBRACK\n", id1, id2);

		this.id1 = id1;
		this.id2 = id2;
	}

	public void PrintMe()
	{
		System.out.println("AST ARRAY DEC");

		System.out.format("ID1(%s)\n", id1);
		System.out.format("ID2(%s)\n", id2);

		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("ID1\n...->%s", id1));
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("ID2\n...->%s", id2));
	}
}
