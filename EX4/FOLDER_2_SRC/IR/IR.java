package pcomp;

import java.util.*;


public class IR
{

    public static abstract class IRComm
    {

        public abstract void toMIPS();
        
    }

    private static final Deque<IRComm> commands = new ArrayDeque<IRComm>();

    public static void add(IRComm cmd) { commands.add(cmd); }
    
    public static void toMIPS() { for (IRComm comm : commands) { comm.toMIPS(); } }

    public static int uniqueLabelCounter = 0;

    public static String uniqueLabel(String label)
    {
        return String.format("label_%d_%s", uniqueLabelCounter++, label);
    }

    public static class Stack
    {

        public static class alloc extends IR.IRComm
        {
            int offset;
            public alloc(int offset) { this.offset = offset; }
            public void toMIPS()
            {
                MIPSGen.writer.printf("\taddi $sp, $sp, -%d\n", offset * MIPSGen.WORD);
            }
        }

        public static class release extends IR.IRComm
        {
            int offset;
            public release(int offset) { this.offset = offset; }
            public void toMIPS()
            {
                MIPSGen.writer.printf("\taddi $sp, $sp, %d\n", offset * MIPSGen.WORD);
            }
        }

        public static class set extends IR.IRComm
        {
            TempReg src;
            int offset;
            public set(TempReg src, int offset) { this.src = src; this.offset = offset; }
            public void toMIPS()
            {
                MIPSGen.writer.printf("\tsw $t%d, %d($sp)\n", src.id, offset * MIPSGen.WORD);
            }
        }

        public static class get extends IR.IRComm
        {
            TempReg dst;
            int offset;
            public get(TempReg dst, int offset) { this.dst = dst; this.offset = offset; }
            public void toMIPS()
            {
                MIPSGen.writer.printf("\tlw $t%d, %d($sp)\n", dst.id, offset * MIPSGen.WORD);
            }
        }

    }

    public static class dereference extends IR.IRComm
    {
        TempReg src;
        public dereference(TempReg src) { this.src = src; }
        public void toMIPS()
        {
            MIPSGen.writer.printf("\tlw $t%d, ($t%d)\n", src.id, src.id);
        }
    }

    public static class funcPrologue extends IR.IRComm
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

    public static class funcEpilogue extends IR.IRComm
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

    public static class frameSet extends IR.IRComm
    {
        TempReg src;
        int offset;
        public frameSet(TempReg src, int offset) { this.src = src; this.offset = offset; }
        public void toMIPS()
        {
            MIPSGen.writer.printf("\tsw $t%d, %d($fp)\n", src.id, offset * MIPSGen.WORD);
        }
    }

    public static class frameGet extends IR.IRComm
    {
        TempReg dst;
        int offset;
        public frameGet(TempReg dst, int offset) { this.dst = dst; this.offset = offset; }
        public void toMIPS()
        {
            MIPSGen.writer.printf("\tlw $t%d, %d($fp)\n", dst.id, offset * MIPSGen.WORD);
        }
    }

    public static class frameGetOffset extends IR.IRComm
    {
        TempReg dst;
        int offset;
        public frameGetOffset(TempReg dst, int offset) { this.dst = dst; this.offset = offset; }
        public void toMIPS()
        {
            MIPSGen.writer.printf("\tadd $t%d, $fp, %d\n", dst.id, offset * MIPSGen.WORD);
        }
    }

    public static class sw extends IR.IRComm
    {
        TempReg dst;
        TempReg src;
        public sw(TempReg dst, TempReg src) { this.dst = dst; this.src = src; }
        public void toMIPS()
        {
            MIPSGen.writer.printf("\tsw $t%d, ($t%d)\n", src.id, dst.id);
        }
    }

    public static class jump extends IR.IRComm
    {
        String label;
        public jump(String label) { this.label = label; }
        public void toMIPS()
        {
            MIPSGen.writer.printf("\tj %s\n", label);
        }
    }

    public static class label extends IR.IRComm
    {
        String label;
        public label(String label) { this.label = label; }
        public void toMIPS()
        {
            MIPSGen.writer.println();
            MIPSGen.writer.printf("%s:\n", label);
        }
    }

