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

public class IRcommand_Jump_If_Eq_To_Zero extends IRcommand
{
	TempReg t;
	String label_name;
	
	public IRcommand_Jump_If_Eq_To_Zero(TempReg t, String label_name)
	{
		this.t          = t;
		this.label_name = label_name;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		sir_MIPS_a_lot.getInstance().beqz(t,label_name);
	}
}
