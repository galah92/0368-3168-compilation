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
        writer.printf("\tli $a0, 0xA\n");  // 0xA is newline char
        writer.printf("\tli $v0, 11\n");
        writer.printf("\tsyscall\n");
        writer.print("\tli $v0, 10\n");
        writer.print("\tsyscall\n");
        writer.close();

        dataWriter.close();

        PrintWriter fileWriter = new PrintWriter(outFile);
        fileWriter.append(stringWriter.toString());
        fileWriter.append(dataStringWriter.toString());
        fileWriter.close();
    }

    // SPIM reference: http://cgi.cse.unsw.edu.au/~cs1521/17s2/docs/spim.php

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
    public static final String s4 = "$s4";
    public static final String s5 = "$s5";
    public static final String s6 = "$s6";
    public static final String s7 = "$s7";

    // temporaries: must be saved by caller to subroutine
    public static final String t0 = "$t0";
    public static final String t1 = "$t1";
    public static final String t2 = "$t2";
    public static final String t3 = "$t3";
    public static final String t4 = "$t4";
    public static final String t5 = "$t5";
    public static final String t6 = "$t6";
    public static final String t7 = "$t7";
    public static final String t8 = "$t8";
    public static final String t9 = "$t9";

    public static final String sp = "$sp";  // stack pointer (top of stack)
    public static final String fp = "$fp";  // frame pointer (bottom of current stack frame)
    public static final String ra = "$ra";  // return address of most recent caller

}
