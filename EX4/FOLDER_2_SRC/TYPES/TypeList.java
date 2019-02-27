package pcomp;

public class TypeList extends Type
{
	public Type value;
	public TypeList next;

	public TypeList(Type value, TypeList next)
	{
		super(null);
		this.value = value;
		this.next = next;
	}
}
