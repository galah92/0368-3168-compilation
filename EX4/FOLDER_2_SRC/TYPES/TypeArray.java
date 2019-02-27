package pcomp;

public class TypeArray extends Type
{
	public Type elementType;
	
	public TypeArray(Type elementType)
	{
		super("Array");
		this.elementType = elementType;
	}
}
