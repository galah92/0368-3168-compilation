package TYPES;

public class TYPE_LIST extends TYPE
{
	public TYPE head;
	public TYPE_LIST tail;

	public TYPE_LIST(TYPE head, TYPE_LIST tail)
	{
		this.head = head;
		this.tail = tail;
	}
}
