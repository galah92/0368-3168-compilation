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
            MIPSGen.writer.printf("\t# start of print_int\n");
            // print_int
            MIPSGen.move(MIPSGen.a0, t);
            MIPSGen.li(MIPSGen.v0, 1);
            MIPSGen.syscall();
            // print_char (whitespace)
            MIPSGen.li(MIPSGen.a0, 32);
            MIPSGen.li(MIPSGen.v0, 11);
            MIPSGen.syscall();
            MIPSGen.writer.printf("\t# end of print_int\n");
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
            MIPSGen.writer.printf("\t# start of print_string\n");
            // print_string
            MIPSGen.move(MIPSGen.a0, t);
            MIPSGen.li(MIPSGen.v0, 4);
            MIPSGen.syscall();
            MIPSGen.writer.printf("\t# end of print_string\n");
        }

    }

}
