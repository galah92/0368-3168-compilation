package TYPES;

public class TYPE_CLASS extends TYPE
{
	public TYPE_CLASS base;
	public TYPE_LIST data_members;
	
	public TYPE_CLASS(TYPE_CLASS base, String name, TYPE_LIST data_members)
	{
		this.name = name;
		this.base = base;
		this.data_members = data_members;
	}

    public boolean isClass() { return true; }
}
