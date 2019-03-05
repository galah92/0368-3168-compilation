package pcomp;

import java.util.*;


public class IR
{

    private static final Deque<IRComm> commands = new ArrayDeque<IRComm>();

    public static void add(IRComm cmd) { commands.add(cmd); }
    
    public static void toMIPS() { for (IRComm comm : commands) { comm.toMIPS(); } }

    public static class Stack
    {

        public static class alloc extends IRComm
        {
            int offset;
            public alloc(int offset) { this.offset = offset; }
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

    public static class dereference extends IRComm
    {
        TempReg src;
        public dereference(TempReg src) { this.src = src; }
        public void toMIPS()
        {
            MIPSGen.writer.printf("\tlw Temp_%d, (Temp_%d)\n", src.id, src.id);
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

    public static class frameGetOffset extends IRComm
    {
        TempReg dst;
        int offset;
        public frameGetOffset(TempReg dst, int offset) { this.dst = dst; this.offset = offset; }
        public void toMIPS()
        {
            MIPSGen.writer.printf("\tadd Temp_%d, $fp, %d\n", dst.id, offset * MIPSGen.WORD);
        }
    }

    public static class sw extends IRComm
    {
        TempReg dst;
        TempReg src;
        public sw(TempReg dst, TempReg src) { this.dst = dst; this.src = src; }
        public void toMIPS()
        {
            MIPSGen.writer.printf("\tsw Temp_%d, (Temp_%d)\n", src.id, dst.id);
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

    public static class lw extends IRComm
    {
        TempReg dst;
        int val;
        public lw(TempReg dst, int val) { this.dst = dst; this.val = val; }
        public void toMIPS()
        {
            MIPSGen.writer.printf("\tlw Temp_%d, %d\n", dst.id, val);
        }
    }

    public static class heapAlloc extends IRComm
    {
        TempReg dst;
        TempReg numBytes;
        public heapAlloc(TempReg dst, TempReg numBytes) { this.dst = dst; this.numBytes = numBytes; }
        public void toMIPS()
        {
            MIPSGen.writer.printf("\t# start of malloc\n");
            MIPSGen.writer.printf("\tmove $a0, Temp_%d\n", numBytes.id);
            MIPSGen.writer.printf("\tsll $a0, $a0, %d\n", MIPSGen.WORD);
            MIPSGen.writer.printf("\tli $v0, 9\n");
            MIPSGen.writer.printf("\tsyscall\n");
            MIPSGen.writer.printf("\tmove Temp_%d, $v0\n", dst.id);
            MIPSGen.writer.printf("\t# end of malloc\n");
        }
    }

    public static class calcOffset extends IRComm
    {
        TempReg dst;
        TempReg src;
        TempReg offset;
        public calcOffset(TempReg dst, TempReg src, TempReg offset) { this.dst = dst; this.src = src; this.offset = offset; }
        public void toMIPS()
        {
            // TODO: boundary-check!
            MIPSGen.writer.printf("\tsll Temp_%d, Temp_%d, %d\n", offset.id, offset.id, MIPSGen.WORD);
            MIPSGen.writer.printf("\tadd Temp_%d, Temp_%d, Temp_%d\n", dst.id, src.id, offset.id);
        }
    }

    public static class heapGet extends IRComm
    {
        TempReg dst;
        TempReg src;
        TempReg offset;
        public heapGet(TempReg dst, TempReg src, TempReg offset) { this.dst = dst; this.src = src; this.offset = offset; }
        public void toMIPS()
        {
            // TODO: boundary-check!
            MIPSGen.writer.printf("\tsll Temp_%d, Temp_%d, %d\n", offset.id, offset.id, MIPSGen.WORD);
            MIPSGen.writer.printf("\tadd Temp_%d, Temp_%d, Temp_%d\n", src.id, src.id, offset.id);
            MIPSGen.writer.printf("\tlw Temp_%d, (Temp_%d)\n", dst.id, src.id);
        }
    }

    public static class heapSet extends IRComm
    {
        TempReg dst;
        TempReg src;
        public heapSet(TempReg dst, TempReg src) { this.dst = dst; this.src = src; }
        public void toMIPS()
        {
            // TODO: boundary-check!
            MIPSGen.writer.printf("\tsw Temp_%d, (Temp_%d)\n", src.id, dst.id);
        }
    }

    public static class stringLiteral extends IRComm
    {
        TempReg dst;
        String str;
        public stringLiteral(TempReg dst, String str) { this.dst = dst; this.str = str; }
        public void toMIPS()
        {
            String label = IRComm.getLabel("string_literal");
            MIPSGen.dataWriter.printf("%s: .asciiz %s\n", label, str);
            MIPSGen.writer.printf("\tla Temp_%d, %s\n", dst.id, label);
        }
    }

    public static class printInt extends IRComm
    {
        TempReg value;
        public printInt(TempReg value) { this.value = value; }
        public void toMIPS()
        {
            // // print_int
            MIPSGen.writer.printf("\t# start of print_int\n");
            MIPSGen.writer.printf("\tmove $a0, Temp_%d\n", value.id);
            MIPSGen.writer.printf("\tli $v0, 1\n");
            MIPSGen.writer.printf("\tsyscall\n");
            // // print_char (whitespace)
            MIPSGen.writer.printf("\tli $a0, 32\n");
            MIPSGen.writer.printf("\tli $v0, 11\n");
            MIPSGen.writer.printf("\tsyscall\n");
            MIPSGen.writer.printf("\t# end of print_int\n");
        }
    }

    public static class printString extends IRComm
    {
        TempReg value;
        public printString(TempReg value) { this.value = value; }
        public void toMIPS()
        {
            MIPSGen.writer.printf("\t# start of print_string\n");
            MIPSGen.writer.printf("\tmove $a0, Temp_%d\n", value.id);
            MIPSGen.writer.printf("\tli $v0, 4\n");
            MIPSGen.writer.printf("\tsyscall\n");
            MIPSGen.writer.printf("\t# end of print_string\n");
        }
    }

    public static class intBinOp extends IRComm
    {
        TempReg dst;
        TempReg left;
        TempReg right;
        char op;
        public intBinOp(TempReg dst, TempReg left, TempReg right, char op) { this.dst = dst; this.left = left; this.right = right; this.op = op; }
        public void toMIPS()
        {
            switch (op)
            {
            case '+':
                MIPSGen.writer.printf("\tadd Temp_%d, Temp_%d, Temp_%d\n", dst.id, left.id, right.id);
                break;
            case '*':
                MIPSGen.writer.printf("\tmul Temp_%d, Temp_%d, Temp_%d\n", dst.id, left.id, right.id);
                break;
            case '<':
                String ltEndLabel = IRComm.getLabel("ltEnd");
                MIPSGen.writer.printf("\tli Temp_%d, 1\n", dst.id);  // be positive - assume equality
                MIPSGen.writer.printf("\tblt Temp_%d, Temp_%d, %s\n", left.id, right.id, ltEndLabel);
                MIPSGen.writer.printf("\tli Temp_%d, 0\n", dst.id);  // guess not
                MIPSGen.writer.printf("%s:\n", ltEndLabel);
                break;
            case '=':
                String eqEndLabel = IRComm.getLabel("eqEnd");
                MIPSGen.writer.printf("\tli Temp_%d, 1\n", dst.id);  // be positive - assume equality
                MIPSGen.writer.printf("\tbeq Temp_%d, Temp_%d, %s\n", left.id, right.id, eqEndLabel);
                MIPSGen.writer.printf("\tli Temp_%d, 0\n", dst.id);  // guess not
                MIPSGen.writer.printf("%s:\n", eqEndLabel);
                break;
            }
        }
    }

    public static class beqz extends IRComm
    {
        TempReg value;
        String label;
        public beqz(TempReg value, String label) { this.value = value; this.label = label; }
        public void toMIPS()
        {
            MIPSGen.writer.printf("\tbeqz Temp_%d, %s\n", value.id, label);
        }
    }

}
