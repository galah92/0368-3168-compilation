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
		sir_MIPS_a_lot.getInstance().print_int(t);
	}
}
