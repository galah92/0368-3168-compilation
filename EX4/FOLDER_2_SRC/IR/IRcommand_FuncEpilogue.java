package pcomp;


public class IRcommand_FuncEpilogue extends IRcommand
{
	int numLocals;
	
	public IRcommand_FuncEpilogue(int numLocals)
	{
		this.numLocals = numLocals;
	}
	
	public void toMIPS()
	{
		MIPSGen.func_epilogue(numLocals);
	}
}
