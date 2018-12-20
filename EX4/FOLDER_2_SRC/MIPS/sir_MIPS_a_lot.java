package MIPS;
import java.io.PrintWriter;
import TEMP.*;

public class sir_MIPS_a_lot
{
	private int WORD_SIZE = 4;
	private PrintWriter fileWriter;

	public void finalizeFile()
	{
		fileWriter.print("\tli $v0,10\n");
		fileWriter.print("\tsyscall\n");
		fileWriter.close();
	}
	public void print_int(TEMP t)
	{
		int idx=t.getSerialNumber();
		// fileWriter.format("\taddi $a0,Temp_%d,0\n",idx);
		fileWriter.format("\tmove $a0,Temp_%d\n",idx);
		fileWriter.format("\tli $v0,1\n");
		fileWriter.format("\tsyscall\n");
		fileWriter.format("\tli $a0,32\n");
		fileWriter.format("\tli $v0,11\n");
		fileWriter.format("\tsyscall\n");
	}
	
	public void allocate(String var_name)
	{
		fileWriter.format(".data\n");
		fileWriter.format("\tglobal_%s: .word 721\n",var_name);
	}

	public void load(TEMP dst,String var_name)
	{
		int idxdst=dst.getSerialNumber();
		fileWriter.format("\tlw Temp_%d,global_%s\n",idxdst,var_name);
	}

	public void store(String var_name,TEMP src)
	{
		int idxsrc=src.getSerialNumber();
		fileWriter.format("\tsw Temp_%d,global_%s\n",idxsrc,var_name);		
	}

	public void li(TEMP t,int value)
	{
		int idx=t.getSerialNumber();
		fileWriter.format("\tli Temp_%d,%d\n",idx,value);
	}

	public void add(TEMP dst,TEMP oprnd1,TEMP oprnd2)
	{
		int i1 = oprnd1.getSerialNumber();
		int i2 = oprnd2.getSerialNumber();
		int dstidx=dst.getSerialNumber();

		fileWriter.format("\tadd Temp_%d,Temp_%d,Temp_%d\n",dstidx,i1,i2);
	}

	public void mul(TEMP dst,TEMP oprnd1,TEMP oprnd2)
	{
		int i1 = oprnd1.getSerialNumber();
		int i2 = oprnd2.getSerialNumber();
		int dstidx=dst.getSerialNumber();

		fileWriter.format("\tmul Temp_%d,Temp_%d,Temp_%d\n",dstidx,i1,i2);
	}

	public void label(String inlabel)
	{
		if (inlabel.equals("main"))
		{
			fileWriter.format(".text\n");
			fileWriter.format("%s:\n",inlabel);
		}
		else
		{
			fileWriter.format("%s:\n",inlabel);
		}
	}	

	public void jump(String inlabel)
	{
		fileWriter.format("\tj %s\n",inlabel);
	}	

	public void blt(TEMP oprnd1,TEMP oprnd2,String label)
	{
		int i1 = oprnd1.getSerialNumber();
		int i2 = oprnd2.getSerialNumber();
		
		fileWriter.format("\tblt Temp_%d,Temp_%d,%s\n",i1,i2,label);				
	}

	public void bge(TEMP oprnd1,TEMP oprnd2,String label)
	{
		int i1 = oprnd1.getSerialNumber();
		int i2 = oprnd2.getSerialNumber();
		
		fileWriter.format("\tbge Temp_%d,Temp_%d,%s\n",i1,i2,label);				
	}

	public void bne(TEMP oprnd1,TEMP oprnd2,String label)
	{
		int i1 = oprnd1.getSerialNumber();
		int i2 = oprnd2.getSerialNumber();
		
		fileWriter.format("\tbne Temp_%d,Temp_%d,%s\n",i1,i2,label);				
	}

	public void beq(TEMP oprnd1,TEMP oprnd2,String label)
	{
		int i1 = oprnd1.getSerialNumber();
		int i2 = oprnd2.getSerialNumber();
		
		fileWriter.format("\tbeq Temp_%d,Temp_%d,%s\n",i1,i2,label);				
	}

	public void beqz(TEMP oprnd1,String label)
	{
		int i1 = oprnd1.getSerialNumber();
				
		fileWriter.format("\tbeq Temp_%d,$zero,%s\n",i1,label);				
	}
	
	private static sir_MIPS_a_lot instance = null;

	protected sir_MIPS_a_lot() {}

	public static sir_MIPS_a_lot getInstance()
	{
		if (instance == null)
		{
			instance = new sir_MIPS_a_lot();

			try
			{
				String dirname="./FOLDER_5_OUTPUT/";
				String filename=String.format("MIPS.txt");
				instance.fileWriter = new PrintWriter(dirname+filename);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			instance.fileWriter.println(".data");
			instance.fileWriter.println("string_access_violation: .asciiz \"Access Violation\"");
			instance.fileWriter.println("string_illegal_div_by_0: .asciiz \"Illegal Division By Zero\"");
			instance.fileWriter.println("string_invalid_ptr_dref: .asciiz \"Invalid Pointer Dereference\"");
		}
		return instance;
	}
}
