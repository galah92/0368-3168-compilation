package IR;

import pcomp.*;


public class IRcommand_Label extends IRcommand
{
	String label_name;
	
	public IRcommand_Label(String label_name)
	{
		this.label_name = label_name;
	}
	
	public void toMIPS()
	{
		MIPSGen.label(label_name);
	}
}
