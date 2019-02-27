package pcomp;


public class IRComm_Jump_If_Eq_To_Zero extends IRComm
{
	TempReg t;
	String label_name;
	
	public IRComm_Jump_If_Eq_To_Zero(TempReg t, String label_name)
	{
		this.t = t;
		this.label_name = label_name;
	}
	
	public void toMIPS()
	{
		MIPSGen.beqz(t, label_name);
	}
}
