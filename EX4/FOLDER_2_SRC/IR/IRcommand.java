package IR;

public abstract class IRcommand
{
	private static int labelCounter = 0;
	
	public static String getLabel(String msg)
	{
		return String.format("Label_%d_%s", labelCounter++, msg);
	}

	public abstract void toMIPS();
}
