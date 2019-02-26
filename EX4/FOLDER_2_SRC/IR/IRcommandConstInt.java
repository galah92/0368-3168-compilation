package pcomp;


public class IRcommandConstInt extends IRcommand
{
	TempReg t;
	int value;
	
	public IRcommandConstInt(TempReg t, int value)
	{
		this.t = t;
		this.value = value;
	}
	
	public void toMIPS()
	{
		MIPSGen.li(t, value);
	}
}
