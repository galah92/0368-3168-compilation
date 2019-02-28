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



	public static class Stack
	{

		public static class claim extends IRComm
		{
			int offset;
			public claim(int offset) { this.offset = offset; }
			public void toMIPS()
			{
				MIPSGen.writer.printf("\taddi $sp, $sp, -%d\n", offset * MIPSGen.WORD);
			}
		}

		public static class release extends IRComm
		{
			int offset;
			public release(int offset) { this.offset = offset; }
			public void toMIPS()
			{
				MIPSGen.writer.printf("\taddi $sp, $sp, %d\n", offset * MIPSGen.WORD);
			}
		}

		public static class set extends IRComm
		{
			TempReg src;
			int offset;
			public set(TempReg src, int offset) { this.src = src; this.offset = offset; }
			public void toMIPS()
			{
				MIPSGen.writer.printf("\tsw Temp_%d, %d($sp)\n", src.id, offset * MIPSGen.WORD);
			}
		}

		public static class get extends IRComm
		{
			TempReg dst;
			int offset;
			public get(TempReg dst, int offset) { this.dst = dst; this.offset = offset; }
			public void toMIPS()
			{
				MIPSGen.writer.printf("\tlw Temp_%d, %d($sp)\n", dst.id, offset * MIPSGen.WORD);
			}
		}

	}

	public static class push extends IRComm
	{
		TempReg src;
		public push(TempReg src) { this.src = src; }
		public void toMIPS()
		{
			MIPSGen.writer.printf("\taddi $sp, $sp, -4\n");
			MIPSGen.writer.printf("\tsw Temp_%d, ($sp)\n", src.id);
		}
	}

	public static class pop extends IRComm
	{
		TempReg dst;
		public pop(TempReg dst) { this.dst = dst; }
		public void toMIPS()
		{
			MIPSGen.writer.printf("\tlw Temp_%d, ($sp)\n", dst.id);
			MIPSGen.writer.printf("\taddi $sp, $sp, 4\n");
		}
	}

	public static class jal extends IRComm
	{
		String label;
		public jal(String label) { this.label = label; }
		public void toMIPS()
		{
			MIPSGen.writer.printf("\tjal %s\n", label);
		}
	}

}
