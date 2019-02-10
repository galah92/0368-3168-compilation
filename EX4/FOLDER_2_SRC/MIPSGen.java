package pcomp;
import java.io.*;

public class MIPSGen
{

	private static final StringWriter stringWriter = new StringWriter();
	private static final PrintWriter writer = new PrintWriter(stringWriter);

	public static void toFile(String outFile) throws Exception
	{
		writer.println(".data");
		writer.println("string_access_violation: .asciiz \"Access Violation\"");
		writer.println("string_illegal_div_by_0: .asciiz \"Illegal Division By Zero\"");
		writer.println("string_invalid_ptr_dref: .asciiz \"Invalid Pointer Dereference\"");
		writer.print("\tli $v0,10\n");
		writer.print("\tsyscall\n");
		writer.close();

		PrintWriter fileWriter = new PrintWriter(outFile);
		fileWriter.append(stringWriter.toString());
		fileWriter.close();
	}

	public static void print_int(TempReg t)
	{
		writer.printf("\tmove $a0,Temp_%d\n", t.serialNum);
		writer.printf("\tli $v0,1\n");
		writer.printf("\tsyscall\n");
		// writer.printf("\tli $a0,32\n");
		// writer.printf("\tli $v0,11\n");
		// writer.printf("\tsyscall\n");
	}

	public static void print_string(TempReg t)
	{
		writer.printf("\tmove $a0,Temp_%d\n", t.serialNum);
		writer.printf("\tli $v0,4\n");
		writer.printf("\tsyscall\n");
	}
	
	public static void allocate(String var_name)
	{
		writer.printf(".data\n");
		writer.printf("\tglobal_%s: .word 721\n",var_name);
	}

	public static void load(TempReg dst, String var_name)
	{
		writer.printf("\tlw Temp_%d,global_%s\n", dst.serialNum, var_name);
	}

	public static void store(String var_name, TempReg src)
	{
		writer.printf("\tsw Temp_%d,global_%s\n", src.serialNum, var_name);
	}

	public static void li(TempReg t, int value)
	{
		writer.printf("\tli Temp_%d,%d\n", t.serialNum, value);
	}

	public static void add(TempReg dst, TempReg oprnd1, TempReg oprnd2)
	{
		writer.printf("\tadd Temp_%d,Temp_%d,Temp_%d\n",
					  dst.serialNum,
					  oprnd1.serialNum,
					  oprnd2.serialNum);
	}

	public static void mul(TempReg dst, TempReg oprnd1, TempReg oprnd2)
	{
		writer.printf("\tmul Temp_%d,Temp_%d,Temp_%d\n",
					  dst.serialNum,
					  oprnd1.serialNum,
					  oprnd2.serialNum);
	}

	public static void label(String inlabel)
	{
		if (inlabel.equals("main")) { writer.printf(".text\n"); }
		writer.printf("%s:\n", inlabel);
	}	

	public static void jump(String inlabel)
	{
		writer.printf("\tj %s\n", inlabel);
	}	

	public static void blt(TempReg oprnd1, TempReg oprnd2, String label)
	{
		writer.printf("\tblt Temp_%d,Temp_%d,%s\n",
					  oprnd1.serialNum,
					  oprnd2.serialNum,
					  label);
	}

	public static void bge(TempReg oprnd1, TempReg oprnd2, String label)
	{
		writer.printf("\tbge Temp_%d,Temp_%d,%s\n",
					  oprnd1.serialNum,
					  oprnd2.serialNum,
					  label);
	}

	public static void bne(TempReg oprnd1,TempReg oprnd2,String label)
	{
		writer.printf("\tbne Temp_%d,Temp_%d,%s\n",
					  oprnd1.serialNum,
					  oprnd2.serialNum,
					  label);
	}

	public static void beq(TempReg oprnd1,TempReg oprnd2,String label)
	{
		writer.printf("\tbeq Temp_%d,Temp_%d,%s\n",
					  oprnd1.serialNum,
					  oprnd2.serialNum,
					  label);
	}

	public static void beqz(TempReg oprnd1, String label)
	{
		writer.printf("\tbeq Temp_%d,$zero,%s\n",
					  oprnd1.serialNum,
					  label);
	}

}
