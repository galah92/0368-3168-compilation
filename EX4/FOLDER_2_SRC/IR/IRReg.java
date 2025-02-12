package pcomp;


public abstract class IRReg
{

    public abstract String toMIPS();

    public static class TempReg extends IRReg
    {
        private static int counter = 0;

        private final int id = counter++;

        @Override public String toMIPS() { return String.format("Temp_%d", id); }
    }

    private static class Zero extends IRReg
    {
        @Override public String toMIPS() { return MIPS.zero; }
    }

    private static class SP extends IRReg
    {
        @Override public String toMIPS() { return MIPS.sp; }
    }

    private static class FP extends IRReg
    {
        @Override public String toMIPS() { return MIPS.fp; }
    }

    private static class RA extends IRReg
    {
        @Override public String toMIPS() { return MIPS.ra; }
    }

    private static class V0 extends IRReg
    {
        @Override public String toMIPS() { return MIPS.v0; }
    }

    private static class V1 extends IRReg
    {
        @Override public String toMIPS() { return MIPS.v1; }
    }

    private static class A0 extends IRReg
    {
        @Override public String toMIPS() { return MIPS.a0; }
    }

    private static class A1 extends IRReg
    {
        @Override public String toMIPS() { return MIPS.a1; }
    }

    private static class A2 extends IRReg
    {
        @Override public String toMIPS() { return MIPS.a2; }
    }

    private static class A3 extends IRReg
    {
        @Override public String toMIPS() { return MIPS.a3; }
    }

    private static class S0 extends IRReg
    {
        @Override public String toMIPS() { return MIPS.s0; }
    }

    private static class S1 extends IRReg
    {
        @Override public String toMIPS() { return MIPS.s1; }
    }

    private static class S2 extends IRReg
    {
        @Override public String toMIPS() { return MIPS.s2; }
    }

    private static class S3 extends IRReg
    {
        @Override public String toMIPS() { return MIPS.s3; }
    }

    private static class T0 extends IRReg
    {
        @Override public String toMIPS() { return MIPS.t0; }
    }

    private static class T1 extends IRReg
    {
        @Override public String toMIPS() { return MIPS.t1; }
    }

    private static class T2 extends IRReg
    {
        @Override public String toMIPS() { return MIPS.t2; }
    }

    private static class T3 extends IRReg
    {
        @Override public String toMIPS() { return MIPS.t3; }
    }

    private static class T4 extends IRReg
    {
        @Override public String toMIPS() { return MIPS.t4; }
    }

    private static class T5 extends IRReg
    {
        @Override public String toMIPS() { return MIPS.t5; }
    }

    private static class T6 extends IRReg
    {
        @Override public String toMIPS() { return MIPS.t6; }
    }

    private static class T7 extends IRReg
    {
        @Override public String toMIPS() { return MIPS.t7; }
    }

    public static final IRReg zero = new IRReg.Zero();
    
    // stack pointer (top of stack)
    public static final IRReg sp = new IRReg.SP();
    // frame pointer (bottom of current stack frame)
    public static final IRReg fp = new IRReg.FP();
    // return address of most recent caller
    public static final IRReg ra = new IRReg.RA();

    public static final IRReg v0 = new IRReg.V0();
    public static final IRReg v1 = new IRReg.V1();

    public static final IRReg a0 = new IRReg.A0();
    public static final IRReg a1 = new IRReg.A1();
    public static final IRReg a2 = new IRReg.A2();
    public static final IRReg a3 = new IRReg.A3();

    public static final IRReg s0 = new IRReg.S0();
    public static final IRReg s1 = new IRReg.S1();
    public static final IRReg s2 = new IRReg.S2();
    public static final IRReg s3 = new IRReg.S3();
    
    public static final IRReg t0 = new IRReg.T0();
    public static final IRReg t1 = new IRReg.T1();
    public static final IRReg t2 = new IRReg.T2();
    public static final IRReg t3 = new IRReg.T3();
    public static final IRReg t4 = new IRReg.T4();
    public static final IRReg t5 = new IRReg.T5();
    public static final IRReg t6 = new IRReg.T6();
    public static final IRReg t7 = new IRReg.T7();

}
