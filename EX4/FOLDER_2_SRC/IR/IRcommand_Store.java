package IR;

import pcomp.*;

public class IRcommand_Store extends IRcommand
{
	String var_name;
	TempReg src;
	
	public IRcommand_Store(String var_name, TempReg src)
	{
		this.src = src;
		this.var_name = var_name;
	}
	
	public void toMIPS()
	{
		MIPSGen.store(var_name, src);
	}
}
