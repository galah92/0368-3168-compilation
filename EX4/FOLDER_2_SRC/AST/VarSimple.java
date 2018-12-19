package AST;
import TYPES.*;
import SymbolStack.*;

public class VarSimple extends Var
{
	public String varName;
	
	public VarSimple(String varName)
	{
		this.varName = varName;
	}

	public void logGraphviz()
	{
		logNode(String.format("VarSimple\n(%s)", varName));
	}
	
	public Type Semant() throws Exception
	{
		return SymbolStack.find(varName);
	}
}
