package pcomp;


public class IRcommand_PrintInt extends IRcommand
{

	TempReg t;
	
	public IRcommand_PrintInt(TempReg t)
	{
		this.t = t;
	}
	
	@Override
	public void toMIPS()
	{
		// print_int
		MIPSGen.move(MIPSGen.a0, t);
		MIPSGen.li(MIPSGen.v0, 1);
		MIPSGen.syscall();
		// print_char (whitespace)
		MIPSGen.li(MIPSGen.a0, 32);
		MIPSGen.li(MIPSGen.v0, 11);
		MIPSGen.syscall();
	}

}
