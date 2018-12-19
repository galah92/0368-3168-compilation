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

	public void toGraphviz()
	{
		logNode(String.format("VarSimple\n(%s)", varName));
	}
	
	public Type Semant() throws Exception
	{
		return SymbolTable.find(varName);
	}
}
