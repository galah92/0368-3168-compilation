package TYPES;

public class TYPE_CLASS extends TYPE
{
	public TYPE_CLASS base;
	public TYPE_LIST fields;
	
	public TYPE_CLASS(TYPE_CLASS base, String name, TYPE_LIST fields)
	{
		this.name = name;
		this.base = base;
		this.fields = fields;
	}
}