    public static class setRetVal extends IR.IRComm
    {
        TempReg src;
        public setRetVal(TempReg src) { this.src = src; }
        public void toMIPS()
        {
            MIPSGen.writer.printf("\tmove $v0, $t%d\n", src.id);
        }
    }

    public static class getRetVal extends IR.IRComm
    {
        TempReg dst;
        public getRetVal(TempReg dst) { this.dst = dst; }
        public void toMIPS()
        {
            MIPSGen.writer.printf("\tmove $t%d, $v0\n", dst.id);
        }
    }

    public static class move extends IR.IRComm
    {
        TempReg dst;
        TempReg src;
        public move(TempReg dst, TempReg src) { this.dst = dst; this.src = src; }
        public void toMIPS()
        {
            MIPSGen.writer.printf("\tmove $t%d, $t%d\n", dst.id, src.id);
        }
    }

    public static class push extends IR.IRComm
    {
        TempReg src;
        public push(TempReg src) { this.src = src; }
        public void toMIPS()
        {
            MIPSGen.writer.printf("\taddi $sp, $sp, -4\n");
            MIPSGen.writer.printf("\tsw $t%d, ($sp)\n", src.id);
        }
    }

    public static class pop extends IR.IRComm
    {
        TempReg dst;
        public pop(TempReg dst) { this.dst = dst; }
        public void toMIPS()
        {
            MIPSGen.writer.printf("\tlw $t%d, ($sp)\n", dst.id);
            MIPSGen.writer.printf("\taddi $sp, $sp, 4\n");
        }
    }

    public static class jal extends IR.IRComm
    {
        String label;
        public jal(String label) { this.label = label; }
        public void toMIPS()
        {
            MIPSGen.writer.printf("\tjal %s\n", label);
        }
    }

    public static class li extends IR.IRComm
    {
        TempReg dst;
        int val;
        public li(TempReg dst, int val) { this.dst = dst; this.val = val; }
        public void toMIPS()
        {
            MIPSGen.writer.printf("\tli $t%d, %d\n", dst.id, val);
        }
    }

    public static class lw extends IR.IRComm
    {
        TempReg dst;
        int val;
        public lw(TempReg dst, int val) { this.dst = dst; this.val = val; }
        public void toMIPS()
        {
            MIPSGen.writer.printf("\tlw $t%d, %d\n", dst.id, val);
        }
    }

    public static class heapAlloc extends IR.IRComm
    {
        TempReg dst;
        TempReg numBytes;
        public heapAlloc(TempReg dst, TempReg numBytes) { this.dst = dst; this.numBytes = numBytes; }
        public void toMIPS()
        {
            MIPSGen.writer.printf("\t# start of malloc\n");
            MIPSGen.writer.printf("\tmove $a0, $t%d\n", numBytes.id);
            MIPSGen.writer.printf("\tsll $a0, $a0, %d\n", MIPSGen.WORD);
            MIPSGen.writer.printf("\tli $v0, 9\n");
            MIPSGen.writer.printf("\tsyscall\n");
            MIPSGen.writer.printf("\tmove $t%d, $v0\n", dst.id);
            MIPSGen.writer.printf("\t# end of malloc\n");
        }
    }

    public static class calcOffset extends IR.IRComm
    {
        TempReg dst;
        TempReg src;
        TempReg offset;
        public calcOffset(TempReg dst, TempReg src, TempReg offset) { this.dst = dst; this.src = src; this.offset = offset; }
        public void toMIPS()
        {
            // TODO: boundary-check!
            MIPSGen.writer.printf("\tsll $t%d, $t%d, %d\n", offset.id, offset.id, MIPSGen.WORD);
            MIPSGen.writer.printf("\tadd $t%d, $t%d, $t%d\n", dst.id, src.id, offset.id);
        }
    }

