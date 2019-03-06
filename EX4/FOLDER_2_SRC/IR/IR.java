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
                MIPS.writer.printf("\tsw $t%d, %d($sp)\n", src.id, offset * MIPS.WORD);
            }
        }

        public static class get extends IRComm
        {
            IRReg dst;
            int offset;
            public get(IRReg dst, int offset) { this.dst = dst; this.offset = offset; }
            public void toMIPS()
            {
                MIPS.writer.printf("\tlw $t%d, %d($sp)\n", dst.id, offset * MIPS.WORD);
            }
        }

    }

    public static class dereference extends IRComm
    {
        IRReg src;
        public dereference(IRReg src) { this.src = src; }
        public void toMIPS()
        {
            MIPS.writer.printf("\tlw $t%d, ($t%d)\n", src.id, src.id);
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
            MIPS.writer.printf("\tsw	$fp, 0($sp)\n");
            MIPS.writer.printf("\tsw	$ra, 4($sp)\n");
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
            MIPS.writer.printf("\tsw $t%d, %d($fp)\n", src.id, offset * MIPS.WORD);
        }
    }

    public static class frameGet extends IRComm
    {
        IRReg dst;
        int offset;
        public frameGet(IRReg dst, int offset) { this.dst = dst; this.offset = offset; }
        public void toMIPS()
        {
            MIPS.writer.printf("\tlw $t%d, %d($fp)\n", dst.id, offset * MIPS.WORD);
        }
    }

    public static class frameGetOffset extends IRComm
    {
        IRReg dst;
        int offset;
        public frameGetOffset(IRReg dst, int offset) { this.dst = dst; this.offset = offset; }
        public void toMIPS()
        {
            MIPS.writer.printf("\tadd $t%d, $fp, %d\n", dst.id, offset * MIPS.WORD);
        }
    }

    public static class sw extends IRComm
    {
        IRReg dst;
        IRReg src;
        public sw(IRReg dst, IRReg src) { this.dst = dst; this.src = src; }
        public void toMIPS()
        {
            MIPS.writer.printf("\tsw $t%d, ($t%d)\n", src.id, dst.id);
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
            MIPS.writer.println();
            MIPS.writer.printf("%s:\n", label);
        }
    }

    public static class setRetVal extends IRComm
    {
        IRReg src;
        public setRetVal(IRReg src) { this.src = src; }
        public void toMIPS()
        {
            MIPS.writer.printf("\tmove $v0, $t%d\n", src.id);
        }
    }

    public static class getRetVal extends IRComm
    {
        IRReg dst;
        public getRetVal(IRReg dst) { this.dst = dst; }
        public void toMIPS()
        {
            MIPS.writer.printf("\tmove $t%d, $v0\n", dst.id);
        }
    }

    public static class move extends IRComm
    {
        IRReg dst;
        IRReg src;
        public move(IRReg dst, IRReg src) { this.dst = dst; this.src = src; }
        public void toMIPS()
        {
            MIPS.writer.printf("\tmove $t%d, $t%d\n", dst.id, src.id);
        }
    }

    public static class push extends IRComm
    {
        IRReg src;
        public push(IRReg src) { this.src = src; }
        public void toMIPS()
        {
            MIPS.writer.printf("\taddi $sp, $sp, -4\n");
            MIPS.writer.printf("\tsw $t%d, ($sp)\n", src.id);
        }
    }

    public static class pop extends IRComm
    {
        IRReg dst;
        public pop(IRReg dst) { this.dst = dst; }
        public void toMIPS()
        {
            MIPS.writer.printf("\tlw $t%d, ($sp)\n", dst.id);
            MIPS.writer.printf("\taddi $sp, $sp, 4\n");
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
            MIPS.writer.printf("\tli $t%d, %d\n", dst.id, val);
        }
    }

    public static class lw extends IRComm
    {
        IRReg dst;
        int val;
        public lw(IRReg dst, int val) { this.dst = dst; this.val = val; }
        public void toMIPS()
        {
            MIPS.writer.printf("\tlw $t%d, %d\n", dst.id, val);
        }
    }

    public static class heapAlloc extends IRComm
    {
        IRReg dst;
        IRReg numBytes;
        public heapAlloc(IRReg dst, IRReg numBytes) { this.dst = dst; this.numBytes = numBytes; }
        public void toMIPS()
        {
            MIPS.writer.printf("\t# start of malloc\n");
            MIPS.writer.printf("\tmove $a0, $t%d\n", numBytes.id);
            MIPS.writer.printf("\tsll $a0, $a0, %d\n", MIPS.WORD);
            MIPS.writer.printf("\tli $v0, 9\n");
            MIPS.writer.printf("\tsyscall\n");
            MIPS.writer.printf("\tmove $t%d, $v0\n", dst.id);
            MIPS.writer.printf("\t# end of malloc\n");
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
            MIPS.writer.printf("\tsll $t%d, $t%d, %d\n", offset.id, offset.id, MIPS.WORD);
            MIPS.writer.printf("\tadd $t%d, $t%d, $t%d\n", dst.id, src.id, offset.id);
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
            MIPS.writer.printf("\tsll $t%d, $t%d, %d\n", offset.id, offset.id, MIPS.WORD);
            MIPS.writer.printf("\tadd $t%d, $t%d, $t%d\n", src.id, src.id, offset.id);
            MIPS.writer.printf("\tlw $t%d, ($t%d)\n", dst.id, src.id);
        }
    }

    public static class heapSet extends IRComm
    {
        IRReg dst;
        IRReg src;
        public heapSet(IRReg dst, IRReg src) { this.dst = dst; this.src = src; }
        public void toMIPS()
        {
            // TODO: boundary-check!
            MIPS.writer.printf("\tsw $t%d, ($t%d)\n", src.id, dst.id);
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
            MIPS.writer.printf("\tla $t%d, %s\n", dst.id, label);
        }
    }

    public static class printInt extends IRComm
    {
        IRReg value;
        public printInt(IRReg value) { this.value = value; }
        public void toMIPS()
        {
            // // print_int
            MIPS.writer.printf("\tmove $a0, $t%d  # start of print_int\n", value.id);
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
            MIPS.writer.printf("\tmove $a0, $t%d  # start of print_string\n", value.id);
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
                MIPS.writer.printf("\tadd $t%d, $t%d, $t%d\n", dst.id, left.id, right.id);
                break;
            case '*':
                MIPS.writer.printf("\tmul $t%d, $t%d, $t%d\n", dst.id, left.id, right.id);
                break;
            case '<':
                String ltEndLabel = IR.uniqueLabel("ltEnd");
                MIPS.writer.printf("\tli $t%d, 1\n", dst.id);  // be positive - assume equality
                MIPS.writer.printf("\tblt $t%d, $t%d, %s\n", left.id, right.id, ltEndLabel);
                MIPS.writer.printf("\tli $t%d, 0\n", dst.id);  // guess not
                MIPS.writer.printf("%s:\n", ltEndLabel);
                break;
            case '=':
                String eqEndLabel = IR.uniqueLabel("eqEnd");
                MIPS.writer.printf("\tli $t%d, 1\n", dst.id);  // be positive - assume equality
                MIPS.writer.printf("\tbeq $t%d, $t%d, %s\n", left.id, right.id, eqEndLabel);
                MIPS.writer.printf("\tli $t%d, 0\n", dst.id);  // guess not
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
            MIPS.writer.printf("\tbeqz $t%d, %s\n", value.id, label);
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
            MIPS.writer.printf("\tsw $t%d, global_%s\n", value.id, name);
        }
    }

    public static class getGlobal extends IRComm
    {
        String name;
        IRReg dst;
        public getGlobal(String name, IRReg dst) { this.name = name; this.dst = dst; }
        public void toMIPS()
        {
            MIPS.writer.printf("\tla $t%d, global_%s\n", dst.id, name);
        }
    }

}
