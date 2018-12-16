package TYPES;

public class TypeClassVar extends Type
{
	public Type varType;
	
	public TypeClassVar(Type varType, String name)
	{
		this.varType = varType;
		this.name = name;
	}
}
