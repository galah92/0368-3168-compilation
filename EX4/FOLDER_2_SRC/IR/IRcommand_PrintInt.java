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


public class IRcommand_PrintInt extends IRcommand
{
	TempReg t;
	
	public IRcommand_PrintInt(TempReg t)
	{
		this.t = t;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		MIPSGen.print_int(t);
	}
}
