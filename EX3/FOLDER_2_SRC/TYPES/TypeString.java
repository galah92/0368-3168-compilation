package TYPES;

public class TypeString extends Type
{
	private static TypeString instance = null;

	protected TypeString() {}

	public static TypeString getInstance()
	{
		if (instance == null)
		{
			instance = new TypeString();
			instance.name = "string";
		}
		return instance;
	}
}
