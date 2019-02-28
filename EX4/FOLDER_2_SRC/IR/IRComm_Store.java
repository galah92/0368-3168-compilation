package pcomp;


public class IRComm_Store extends IRComm
{
	String var_name;
	TempReg src;
	
	public IRComm_Store(String var_name, TempReg src)
	{
		this.src = src;
		this.var_name = var_name;
	}

	@Override	
	public void toMIPS()
	{
		MIPSGen.store(var_name, src);
	}
}
