package pcomp;


public class IRReg
{
    private static int counter = 0;

    public final int id = (counter++) % 8;

    public static final IRReg ZeroReg = new IRReg();
}
