package pcomp;


public class IRComm_FuncPrologue extends IRComm
{
	int numLocals;
	
	public IRComm_FuncPrologue(int numLocals)
	{
		this.numLocals = numLocals;
	}
	
	public void toMIPS()
	{
		MIPSGen.func_prologue(numLocals);
	}
}
