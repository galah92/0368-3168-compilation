package AST;
import TYPES.*;
import SymbolTable.*;

public class VarSimple extends Var
{
	public String varName;
	
	public VarSimple(String varName)
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
