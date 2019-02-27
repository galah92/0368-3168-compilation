package pcomp;


public class IRComm_Load extends IRComm
{
	TempReg dst;
	String var_name;
	
	public IRComm_Load(TempReg dst,String var_name)
	{
		this.dst = dst;
		this.var_name = var_name;
	}
	
	public void toMIPS()
	{
		MIPSGen.load(dst,var_name);
	}
}
