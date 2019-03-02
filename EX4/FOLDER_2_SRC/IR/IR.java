package pcomp;

import java.util.*;


public class IR
{

    private static final Deque<IRComm> commands = new ArrayDeque<IRComm>();

    public static void add(IRComm cmd) { commands.add(cmd); }
    
    public static void toMIPS() { for (IRComm comm : commands) { comm.toMIPS(); } }

    public static class Stack
    {

        public static class claim extends IRComm
        {
            int offset;
            public claim(int offset) { this.offset = offset; }
            public void toMIPS()
            {
                MIPSGen.writer.printf("\taddi $sp, $sp, -%d\n", offset * MIPSGen.WORD);
            }
        }

        public static class release extends IRComm
        {
            int offset;
            public release(int offset) { this.offset = offset; }
            public void toMIPS()
            {
                MIPSGen.writer.printf("\taddi $sp, $sp, %d\n", offset * MIPSGen.WORD);
            }
        }

        public static class set extends IRComm
        {
            TempReg src;
            int offset;
            public set(TempReg src, int offset) { this.src = src; this.offset = offset; }
            public void toMIPS()
            {
                MIPSGen.writer.printf("\tsw Temp_%d, %d($sp)\n", src.id, offset * MIPSGen.WORD);
            }
        }

        public static class get extends IRComm
        {
            TempReg dst;
            int offset;
            public get(TempReg dst, int offset) { this.dst = dst; this.offset = offset; }
            public void toMIPS()
            {
                MIPSGen.writer.printf("\tlw Temp_%d, %d($sp)\n", dst.id, offset * MIPSGen.WORD);
            }
        }

    }

    public static class funcPrologue extends IRComm
    {
        int numLocals;
        public funcPrologue(int numLocals) { this.numLocals = numLocals; }
        public void toMIPS()
        {
            MIPSGen.writer.printf("\t# start of function prologue\n");
            // save fp & ra
            MIPSGen.writer.printf("\taddi $sp, $sp, -8\n");
            MIPSGen.writer.printf("\tsw	$fp, 0($sp)\n");
            MIPSGen.writer.printf("\tsw	$ra, 4($sp)\n");
            // set fp = sp
            MIPSGen.writer.printf("\tmove $fp, $sp\n");
            // allocate stack frame
            MIPSGen.writer.printf("\taddi $sp, $sp, %d\n", -MIPSGen.WORD * numLocals);
            MIPSGen.writer.printf("\t# end of function prologue\n");
        }
    }

    public static class funcEpilogue extends IRComm
    {
        int numLocals;
        public funcEpilogue(int numLocals) { this.numLocals = numLocals; }
        public void toMIPS()
        {
            MIPSGen.writer.printf("\t# start of function epilogue\n");
            // deallocate stack frame
            MIPSGen.writer.printf("\taddi $sp, $sp, %d\n", MIPSGen.WORD * numLocals);
            // restore fp & ra
            MIPSGen.writer.printf("\tlw	$ra, 4($fp)\n");
            MIPSGen.writer.printf("\tlw	$fp, ($fp)\n");
            // deallocate place for ra & fp
            MIPSGen.writer.printf("\taddi $sp, $sp, 8\n");
            // jump back
            MIPSGen.writer.printf("\tjr $ra\n");
            MIPSGen.writer.printf("\t# end of function epilogue\n");
        }
    }

    public static class frameSet extends IRComm
    {
        TempReg src;
        int offset;
        public frameSet(TempReg src, int offset) { this.src = src; this.offset = offset; }
        public void toMIPS()
        {
            MIPSGen.writer.printf("\tsw Temp_%d, %d($fp)\n", src.id, offset * MIPSGen.WORD);
        }
    }

    public static class frameGet extends IRComm
    {
        TempReg dst;
        int offset;
        public frameGet(TempReg dst, int offset) { this.dst = dst; this.offset = offset; }
        public void toMIPS()
        {
            MIPSGen.writer.printf("\tlw Temp_%d, %d($fp)\n", dst.id, offset * MIPSGen.WORD);
        }
    }

    public static class jump extends IRComm
    {
        String label;
        public jump(String label) { this.label = label; }
        public void toMIPS()
        {
            MIPSGen.writer.printf("\tj %s\n", label);
        }
    }

    public static class label extends IRComm
    {
        String label;
        public label(String label) { this.label = label; }
        public void toMIPS()
        {
            MIPSGen.writer.println();
            MIPSGen.writer.printf("%s:\n", label);
        }
    }

    public static class setRetVal extends IRComm
    {
        TempReg src;
        public setRetVal(TempReg src) { this.src = src; }
        public void toMIPS()
        {
            MIPSGen.writer.printf("\tmove $v0, Temp_%d\n", src.id);
        }
    }

    public static class getRetVal extends IRComm
    {
        TempReg dst;
        public getRetVal(TempReg dst) { this.dst = dst; }
        public void toMIPS()
        {
            MIPSGen.writer.printf("\tmove Temp_%d, $v0\n", dst.id);
        }
    }

    public static class move extends IRComm
    {
        TempReg dst;
        TempReg src;
        public move(TempReg dst, TempReg src) { this.dst = dst; this.src = src; }
        public void toMIPS()
        {
            MIPSGen.writer.printf("\tmove Temp_%d, Temp_%d\n", dst.id, src.id);
        }
    }

    public static class push extends IRComm
    {
        TempReg src;
        public push(TempReg src) { this.src = src; }
        public void toMIPS()
        {
            MIPSGen.writer.printf("\taddi $sp, $sp, -4\n");
            MIPSGen.writer.printf("\tsw Temp_%d, ($sp)\n", src.id);
        }
    }

    public static class pop extends IRComm
    {
        TempReg dst;
        public pop(TempReg dst) { this.dst = dst; }
        public void toMIPS()
        {
            MIPSGen.writer.printf("\tlw Temp_%d, ($sp)\n", dst.id);
            MIPSGen.writer.printf("\taddi $sp, $sp, 4\n");
        }
    }

    public static class jal extends IRComm
    {
        String label;
        public jal(String label) { this.label = label; }
        public void toMIPS()
        {
            MIPSGen.writer.printf("\tjal %s\n", label);
        }
    }

    public static class li extends IRComm
    {
        TempReg dst;
        int val;
        public li(TempReg dst, int val) { this.dst = dst; this.val = val; }
        public void toMIPS()
        {
            MIPSGen.writer.printf("\tli Temp_%d, %d\n", dst.id, val);
        }
    }

    public static class malloc extends IRComm
    {
        int size;
        public malloc(int size) { this.size = size; }
        public void toMIPS()
        {
            MIPSGen.writer.printf("\t# start of malloc\n");
            MIPSGen.writer.printf("\tli $a0, %d\n", size);
            MIPSGen.writer.printf("\tli $v0, 9\n");
            MIPSGen.writer.printf("\tsyscall\n");
            MIPSGen.writer.printf("\t# end of malloc\n");
        }
    }

}
