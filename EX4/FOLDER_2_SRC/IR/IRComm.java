package pcomp;


public abstract class IRComm
{

	private static int labelCounter = 0;
	
	public static String getLabel(String name)
	{
		return String.format("Label_%d_%s", labelCounter++, name);
	}

	public abstract void toMIPS();
	
}
