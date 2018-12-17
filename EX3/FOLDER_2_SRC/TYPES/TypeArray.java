package TYPES;

public class TypeArray extends Type
{
	public Type elementType;
	
	public TypeArray(Type elementType, String name)
	{
		this.elementType = elementType;
		this.name = name;
	}
}
