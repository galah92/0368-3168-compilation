package IR;

import pcomp.*;
import MIPS.*;

public class IRcommand_Binop_LT_Integers extends IRcommand
{
	public TempReg t1;
	public TempReg t2;
	public TempReg dst;

	public IRcommand_Binop_LT_Integers(TempReg dst, TempReg t1, TempReg t2)
	{
		this.dst = dst;
		this.t1 = t1;
		this.t2 = t2;
	}
	
	public void MIPSme()
	{
		String label_end = getLabel("end");
		String label_AssignOne = getLabel("AssignOne");
		String label_AssignZero = getLabel("AssignZero");
		
		/* [2] if (t1< t2) goto label_AssignOne;  */
		/*     if (t1>=t2) goto label_AssignZero; */
		sir_MIPS_a_lot.getInstance().blt(t1, t2, label_AssignOne);
		sir_MIPS_a_lot.getInstance().bge(t1, t2, label_AssignZero);

		/* [3] label_AssignOne: */
		/*                      */
		/*         t3 := 1      */
		/*         goto end;    */
		sir_MIPS_a_lot.getInstance().label(label_AssignOne);
		sir_MIPS_a_lot.getInstance().li(dst, 1);
		sir_MIPS_a_lot.getInstance().jump(label_end);

		/* [4] label_AssignZero: */
		/*                       */
		/*         t3 := 1       */
		/*         goto end;     */
		/*                       */
		sir_MIPS_a_lot.getInstance().label(label_AssignZero);
		sir_MIPS_a_lot.getInstance().li(dst, 0);
		sir_MIPS_a_lot.getInstance().jump(label_end);

		/* [5] label_end: */
		sir_MIPS_a_lot.getInstance().label(label_end);
	}
}
