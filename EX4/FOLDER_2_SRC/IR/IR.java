package pcomp;

import java.util.*;


abstract class IRComm
{
    public abstract void toMIPS();
}


public class IR
{

    private static final Deque<IRComm> commands = new ArrayDeque<IRComm>();

    public static void add(IRComm cmd) { commands.add(cmd); }
    
    public static void toMIPS() { for (IRComm comm : commands) { comm.toMIPS(); } }

    private static int uniqueLabelCounter = 0;

    public static String uniqueLabel(String label)
    {
        return String.format("label_%d_%s", uniqueLabelCounter++, label);
    }

    public static class Stack
    {

        public static class alloc extends IRComm
        {
            int offset;
            public alloc(int offset) { this.offset = offset; }
            public void toMIPS()
            {
                MIPS.writer.printf("\taddi $sp, $sp, -%d\n", offset * MIPS.WORD);
            }
        }

        public static class release extends IRComm
        {
            int offset;
            public release(int offset) { this.offset = offset; }
            public void toMIPS()
            {
                MIPS.writer.printf("\taddi $sp, $sp, %d\n", offset * MIPS.WORD);
            }
        }

        public static class set extends IRComm
        {
            IRReg src;
            int offset;
            public set(IRReg src, int offset) { this.src = src; this.offset = offset; }
            public void toMIPS()
            {
                MIPS.writer.printf("\tsw %s, %d($sp)\n", src.toMIPS(), offset * MIPS.WORD);
            }
        }

        public static class get extends IRComm
        {
            IRReg dst;
            int offset;
            public get(IRReg dst, int offset) { this.dst = dst; this.offset = offset; }
            public void toMIPS()
            {
                MIPS.writer.printf("\tlw %s, %d($sp)\n", dst.toMIPS(), offset * MIPS.WORD);
            }
        }

    }

    public static class funcPrologue extends IRComm
    {
        int numLocals;
        public funcPrologue(int numLocals) { this.numLocals = numLocals; }
        public void toMIPS()
        {
            // save fp & ra
            MIPS.writer.printf("\taddi $sp, $sp, -8  # start of function prologue\n");
            MIPS.writer.printf("\tsw $fp, 0($sp)\n");
            MIPS.writer.printf("\tsw $ra, 4($sp)\n");
            // set fp = sp
            MIPS.writer.printf("\tmove $fp, $sp\n");
            // allocate stack frame
            MIPS.writer.printf("\taddi $sp, $sp, %d  # end of function prologue\n", -MIPS.WORD * numLocals);
        }
    }

    public static class funcEpilogue extends IRComm
    {
        int numLocals;
        public funcEpilogue(int numLocals) { this.numLocals = numLocals; }
        public void toMIPS()
        {
            // deallocate stack frame
            MIPS.writer.printf("\taddi $sp, $sp, %d  # start of function epilogue\n", MIPS.WORD * numLocals);
            // restore fp & ra
            MIPS.writer.printf("\tlw	$ra, 4($fp)\n");
            MIPS.writer.printf("\tlw	$fp, ($fp)\n");
            // deallocate place for ra & fp
            MIPS.writer.printf("\taddi $sp, $sp, 8\n");
            // jump back
            MIPS.writer.printf("\tjr $ra  # end of function epilogue\n");
        }
    }

    public static class frameSet extends IRComm
    {
        IRReg src;
        int offset;
        public frameSet(IRReg src, int offset) { this.src = src; this.offset = offset; }
        public void toMIPS()
        {
            MIPS.writer.printf("\tsw %s, %d($fp)\n", src.toMIPS(), offset * MIPS.WORD);
        }
    }

    public static class frameGetOffset extends IRComm
    {
        IRReg dst;
        int offset;
        public frameGetOffset(IRReg dst, int offset) { this.dst = dst; this.offset = offset; }
        public void toMIPS()
        {
            MIPS.writer.printf("\tadd %s, $fp, %d\n", dst.toMIPS(), offset * MIPS.WORD);
        }
    }

    public static class sw extends IRComm
    {
        IRReg dst;
        IRReg src;
        public sw(IRReg dst, IRReg src) { this.dst = dst; this.src = src; }
        public void toMIPS()
        {
            MIPS.writer.printf("\tsw %s, (%s)\n", src.toMIPS(), dst.toMIPS());
        }
    }

