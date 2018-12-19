package TYPES;

public class TypeArray extends Type
{
	public Type elementType;
	
	public TypeArray(Type elementType, String name)
	{
		super(name);
		this.elementType = elementType;
	}
}
