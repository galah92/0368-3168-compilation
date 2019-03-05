package pcomp;


public class TempReg
{
    private static int counter = 0;

    public final int id = (counter++) % 8;

    public static final TempReg ZeroReg = new TempReg();
}
