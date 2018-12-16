package TYPES;

public class TypeVoid extends Type
{
	private static TypeVoid instance = null;

	protected TypeVoid() {}

	public static TypeVoid getInstance()
	{
		if (instance == null)
		{
			instance = new TypeVoid();
		}
		return instance;
	}
}
