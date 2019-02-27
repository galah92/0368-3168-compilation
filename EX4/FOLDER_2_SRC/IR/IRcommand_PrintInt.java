package pcomp;


public class IRcommand_PrintInt extends IRcommand
{

	TempReg t;
	
	public IRcommand_PrintInt(TempReg t)
	{
		this.t = t;
	}
	
	public void toMIPS()
	{
		// MIPSGen.print_int(t);
		MIPSGen.move("$a0", t);
		MIPSGen.li("$v0", 1);
		MIPSGen.syscall();
		MIPSGen.li("$a0", 32);
		MIPSGen.li("$v0", 11);
		MIPSGen.syscall();
	}

}
