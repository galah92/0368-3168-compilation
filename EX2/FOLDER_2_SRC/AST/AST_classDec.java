package AST;

public class AST_classDec extends AST_EXP
{
	public String className;
	public String extName;
	public AST_cFieldList cFields;
	
	public AST_classDec(String className, AST_cFieldList cFields)
	{
		this.className = className;
		this.cFields = cFields;
	}

	public AST_classDec(String className, String extName, AST_cFieldList cFields)
	{
		this.className = className;
		this.extName = extName;
		this.cFields = cFields;
	}

	public void PrintMe()
	{
		System.out.format("AST_classDec\n");

		AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("ID(%s)" ,className));
		if (extName != null) { AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("ID(%s)" ,extName)); }
		AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, cFields.SerialNumber);
	}
}
