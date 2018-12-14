package TYPES;

public class TYPE_ARRAY extends TYPE
{
	public TYPE elementType;
	
	public TYPE_ARRAY(TYPE elementType, String name)
	{
		this.elementType = elementType;
		this.name = name;
	}
}
