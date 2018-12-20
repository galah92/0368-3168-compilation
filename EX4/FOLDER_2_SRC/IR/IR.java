package IR;

public class IR
{
	private IRcommand head = null;
	private IRcommandList tail = null;

	public void Add_IRcommand(IRcommand cmd)
	{
		if (head == null && tail == null)
		{
			this.head = cmd;
		}
		else if (head != null && tail == null)
		{
			this.tail = new IRcommandList(cmd, null);
		}
		else
		{
			IRcommandList it = tail;
			while (it != null && it.tail != null) { it = it.tail; }
			it.tail = new IRcommandList(cmd, null);
		}
	}
	
	public void MIPSme()
	{
		if (head != null) head.MIPSme();
		if (tail != null) tail.MIPSme();
	}

	private static IR instance = null;

	protected IR() {}

	public static IR getInstance()
	{
		if (instance == null)
		{
			instance = new IR();
		}
		return instance;
	}
}