    public static class jump extends IRComm
    {
        String label;
        public jump(String label) { this.label = label; }
        public void toMIPS()
        {
            MIPS.writer.printf("\tj %s\n", label);
        }
    }

    public static class label extends IRComm
    {
        String label;
        public label(String label) { this.label = label; }
        public void toMIPS()
        {
            MIPS.writer.printf("\n%s:\n", label);
        }
    }

    public static class setRetVal extends IRComm
    {
        IRReg src;
        public setRetVal(IRReg src) { this.src = src; }
        public void toMIPS()
        {
            MIPS.writer.printf("\tmove $v0, %s\n", src.toMIPS());
        }
    }

    public static class getRetVal extends IRComm
    {
        IRReg dst;
        public getRetVal(IRReg dst) { this.dst = dst; }
        public void toMIPS()
        {
            MIPS.writer.printf("\tmove %s, $v0\n", dst.toMIPS());
        }
    }

    public static class move extends IRComm
    {
        IRReg dst;
        IRReg src;
        public move(IRReg dst, IRReg src) { this.dst = dst; this.src = src; }
        public void toMIPS()
        {
            MIPS.writer.printf("\tmove %s, %s\n", dst.toMIPS(), src.toMIPS());
        }
    }

    public static class jal extends IRComm
    {
        String label;
        public jal(String label) { this.label = label; }
        public void toMIPS()
        {
            MIPS.writer.printf("\tjal %s\n", label);
        }
    }

    public static class li extends IRComm
    {
        IRReg dst;
        int val;
        public li(IRReg dst, int val) { this.dst = dst; this.val = val; }
        public void toMIPS()
        {
            MIPS.writer.printf("\tli %s, %d\n", dst.toMIPS(), val);
        }
    }

    public static class lw extends IRComm
    {
        IRReg dst;
        IRReg src;
        int offset;
        public lw(IRReg dst, IRReg src, int offset) { this.dst = dst; this.src = src; this.offset = offset; }
        public void toMIPS()
        {
            MIPS.writer.printf("\tlw %s, %d(%s)\n", dst.toMIPS(), offset, src.toMIPS());
        }
    }

    public static class heapAlloc extends IRComm
    {
        IRReg dst;
        IRReg numBytes;
        public heapAlloc(IRReg dst, IRReg numBytes) { this.dst = dst; this.numBytes = numBytes; }
        public void toMIPS()
        {
            MIPS.writer.printf("\tmove $a0, %s  # start of malloc\n", numBytes.toMIPS());
            MIPS.writer.printf("\tsll $a0, $a0, %d\n", MIPS.WORD);
            MIPS.writer.printf("\tli $v0, 9\n");
            MIPS.writer.printf("\tsyscall\n");
            MIPS.writer.printf("\tmove %s, $v0  # end of malloc\n", dst.toMIPS());
        }
    }

    public static class calcOffset extends IRComm
    {
        IRReg dst;
        IRReg src;
        IRReg offset;
        public calcOffset(IRReg dst, IRReg src, IRReg offset) { this.dst = dst; this.src = src; this.offset = offset; }
        public void toMIPS()
        {
            // TODO: boundary-check!
            MIPS.writer.printf("\tsll %s, %s, %d\n", offset.toMIPS(), offset.toMIPS(), MIPS.WORD);
            MIPS.writer.printf("\tadd %s, %s, %s\n", dst.toMIPS(), src.toMIPS(), offset.toMIPS());
        }
    }

    public static class heapGet extends IRComm
    {
        IRReg dst;
        IRReg src;
        IRReg offset;
        public heapGet(IRReg dst, IRReg src, IRReg offset) { this.dst = dst; this.src = src; this.offset = offset; }
        public void toMIPS()
        {
            // TODO: boundary-check!
            MIPS.writer.printf("\tsll %s, %s, %d\n", offset.toMIPS(), offset.toMIPS(), MIPS.WORD);
            MIPS.writer.printf("\tadd %s, %s, %s\n", src.toMIPS(), src.toMIPS(), offset.toMIPS());
            MIPS.writer.printf("\tlw %s, (%s)\n", dst.toMIPS(), src.toMIPS());
        }
    }

