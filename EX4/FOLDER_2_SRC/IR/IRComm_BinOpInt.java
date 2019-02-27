package pcomp;


public class IRComm_BinOpInt extends IRComm
{

    public char op;
	public TempReg t1;
	public TempReg t2;
	public TempReg dst;

	public IRComm_BinOpInt(char op, TempReg dst, TempReg t1, TempReg t2)
	{
        this.op = op;
		this.dst = dst;
		this.t1 = t1;
		this.t2 = t2;
	}
	
	@Override
	public void toMIPS()
	{
        switch (op)
        {
        case '+':
            MIPSGen.add(dst, t1, t2);
            break;
        case '*':
            MIPSGen.mul(dst, t1, t2);
            break;
        case '<':
            String ltEndLabel = getLabel("ltEnd");
            MIPSGen.li(dst, 1);  // be positive - assume equality
            MIPSGen.blt(t1, t2, ltEndLabel);
            MIPSGen.li(dst, 0);  // guess not
            MIPSGen.label(ltEndLabel);
            break;
        case '=':
            String eqEndLabel = getLabel("eqEnd");
            MIPSGen.li(dst, 1);  // be positive - assume equality
            MIPSGen.beq(t1, t2, eqEndLabel);
            MIPSGen.li(dst, 0);  // guess not
            MIPSGen.label(eqEndLabel);
            break;
        }
	}
	
}
