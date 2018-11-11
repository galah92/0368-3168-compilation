package AST;

public class AST_dec extends AST_Node
{
    public AST_varDec varDec;
    public AST_funcDec funcDec;
    public AST_classDec classDec;
    public AST_ARRAY_DEC arrayDec;
	
	public AST_dec(AST_varDec varDec)
	{
		this.varDec = varDec;
	}
	
	public AST_dec(AST_funcDec funcDec)
	{
		this.funcDec = funcDec;
	}
	
	public AST_dec(AST_classDec classDec)
	{
		this.classDec = classDec;
	}
	
	public AST_dec(AST_ARRAY_DEC arrayDec)
	{
		this.arrayDec = arrayDec;
	}
	
	public void PrintMe()
	{
		System.out.println("AST_dec");

		if (varDec != null) { varDec.PrintMe(); }
		if (funcDec != null) { funcDec.PrintMe(); }
		if (classDec != null) { classDec.PrintMe(); }
		if (arrayDec != null) { arrayDec.PrintMe(); }

		if (varDec != null) { AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, varDec.SerialNumber); }
		if (funcDec != null) { AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, funcDec.SerialNumber); }
		if (classDec != null) { AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, classDec.SerialNumber); }
		if (arrayDec != null) { AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, arrayDec.SerialNumber); }
	}
}
