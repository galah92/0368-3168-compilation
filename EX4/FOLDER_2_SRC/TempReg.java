package pcomp;


public class TempReg
{
    private static int counter = 0;

    public final int id = counter++;

    public static final TempReg ZeroReg = new TempReg();
}
