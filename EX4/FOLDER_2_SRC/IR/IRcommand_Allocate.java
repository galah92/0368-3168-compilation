package pcomp;


public class IRcommand_Allocate extends IRcommand
{
	String var_name;
	
	public IRcommand_Allocate(String var_name)
	{
		this.var_name = var_name;
	}
	
	public void toMIPS()
	{
		MIPSGen.allocate(var_name);
	}
}
