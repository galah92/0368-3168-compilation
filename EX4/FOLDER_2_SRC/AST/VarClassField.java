package AST;
import TYPES.*;
import pcomp.*;


public class VarClassField extends Var
{

	public Var className;
	public String fieldName;
	
	public VarClassField(Var className, String fieldName)
	{
		this.className = className;
		this.fieldName = fieldName;
	}

	public void logGraphviz()
	{
		className.logGraphviz();
		logNode(String.format("VarClassField\n%s", fieldName));
		logEdge(className);
	}

	public Type Semant() throws Exception
	{
		Type t = className.Semant();
		if (!(t instanceof TypeClass)) { throw new SemanticException(); }
		TypeClass typeClass = (TypeClass)t;
		return typeClass.getVarField(fieldName);
	}

	@Override
	public TempReg toIR()
	{
		// TODO: implement
		return null;
	}
    
}
