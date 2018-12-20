package IR;

import java.util.*;

public class IR
{
	private static final Deque<IRcommand> commands = new ArrayDeque<IRcommand>();

	public static void add(IRcommand cmd) { commands.add(cmd); }
	
	public static void MIPSme()
	{
		for (IRcommand comm : commands) { comm.MIPSme(); }
	}
}
