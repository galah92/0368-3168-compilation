package pcomp;

public class TypeClassMember extends Type
{
	public Type varType;
	
	public TypeClassMember(Type varType, String name)
	{
		super(name);
		this.varType = varType;
	}
}
