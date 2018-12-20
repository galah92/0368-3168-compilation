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


public class IRcommandConstInt extends IRcommand
{
	TempReg t;
	int value;
	
	public IRcommandConstInt(TempReg t,int value)
	{
		this.t = t;
		this.value = value;
	}
	
	/***************/
	/* MIPS me !!! */
	/***************/
	public void MIPSme()
	{
		MIPSGen.li(t,value);
	}
}
