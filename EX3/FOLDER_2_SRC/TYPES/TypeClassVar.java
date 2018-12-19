package TYPES;

public class TypeClassVar extends Type
{
	public Type varType;
	
	public TypeClassVar(Type varType, String name)
	{
		super(name);
		this.varType = varType;
	}
}
