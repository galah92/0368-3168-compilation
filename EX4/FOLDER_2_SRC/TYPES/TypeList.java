package pcomp;


public class TypeList extends Type
{

	public Type head;
	public TypeList tail;

	public TypeList(Type head, TypeList tail)
	{
		super(null);
		this.head = head;
		this.tail = tail;
	}

}
