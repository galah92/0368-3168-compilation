package AST;
import TYPES.*;
import SymbolStack.*;

public class VarArrayElement extends Var
{
	public Var var;
	public Exp index;

	public VarArrayElement(Var var, Exp index)
	{
		this.var = var;
		this.index = index;
	}

	public void logGraphviz()
	{
		var.logGraphviz();
		index.logGraphviz();
		logNode(String.format("VarArrayElement\n%s", index));
		logEdge(var);
	}

	public Type Semant() throws Exception
	{
		if (index.Semant() != Type.INT) { throw new SemanticException(); }
		Type arrType = var.Semant();
		if (!(arrType instanceof TypeArray)) { throw new SemanticException(); }
		return ((TypeArray)arrType).elementType;
	}

}