    public static class stringLiteral extends IRComm
    {
        IRReg dst;
        String str;
        public stringLiteral(IRReg dst, String str) { this.dst = dst; this.str = str; }
        public void toMIPS()
        {
            String label = IR.uniqueLabel("string_literal");
            MIPS.dataWriter.printf("%s: .asciiz %s\n", label, str);
            MIPS.writer.printf("\tla %s, %s\n", dst.toMIPS(), label);
        }
    }

    public static class printInt extends IRComm
    {
        IRReg value;
        public printInt(IRReg value) { this.value = value; }
        public void toMIPS()
        {
            // // print_int
            MIPS.writer.printf("\tmove $a0, %s  # start of print_int\n", value.toMIPS());
            MIPS.writer.printf("\tli $v0, 1\n");
            MIPS.writer.printf("\tsyscall\n");
            // // print_char (whitespace)
            MIPS.writer.printf("\tli $a0, 32\n");
            MIPS.writer.printf("\tli $v0, 11\n");
            MIPS.writer.printf("\tsyscall  # end of print_int\n");
        }
    }

    public static class printString extends IRComm
    {
        IRReg value;
        public printString(IRReg value) { this.value = value; }
        public void toMIPS()
        {
            MIPS.writer.printf("\tmove $a0, %s  # start of print_string\n", value.toMIPS());
            MIPS.writer.printf("\tli $v0, 4\n");
            MIPS.writer.printf("\tsyscall  # end of print_string\n");
        }
    }

    public static class intBinOp extends IRComm
    {
        IRReg dst;
        IRReg left;
        IRReg right;
        char op;
        public intBinOp(IRReg dst, IRReg left, IRReg right, char op) { this.dst = dst; this.left = left; this.right = right; this.op = op; }
        public void toMIPS()
        {
            switch (op)
            {
            case '+':
                MIPS.writer.printf("\tadd %s, %s, %s\n", dst.toMIPS(), left.toMIPS(), right.toMIPS());
                break;
            case '*':
                MIPS.writer.printf("\tmul %s, %s, %s\n", dst.toMIPS(), left.toMIPS(), right.toMIPS());
                break;
            case '<':
                String ltEndLabel = IR.uniqueLabel("ltEnd");
                MIPS.writer.printf("\tli %s, 1\n", dst.toMIPS());  // be positive - assume equality
                MIPS.writer.printf("\tblt %s, %s, %s\n", left.toMIPS(), right.toMIPS(), ltEndLabel);
                MIPS.writer.printf("\tli %s, 0\n", dst.toMIPS());  // guess not
                MIPS.writer.printf("%s:\n", ltEndLabel);
                break;
            case '=':
                String eqEndLabel = IR.uniqueLabel("eqEnd");
                MIPS.writer.printf("\tli %s, 1\n", dst.toMIPS());  // be positive - assume equality
                MIPS.writer.printf("\tbeq %s, %s, %s\n", left.toMIPS(), right.toMIPS(), eqEndLabel);
                MIPS.writer.printf("\tli %s, 0\n", dst.toMIPS());  // guess not
                MIPS.writer.printf("%s:\n", eqEndLabel);
                break;
            }
        }
    }

    public static class beqz extends IRComm
    {
        IRReg value;
        String label;
        public beqz(IRReg value, String label) { this.value = value; this.label = label; }
        public void toMIPS()
        {
            MIPS.writer.printf("\tbeqz %s, %s\n", value.toMIPS(), label);
        }
    }

    public static class declareGlobal extends IRComm
    {
        String name;
        IRReg value;
        public declareGlobal(String name, IRReg value) { this.name = name; this.value = value; }
        public void toMIPS()
        {
            MIPS.dataWriter.printf("global_%s:\t.word 0\n", name);
            MIPS.writer.printf("\tsw %s, global_%s\n", value.toMIPS(), name);
        }
    }

    public static class getGlobal extends IRComm
    {
        String name;
        IRReg dst;
        public getGlobal(String name, IRReg dst) { this.name = name; this.dst = dst; }
        public void toMIPS()
        {
            MIPS.writer.printf("\tla %s, global_%s\n", dst.toMIPS(), name);
        }
    }

}
