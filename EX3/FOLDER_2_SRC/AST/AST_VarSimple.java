package AST;
import TYPES.*;
import SymbolTable.*;

public class AST_VarSimple extends AST_Var
{
	public String varName;
	
	public AST_VarSimple(String varName)
	{
		this.varName = varName;
	}

	public void PrintMe()
	{
		AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("VarSimple\n(%s)", varName));
	}
	
	public TYPE SemantMe() throws Exception
	{
		return SymbolTable.getInstance().find(varName);
	}
}
