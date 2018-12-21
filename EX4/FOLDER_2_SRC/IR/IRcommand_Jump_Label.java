package IR;

import pcomp.*;


public class IRcommand_Jump_Label extends IRcommand
{
	String label_name;
	
	public IRcommand_Jump_Label(String label_name)
	{
		this.label_name = label_name;
	}
	
	public void toMIPS()
	{
		MIPSGen.jump(label_name);
	}
}
