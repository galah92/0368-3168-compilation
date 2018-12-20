/***********/
/* PACKAGE */
/***********/
package IR;

/*******************/
/* GENERAL IMPORTS */
/*******************/

/*******************/
/* PROJECT IMPORTS */
/*******************/
import pcomp.*;


public class IRcommand_Jump_Label extends IRcommand
{
	String label_name;
	
	public IRcommand_Jump_Label(String label_name)
	{
		this.label_name = label_name;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		MIPSGen.jump(label_name);
	}
}
