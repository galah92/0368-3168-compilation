package AST;

public class AST_EXP_ID extends AST_EXP
{
	public String id;
	public AST_expList expList;
	public AST_VAR var;

	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_EXP_ID(String id, AST_expList expList)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		if (expList != null) System.out.print("====================== exp -> ID LPAREN expList RPAREN\n");
		else System.out.print("====================== exp -> ID LPAREN RPAREN\n");			

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.id = id;
		if (expList != null) this.expList = expList;
	}

public AST_EXP_ID(AST_VAR var, String id, AST_expList expList)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		/***************************************/
		/* PRINT CORRESPONDING DERIVATION RULE */
		/***************************************/
		if (expList != null) System.out.print("====================== exp -> var DOT ID LPAREN expList RPAREN\n");			
		else System.out.print("====================== exp -> var DOT ID LPAREN RPAREN\n");

		/*******************************/
		/* COPY INPUT DATA NENBERS ... */
		/*******************************/
		this.var = var;
		this.id = id;
		if (expList != null) this.expList = expList;
	}
	
	
	public void PrintMe()
	{
		/************************************/
		/* AST NODE TYPE = EXP ID LPAREN expList RPAREN AST NODE */
		/************************************/
		if(var == null){
			if (expList == null) System.out.print(" ID LPAREN RPAREN\n");
			else System.out.print(" ID LPAREN expList RPAREN\n");
		} else {
			if (expList == null) System.out.print(" ID VAR DOT LPAREN RPAREN\n");
			else System.out.print(" ID VAR DOT LPAREN expList RPAREN\n");
		}
		/*****************************/
		/* RECURSIVELY PRINT exp ... */
		/*****************************/
		if (var != null) var.PrintMe();
		if (expList != null) expList.PrintMe();
		
		/*********************************/
		/* Print to AST GRAPHIZ DOT file */
		/*********************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			 String.format("ID1->%s", id));

		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		//AST_GRAPHVIZ.getInstance().logNode(SerialNumber, "Lparen exp Rparen");
		if (var != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,var.SerialNumber);
		if (expList != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,expList.SerialNumber);
			
	}
}
