package TYPES;

public class TYPE_FUNCTION extends TYPE
{
	public TYPE retType;
	public TYPE_LIST params;
	
	public TYPE_FUNCTION(TYPE retType, String name, TYPE_LIST params)
	{
		this.name = name;
		this.retType = retType;
		this.params = params;
	}
}
