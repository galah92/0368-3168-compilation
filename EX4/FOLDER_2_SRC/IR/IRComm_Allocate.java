package pcomp;


public class IRComm_Allocate extends IRComm
{
	String var_name;
	
	public IRComm_Allocate(String var_name)
	{
		this.var_name = var_name;
	}
	
	public void toMIPS()
	{
		MIPSGen.allocate(var_name);
	}
}
