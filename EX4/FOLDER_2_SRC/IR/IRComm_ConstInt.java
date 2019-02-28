package pcomp;


public class IRComm_ConstInt extends IRComm
{

    TempReg t;
    int value;
    
    public IRComm_ConstInt(TempReg t, int value)
    {
        this.t = t;
        this.value = value;
    }
    
    public void toMIPS()
    {
        MIPSGen.li(t, value);
    }
    
}
