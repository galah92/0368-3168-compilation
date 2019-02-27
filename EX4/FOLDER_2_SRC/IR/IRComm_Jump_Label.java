package pcomp;


public class IRComm_Jump_Label extends IRComm
{
	String label_name;
	
	public IRComm_Jump_Label(String label_name)
	{
		this.label_name = label_name;
	}
	
	public void toMIPS()
	{
		MIPSGen.jump(label_name);
	}
}
