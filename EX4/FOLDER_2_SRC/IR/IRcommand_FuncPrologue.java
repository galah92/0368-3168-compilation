package pcomp;


public class IRcommand_FuncPrologue extends IRcommand
{
	int numLocals;
	
	public IRcommand_FuncPrologue(int numLocals)
	{
		this.numLocals = numLocals;
	}
	
	public void toMIPS()
	{
		MIPSGen.func_prologue(numLocals);
	}
}