    public static class heapGet extends IR.IRComm
    {
        TempReg dst;
        TempReg src;
        TempReg offset;
        public heapGet(TempReg dst, TempReg src, TempReg offset) { this.dst = dst; this.src = src; this.offset = offset; }
        public void toMIPS()
        {
            // TODO: boundary-check!
            MIPSGen.writer.printf("\tsll $t%d, $t%d, %d\n", offset.id, offset.id, MIPSGen.WORD);
            MIPSGen.writer.printf("\tadd $t%d, $t%d, $t%d\n", src.id, src.id, offset.id);
            MIPSGen.writer.printf("\tlw $t%d, ($t%d)\n", dst.id, src.id);
        }
    }

    public static class heapSet extends IR.IRComm
    {
        TempReg dst;
        TempReg src;
        public heapSet(TempReg dst, TempReg src) { this.dst = dst; this.src = src; }
        public void toMIPS()
        {
            // TODO: boundary-check!
            MIPSGen.writer.printf("\tsw $t%d, ($t%d)\n", src.id, dst.id);
        }
    }

    public static class stringLiteral extends IR.IRComm
    {
        TempReg dst;
        String str;
        public stringLiteral(TempReg dst, String str) { this.dst = dst; this.str = str; }
        public void toMIPS()
        {
            String label = IR.uniqueLabel("string_literal");
            MIPSGen.dataWriter.printf("%s: .asciiz %s\n", label, str);
            MIPSGen.writer.printf("\tla $t%d, %s\n", dst.id, label);
        }
    }

    public static class printInt extends IR.IRComm
    {
        TempReg value;
        public printInt(TempReg value) { this.value = value; }
        public void toMIPS()
        {
            // // print_int
            MIPSGen.writer.printf("\t# start of print_int\n");
            MIPSGen.writer.printf("\tmove $a0, $t%d\n", value.id);
            MIPSGen.writer.printf("\tli $v0, 1\n");
            MIPSGen.writer.printf("\tsyscall\n");
            // // print_char (whitespace)
            MIPSGen.writer.printf("\tli $a0, 32\n");
            MIPSGen.writer.printf("\tli $v0, 11\n");
            MIPSGen.writer.printf("\tsyscall\n");
            MIPSGen.writer.printf("\t# end of print_int\n");
        }
    }

    public static class printString extends IR.IRComm
    {
        TempReg value;
        public printString(TempReg value) { this.value = value; }
        public void toMIPS()
        {
            MIPSGen.writer.printf("\t# start of print_string\n");
            MIPSGen.writer.printf("\tmove $a0, $t%d\n", value.id);
            MIPSGen.writer.printf("\tli $v0, 4\n");
            MIPSGen.writer.printf("\tsyscall\n");
            MIPSGen.writer.printf("\t# end of print_string\n");
        }
    }

    public static class intBinOp extends IR.IRComm
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
                MIPSGen.writer.printf("\tadd $t%d, $t%d, $t%d\n", dst.id, left.id, right.id);
                break;
            case '*':
                MIPSGen.writer.printf("\tmul $t%d, $t%d, $t%d\n", dst.id, left.id, right.id);
                break;
            case '<':
                String ltEndLabel = IR.uniqueLabel("ltEnd");
                MIPSGen.writer.printf("\tli $t%d, 1\n", dst.id);  // be positive - assume equality
                MIPSGen.writer.printf("\tblt $t%d, $t%d, %s\n", left.id, right.id, ltEndLabel);
                MIPSGen.writer.printf("\tli $t%d, 0\n", dst.id);  // guess not
                MIPSGen.writer.printf("%s:\n", ltEndLabel);
                break;
            case '=':
                String eqEndLabel = IR.uniqueLabel("eqEnd");
                MIPSGen.writer.printf("\tli $t%d, 1\n", dst.id);  // be positive - assume equality
                MIPSGen.writer.printf("\tbeq $t%d, $t%d, %s\n", left.id, right.id, eqEndLabel);
                MIPSGen.writer.printf("\tli $t%d, 0\n", dst.id);  // guess not
                MIPSGen.writer.printf("%s:\n", eqEndLabel);
                break;
            }
        }
    }

    public static class beqz extends IR.IRComm
    {
        TempReg value;
        String label;
        public beqz(TempReg value, String label) { this.value = value; this.label = label; }
        public void toMIPS()
        {
            MIPSGen.writer.printf("\tbeqz $t%d, %s\n", value.id, label);
        }
    }

}
