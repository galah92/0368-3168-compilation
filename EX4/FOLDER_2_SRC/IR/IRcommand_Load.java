package IR;

import pcomp.*;


public class IRcommand_Load extends IRcommand
{
	TempReg dst;
	String var_name;
	
	public IRcommand_Load(TempReg dst,String var_name)
	{
		this.dst = dst;
		this.var_name = var_name;
	}
	
	public void toMIPS()
	{
		MIPSGen.load(dst,var_name);
	}
}
