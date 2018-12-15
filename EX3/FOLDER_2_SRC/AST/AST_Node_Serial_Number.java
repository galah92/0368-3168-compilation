package AST;

public class AST_Node_Serial_Number
{
	public int SerialNumber;
	
	private static AST_Node_Serial_Number instance = null;

	protected AST_Node_Serial_Number() {}

	private static AST_Node_Serial_Number getInstance()
	{
		if (instance == null)
		{
			instance = new AST_Node_Serial_Number();
			instance.SerialNumber = 0;			
		}
		return instance;
	}

	public int get()
	{
		return SerialNumber++;
	}

	public static int getFresh()
	{
		return AST_Node_Serial_Number.getInstance().get();
	}
}
