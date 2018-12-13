package AST;

import TYPES.*;

public class AST_EXP_CALL extends AST_EXP
{
	/****************/
	/* DATA MEMBERS */
	/****************/
	public String funcName;
	public AST_EXP_LIST params;

	/******************/
	/* CONSTRUCTOR(S) */
	/******************/
	public AST_EXP_CALL(String funcName,AST_EXP_LIST params)
	{
		/******************************/
		/* SET A UNIQUE SERIAL NUMBER */
		/******************************/
		SerialNumber = AST_Node_Serial_Number.getFresh();

		this.funcName = funcName;
		this.params = params;
	}

	/************************************************************/
	/* The printing message for a function declaration AST node */
	/************************************************************/
	public void PrintMe()
	{
		/*************************************************/
		/* AST NODE TYPE = AST NODE FUNCTION DECLARATION */
		/*************************************************/
		System.out.format("CALL(%s)\nWITH:\n",funcName);

		/***************************************/
		/* RECURSIVELY PRINT params + body ... */
		/***************************************/
		if (params != null) params.PrintMe();

		/***************************************/
		/* PRINT Node to AST GRAPHVIZ DOT file */
		/***************************************/
		AST_GRAPHVIZ.getInstance().logNode(
			SerialNumber,
			String.format("CALL(%s)\nWITH",funcName));

		/****************************************/
		/* PRINT Edges to AST GRAPHVIZ DOT file */
		/****************************************/
		AST_GRAPHVIZ.getInstance().logEdge(SerialNumber,params.SerialNumber);
	}

	public TYPE SemantMe(){
		TYPE t;
		TYPE type_func = SYMBOL_TABLE.getInstance().find(this.funcName);
		if (ret_type == null){
			System.out.format(">> ERROR [%d:%d] non existing function %s\n",99,99,this.funcName);
		}
		AST_EXP_LIST el;
		TYPE_LIST tl;
		for( el = paramas,  tl = type_func.params; (el != null && tl != null) ; el = el.tail, tl = tl.tail){
			t = SYMBOL_TABLE.getInstance().find(el.head.name);
			if(tl.head != t)
				System.out.format(">> ERROR [%d:%d] arguments types dont fit in func %s\n",99,99,this.funcName);
		}
		if (el !=null || tl != null) /* one of them is not null and therefore there is an error */
			System.out.format(">> ERROR [%d:%d] number of arguments doesnt fit function %s\n",99,99,this.funcName);


		return ret_type.returnType;
	}
}
