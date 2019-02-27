package pcomp;


public class IRComm_StoreLocal extends IRComm
{
	TempReg dst;
	int offset;
	
	public IRComm_StoreLocal(TempReg dst, int offset)
	{
		this.dst = dst;
		this.offset = offset;
	}
	
	public void toMIPS()
	{
		MIPSGen.sw(dst, String.format("%d($fp)", -MIPSGen.WORD * offset));
	}
}
