package pcomp;


public class TempReg
{
    private static int serialCounter = 0;

    public final int serialNum = serialCounter++;

    public static final TempReg ZeroReg = new TempReg();
}
