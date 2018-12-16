package TYPES;

public class TypeFunc extends Type
{
	public Type retType;
	public TypeList params;
	
	public TypeFunc(Type retType, String name, TypeList params)
	{
		this.name = name;
		this.retType = retType;
		this.params = params;
	}
}
