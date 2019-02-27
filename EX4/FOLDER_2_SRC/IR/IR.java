package pcomp;

import java.util.*;

public class IR
{
	private static final Deque<IRComm> commands = new ArrayDeque<IRComm>();

	public static void add(IRComm cmd) { commands.add(cmd); }
	
	public static void toMIPS()
	{
		for (IRComm comm : commands) { comm.toMIPS(); }
	}
}
