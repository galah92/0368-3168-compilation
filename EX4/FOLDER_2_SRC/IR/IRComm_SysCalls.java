package pcomp;


public abstract class IRComm_SysCalls extends IRComm
{

    public static class PrintInt extends IRComm
    {

        TempReg t;
        
        public PrintInt(TempReg t)
        {
            this.t = t;
        }
        
        @Override
        public void toMIPS()
        {
            // print_int
            MIPSGen.move(MIPSGen.a0, t);
            MIPSGen.li(MIPSGen.v0, 1);
            MIPSGen.syscall();
            // print_char (whitespace)
            MIPSGen.li(MIPSGen.a0, 32);
            MIPSGen.li(MIPSGen.v0, 11);
            MIPSGen.syscall();
        }

    }

    public static class PrintString extends IRComm
    {

        TempReg t;
	
        public PrintString(TempReg t)
        {
            this.t = t;
        }
        
        @Override
        public void toMIPS()
        {
            // print_string
            MIPSGen.move(MIPSGen.a0, t);
            MIPSGen.li(MIPSGen.v0, 4);
            MIPSGen.syscall();
        }

    }

}
