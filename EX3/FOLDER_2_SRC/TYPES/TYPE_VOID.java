package TYPES;

public class TYPE_VOID extends TYPE
{
	private static TYPE_VOID instance = null;

	protected TYPE_VOID() {}

	public static TYPE_VOID getInstance()
	{
		if (instance == null)
		{
			instance = new TYPE_VOID();
            // TODO: should we assign name here?
		}
		return instance;
	}
}
