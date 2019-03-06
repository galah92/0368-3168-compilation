package pcomp;


public class IRReg
{
    private static int counter = 0;

    private final int id = counter++;

    public String toMIPS() { return String.format("$t%d", id % 8); }

    public static final IRReg ZeroReg = new IRReg();
}
