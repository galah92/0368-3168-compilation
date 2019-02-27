package pcomp;


public class IRcommand_Binop_Add_Integers extends IRcommand
{

	public TempReg t1;
	public TempReg t2;
	public TempReg dst;
	
	public IRcommand_Binop_Add_Integers(TempReg dst, TempReg t1, TempReg t2)
	{
		this.dst = dst;
		this.t1 = t1;
		this.t2 = t2;
	}

	@Override
	public void toMIPS()
	{
		MIPSGen.add(dst, t1, t2);
	}

}
