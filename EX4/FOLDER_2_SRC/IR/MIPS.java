package pcomp;
import java.io.*;


public class MIPS
{

    private static final StringWriter stringWriter = new StringWriter();
    public static final PrintWriter writer = new PrintWriter(stringWriter);

    private static final StringWriter dataStringWriter = new StringWriter();
    public static final PrintWriter dataWriter = new PrintWriter(dataStringWriter);

    public static void toFile(String outFile) throws Exception
    {
        writer.print("\tli $v0, 10\n");
        writer.print("\tsyscall\n");
        writer.close();

        dataWriter.close();

        PrintWriter fileWriter = new PrintWriter(outFile);
        fileWriter.append(stringWriter.toString());
        fileWriter.println();
        fileWriter.println(".data");
        fileWriter.println("string_access_violation: .asciiz \"Access Violation\"");
        fileWriter.println("string_illegal_div_by_0: .asciiz \"Illegal Division By Zero\"");
        fileWriter.println("string_invalid_ptr_dref: .asciiz \"Invalid Pointer Dereference\"");
        fileWriter.append(dataStringWriter.toString());
        fileWriter.close();
    }

    public static final int WORD = 4;

    public static final String zero = "$0";  // the value 0, not changeable

    // value from expression evaluation or function return
    public static final String v0 = "$v0";
    public static final String v1 = "$v1";

    // first four arguments to a function/subroutine, if needed
    public static final String a0 = "$a0";
    public static final String a1 = "$a1";
    public static final String a2 = "$a2";
    public static final String a3 = "$a3";

    // safe function variable, must not be overwritten by called subroutine
    public static final String s0 = "$s0";
    public static final String s1 = "$s1";
    public static final String s2 = "$s2";
    public static final String s3 = "$s3";

    public static final String sp = "$sp";  // stack pointer (top of stack)
    public static final String fp = "$fp";  // frame pointer (bottom of current stack frame)
    public static final String ra = "$ra";  // return address of most recent caller

}
