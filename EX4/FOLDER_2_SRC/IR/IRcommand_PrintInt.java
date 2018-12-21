package IR;

import pcomp.*;

public class IRcommand_PrintInt extends IRcommand
{
	TempReg t;
	
	public IRcommand_PrintInt(TempReg t)
	{
		this.t = t;
	}
	
	public void toMIPS()
	{
		MIPSGen.print_int(t);
	}
}
