package MIPS;
import java.io.*;
import pcomp.*;

public class MIPSGen
{

	private static PrintWriter writer = null;

	public static void init(String outFile)
	{
		try
		{
			writer = new PrintWriter(outFile);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		writer.println(".data");
		writer.println("string_access_violation: .asciiz \"Access Violation\"");
		writer.println("string_illegal_div_by_0: .asciiz \"Illegal Division By Zero\"");
		writer.println("string_invalid_ptr_dref: .asciiz \"Invalid Pointer Dereference\"");
	}

	public static void toFile()
	{
		writer.print("\tli $v0,10\n");
		writer.print("\tsyscall\n");
		writer.close();
	}
	public static void print_int(TempReg t)
	{
		int idx=t.serialNum;
		// writer.format("\taddi $a0,Temp_%d,0\n",idx);
		writer.format("\tmove $a0,Temp_%d\n",idx);
		writer.format("\tli $v0,1\n");
		writer.format("\tsyscall\n");
		writer.format("\tli $a0,32\n");
		writer.format("\tli $v0,11\n");
		writer.format("\tsyscall\n");
	}
	
	public static void allocate(String var_name)
	{
		writer.format(".data\n");
		writer.format("\tglobal_%s: .word 721\n",var_name);
	}

	public static void load(TempReg dst,String var_name)
	{
		int idxdst=dst.serialNum;
		writer.format("\tlw Temp_%d,global_%s\n",idxdst,var_name);
	}

	public static void store(String var_name,TempReg src)
	{
		int idxsrc=src.serialNum;
		writer.format("\tsw Temp_%d,global_%s\n",idxsrc,var_name);		
	}

	public static void li(TempReg t,int value)
	{
		int idx=t.serialNum;
		writer.format("\tli Temp_%d,%d\n",idx,value);
	}

	public static void add(TempReg dst,TempReg oprnd1,TempReg oprnd2)
	{
		int i1 = oprnd1.serialNum;
		int i2 = oprnd2.serialNum;
		int dstidx=dst.serialNum;

		writer.format("\tadd Temp_%d,Temp_%d,Temp_%d\n",dstidx,i1,i2);
	}

	public static void mul(TempReg dst,TempReg oprnd1,TempReg oprnd2)
	{
		int i1 = oprnd1.serialNum;
		int i2 = oprnd2.serialNum;
		int dstidx=dst.serialNum;

		writer.format("\tmul Temp_%d,Temp_%d,Temp_%d\n",dstidx,i1,i2);
	}

	public static void label(String inlabel)
	{
		if (inlabel.equals("main"))
		{
			writer.format(".text\n");
			writer.format("%s:\n",inlabel);
		}
		else
		{
			writer.format("%s:\n",inlabel);
		}
	}	

	public static void jump(String inlabel)
	{
		writer.format("\tj %s\n",inlabel);
	}	

	public static void blt(TempReg oprnd1,TempReg oprnd2,String label)
	{
		int i1 = oprnd1.serialNum;
		int i2 = oprnd2.serialNum;
		
		writer.format("\tblt Temp_%d,Temp_%d,%s\n",i1,i2,label);				
	}

	public static void bge(TempReg oprnd1,TempReg oprnd2,String label)
	{
		int i1 = oprnd1.serialNum;
		int i2 = oprnd2.serialNum;
		
		writer.format("\tbge Temp_%d,Temp_%d,%s\n",i1,i2,label);				
	}

	public static void bne(TempReg oprnd1,TempReg oprnd2,String label)
	{
		int i1 = oprnd1.serialNum;
		int i2 = oprnd2.serialNum;
		
		writer.format("\tbne Temp_%d,Temp_%d,%s\n",i1,i2,label);				
	}

	public static void beq(TempReg oprnd1,TempReg oprnd2,String label)
	{
		int i1 = oprnd1.serialNum;
		int i2 = oprnd2.serialNum;
		
		writer.format("\tbeq Temp_%d,Temp_%d,%s\n",i1,i2,label);				
	}

	public static void beqz(TempReg oprnd1, String label)
	{
		int i1 = oprnd1.serialNum;
				
		writer.format("\tbeq Temp_%d,$zero,%s\n",i1,label);				
	}
}
