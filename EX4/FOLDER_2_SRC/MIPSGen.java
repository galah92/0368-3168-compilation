package pcomp;
import java.io.*;

public class MIPSGen
{

	private static final StringBuilder sb = new StringBuilder();

	public static void toFile(String outFile) throws Exception
	{
		PrintWriter writer = new PrintWriter(outFile);
		writer.println(".data");
		writer.println("string_access_violation: .asciiz \"Access Violation\"");
		writer.println("string_illegal_div_by_0: .asciiz \"Illegal Division By Zero\"");
		writer.println("string_invalid_ptr_dref: .asciiz \"Invalid Pointer Dereference\"");
		writer.println(sb.toString());
		writer.print("\tli $v0,10\n");
		writer.print("\tsyscall\n");
		writer.close();
	}

	public static void print_int(TempReg t)
	{
		sb.append(String.format("\tmove $a0,Temp_%d\n", t.serialNum));
		sb.append(String.format("\tli $v0,1\n"));
		sb.append(String.format("\tsyscall\n"));
		// sb.append(String.format("\tli $a0,32\n"));
		// sb.append(String.format("\tli $v0,11\n"));
		// sb.append(String.format("\tsyscall\n"));
	}

	public static void print_string(TempReg t)
	{
		sb.append(String.format("\tmove $a0,Temp_%d\n", t.serialNum));
		sb.append(String.format("\tli $v0,4\n"));
		sb.append(String.format("\tsyscall\n"));
	}
	
	public static void allocate(String var_name)
	{
		sb.append(String.format(".data\n"));
		sb.append(String.format("\tglobal_%s: .word 721\n",var_name));
	}

	public static void load(TempReg dst, String var_name)
	{
		sb.append(String.format("\tlw Temp_%d,global_%s\n", dst.serialNum, var_name));
	}

	public static void store(String var_name, TempReg src)
	{
		sb.append(String.format("\tsw Temp_%d,global_%s\n", src.serialNum, var_name));
	}

	public static void li(TempReg t, int value)
	{
		sb.append(String.format("\tli Temp_%d,%d\n", t.serialNum, value));
	}

	public static void add(TempReg dst, TempReg oprnd1, TempReg oprnd2)
	{
		sb.append(String.format("\tadd Temp_%d,Temp_%d,Temp_%d\n",
								dst.serialNum,
								oprnd1.serialNum,
								oprnd2.serialNum));
	}

	public static void mul(TempReg dst, TempReg oprnd1, TempReg oprnd2)
	{
		sb.append(String.format("\tmul Temp_%d,Temp_%d,Temp_%d\n",
								dst.serialNum,
								oprnd1.serialNum,
								oprnd2.serialNum));
	}

	public static void label(String inlabel)
	{
		if (inlabel.equals("main")) { sb.append(String.format(".text\n")); }
		sb.append(String.format("%s:\n", inlabel));
	}	

	public static void jump(String inlabel)
	{
		sb.append(String.format("\tj %s\n", inlabel));
	}	

	public static void blt(TempReg oprnd1, TempReg oprnd2, String label)
	{
		sb.append(String.format("\tblt Temp_%d,Temp_%d,%s\n",
								oprnd1.serialNum,
								oprnd2.serialNum,
								label));
	}

	public static void bge(TempReg oprnd1, TempReg oprnd2, String label)
	{
		sb.append(String.format("\tbge Temp_%d,Temp_%d,%s\n",
								oprnd1.serialNum,
								oprnd2.serialNum,
								label));
	}

	public static void bne(TempReg oprnd1,TempReg oprnd2,String label)
	{
		sb.append(String.format("\tbne Temp_%d,Temp_%d,%s\n",
								oprnd1.serialNum,
								oprnd2.serialNum,
								label));
	}

	public static void beq(TempReg oprnd1,TempReg oprnd2,String label)
	{
		sb.append(String.format("\tbeq Temp_%d,Temp_%d,%s\n",
								oprnd1.serialNum,
								oprnd2.serialNum,
								label));
	}

	public static void beqz(TempReg oprnd1, String label)
	{
		sb.append(String.format("\tbeq Temp_%d,$zero,%s\n",
								oprnd1.serialNum,
								label));
	}
}
