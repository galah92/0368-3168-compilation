package pcomp;
import java.io.*;


public class MIPSGen
{

    private static final StringWriter stringWriter = new StringWriter();
    private static final PrintWriter writer = new PrintWriter(stringWriter);

    public static void toFile(String outFile) throws Exception
    {
        writer.print("\tli $v0, 10\n");
        writer.print("\tsyscall\n");
        writer.close();

        PrintWriter fileWriter = new PrintWriter(outFile);
        fileWriter.println(".data");
        fileWriter.println("string_access_violation: .asciiz \"Access Violation\"");
        fileWriter.println("string_illegal_div_by_0: .asciiz \"Illegal Division By Zero\"");
        fileWriter.println("string_invalid_ptr_dref: .asciiz \"Invalid Pointer Dereference\"");
        fileWriter.append(stringWriter.toString());
        fileWriter.close();
    }

    public static final int WORD = 4;

    public static final String zero = "zero";  // the value 0, not changeable

    // value from expression evaluation or function return
    public static final String v0 = "$v0";
    public static final String v1 = "$v1";

    // first four arguments to a function/subroutine, if needed
    public static final String a0 = "$a0";
    public static final String a1 = "$a1";
    public static final String a2 = "$a2";
    public static final String a3 = "$a3";

    public static final String sp = "$sp";  // stack pointer (top of stack)
    public static final String fp = "$fp";  // frame pointer (bottom of current stack frame)
    public static final String ra = "$ra";  // return address of most recent caller

    public static void add(TempReg dst, TempReg reg1, TempReg reg2) { writer.printf("\tadd Temp_%d, Temp_%d, Temp_%d\n", dst.id, reg1.id, reg2.id); }

    public static void addi(TempReg rd, TempReg rs, int imm) { writer.printf("\taddi Temp_%d, Temp_%d, Temp_%d\n", rd.id, rs.id, imm); }

    public static void mul(TempReg dst, TempReg reg1, TempReg reg2) { writer.printf("\tmul Temp_%d, Temp_%d, Temp_%d\n", dst.id, reg1.id, reg2.id); }

    public static void move(TempReg dst, TempReg reg1) { writer.printf("\tmove Temp_%d, Temp_%d\n", dst.id, reg1.id); }
    public static void move(String dst, TempReg reg1) { writer.printf("\tmove %s, Temp_%d\n", dst, reg1.id); }

    public static void li(TempReg d, int imm) { writer.printf("\tli Temp_%d, %d\n", d.id, imm); }
    public static void li(String d, int imm) { writer.printf("\tli %s, %d\n", d, imm); }

    public static void lw(String rd, String addr) { writer.printf("\tlw %s, %s\n", rd, addr); }

    public static void sw(String rs, String addr) { writer.printf("\tsw %s, %s\n", rs, addr); }

    public static void jr(String rs) { writer.printf("\tjr %s\n", rs); }

    public static void syscall() { writer.printf("\tsyscall\n"); }
    
    public static void allocate(String var_name)
    {
        writer.printf(".data\n");
        writer.printf("\tglobal_%s: .word 721\n",var_name);
    }

    public static void load(TempReg dst, String var_name)
    {
        writer.printf("\tlw Temp_%d, global_%s\n", dst.id, var_name);
    }

    public static void store(String var_name, TempReg src)
    {
        writer.printf("\tsw Temp_%d, global_%s\n", src.id, var_name);
    }

    public static void label(String inlabel)
    {
        writer.println();
        if (inlabel.equals("main")) { writer.printf(".text\n"); }
        writer.printf("%s:\n", inlabel);
    }	

    public static void jump(String inlabel) { writer.printf("\tj %s\n", inlabel); }

    public static void blt(TempReg reg1, TempReg reg2, String label) { writer.printf("\tblt Temp_%d, Temp_%d, %s\n", reg1.id, reg2.id, label); }

    public static void bge(TempReg reg1, TempReg reg2, String label) { writer.printf("\tbge Temp_%d, Temp_%d, %s\n", reg1.id, reg2.id, label); }

    public static void bne(TempReg reg1,TempReg reg2,String label) { writer.printf("\tbne Temp_%d, Temp_%d, %s\n", reg1.id, reg2.id, label); }

    public static void beq(TempReg reg1,TempReg reg2,String label) { writer.printf("\tbeq Temp_%d, Temp_%d, %s\n", reg1.id, reg2.id, label); }

    public static void beqz(TempReg reg1, String label) { writer.printf("\tbeq Temp_%d, $zero, %s\n", reg1.id, label); }

    public static void la(TempReg reg, String label) { writer.printf("\tla Temp_%d, %s\n", reg.id, label); }

    public static void func_prologue(int numLocals)
    {
        // save fp & ra
        writer.printf("\taddi $sp, $sp, -8\n");
        writer.printf("\tsw	$fp, 0($sp)\n");
        writer.printf("\tsw	$ra, 4($sp)\n");
        // set fp = sp
        writer.printf("\taddi $fp, $sp, 0\n");
	    // allocate stack frame
        writer.printf("\taddi $sp, $sp, %d\n", -WORD * numLocals);
    }

    public static void func_epilogue(int numLocals)
    {
        // deallocate stack frame
        writer.printf("\taddi $sp, $sp, %d\n", WORD * numLocals);
        // restore fp & ra
        writer.printf("\tlw	$ra, 4($fp)\n");
        writer.printf("\tlw	$fp, ($fp)\n");
        // deallocate place for ra & fp
        writer.printf("\taddi $sp, $sp, 8\n");
        // jump back
        writer.printf("\tjr $ra\n");
    }

}
