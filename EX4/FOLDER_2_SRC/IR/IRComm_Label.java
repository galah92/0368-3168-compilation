package pcomp;


public class IRComm_Label extends IRComm
{
	String label_name;
	
	public IRComm_Label(String label_name)
	{
		this.label_name = label_name;
	}
	
	public void toMIPS()
	{
		MIPSGen.label(label_name);
	}
}
