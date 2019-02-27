package pcomp;


public class IRComm_FuncEpilogue extends IRComm
{
	int numLocals;
	
	public IRComm_FuncEpilogue(int numLocals)
	{
		this.numLocals = numLocals;
	}
	
	public void toMIPS()
	{
		MIPSGen.func_epilogue(numLocals);
	}
}
