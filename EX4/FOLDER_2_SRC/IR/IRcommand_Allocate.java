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
import MIPS.*;

public class IRcommand_Allocate extends IRcommand
{
	String var_name;
	
	public IRcommand_Allocate(String var_name)
	{
		this.var_name = var_name;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		MIPSGen.allocate(var_name);
	}
}
