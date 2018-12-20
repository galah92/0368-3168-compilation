package IR;

import pcomp.*;
import MIPS.*;

public class IRcommand_Store extends IRcommand
{
	String var_name;
	TempReg src;
	
	public IRcommand_Store(String var_name, TempReg src)
	{
		this.src = src;
		this.var_name = var_name;
	}
	
	public void MIPSme()
	{
		sir_MIPS_a_lot.getInstance().store(var_name,src);
	}
}
