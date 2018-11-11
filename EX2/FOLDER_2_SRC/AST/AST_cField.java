package AST;

public class AST_cField extends AST_Node
{
    public AST_varDec varDec;
    public AST_funcDec funcDec;
	
	public AST_cField(AST_varDec varDec)
	{
		this.varDec = varDec;
	}
	
	public AST_cField(AST_funcDec funcDec)
	{
		this.funcDec = funcDec;
	}
	
	public void PrintMe()
	{
		System.out.println("AST_cField");

		if (varDec != null) { varDec.PrintMe(); }
		if (funcDec != null) { funcDec.PrintMe(); }

		if (varDec != null) { AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, varDec.SerialNumber); }
		if (funcDec != null) { AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, funcDec.SerialNumber); }
	}
}
