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

    public static final Deque<String> globalVars = new ArrayDeque<String>();

    public static class add extends IRComm
    {
        IRReg dst;
        IRReg src1;
        IRReg src2;
        public add(IRReg dst, IRReg src1, IRReg src2) { this.dst = dst; this.src1 = src1; this.src2 = src2; }
        public void toMIPS()
        {
            MIPS.writer.printf("\tadd %s, %s, %s\n", dst.toMIPS(), src1.toMIPS(), src2.toMIPS());
        }
    }

    public static class addi extends IRComm
    {
        IRReg dst;
        IRReg src;
        int imm;
        public addi(IRReg dst, IRReg src, int imm) { this.dst = dst; this.src = src; this.imm = imm; }
        public void toMIPS()
        {
            MIPS.writer.printf("\taddi %s, %s, %d\n", dst.toMIPS(), src.toMIPS(), imm);
        }
    }

    public static class sub extends IRComm
    {
        IRReg dst;
        IRReg src1;
        IRReg src2;
        public sub(IRReg dst, IRReg src1, IRReg src2) { this.dst = dst; this.src1 = src1; this.src2 = src2; }
        public void toMIPS()
        {
            MIPS.writer.printf("\tsub %s, %s, %s\n", dst.toMIPS(), src1.toMIPS(), src2.toMIPS());
        }
    }

    public static class mul extends IRComm
    {
        IRReg dst;
        IRReg src1;
        IRReg src2;
        public mul(IRReg dst, IRReg src1, IRReg src2) { this.dst = dst; this.src1 = src1; this.src2 = src2; }
        public void toMIPS()
        {
            MIPS.writer.printf("\tmul %s, %s, %s\n", dst.toMIPS(), src1.toMIPS(), src2.toMIPS());
        }
    }

    public static class div extends IRComm
    {
        IRReg dst;
        IRReg src1;
        IRReg src2;
        public div(IRReg dst, IRReg src1, IRReg src2) { this.dst = dst; this.src1 = src1; this.src2 = src2; }
        public void toMIPS()
        {
            MIPS.writer.printf("\tdiv %s, %s, %s\n", dst.toMIPS(), src1.toMIPS(), src2.toMIPS());
        }
    }

    public static class blt extends IRComm
    {
        String label;
        IRReg src1;
        IRReg src2;
        public blt(IRReg src1, IRReg src2, String label) { this.label = label; this.src1 = src1; this.src2 = src2; }
        public void toMIPS()
        {
            MIPS.writer.printf("\tblt %s, %s, %s\n", src1.toMIPS(), src2.toMIPS(), label);
        }
    }

    public static class bgt extends IRComm
    {
        String label;
        IRReg src1;
        IRReg src2;
        public bgt(IRReg src1, IRReg src2, String label) { this.label = label; this.src1 = src1; this.src2 = src2; }
        public void toMIPS()
        {
            MIPS.writer.printf("\tbgt %s, %s, %s\n", src1.toMIPS(), src2.toMIPS(), label);
        }
    }

    public static class beq extends IRComm
    {
        String label;
        IRReg src1;
        IRReg src2;
        public beq(IRReg src1, IRReg src2, String label) { this.label = label; this.src1 = src1; this.src2 = src2; }
        public void toMIPS()
        {
            MIPS.writer.printf("\tbeq %s, %s, %s\n", src1.toMIPS(), src2.toMIPS(), label);
        }
    }

    public static class bne extends IRComm
    {
        String label;
        IRReg src1;
        IRReg src2;
        public bne(IRReg src1, IRReg src2, String label) { this.label = label; this.src1 = src1; this.src2 = src2; }
        public void toMIPS()
        {
            MIPS.writer.printf("\tbne %s, %s, %s\n", src1.toMIPS(), src2.toMIPS(), label);
        }
    }

    public static class sbrk extends IRComm
    {
        public void toMIPS()
        {
            MIPS.writer.printf("\tli $v0, 9\n");
            MIPS.writer.printf("\tsyscall\n");
        }
    }

    public static class sll extends IRComm
    {
        IRReg dst;
        IRReg src;
        int imm;
        public sll(IRReg dst, IRReg src, int imm) { this.dst = dst; this.src = src; this.imm = imm; }
        public void toMIPS()
        {
            MIPS.writer.printf("\tsll %s, %s, %d\n", dst.toMIPS(), src.toMIPS(), imm);
        }
    }

    public static class comment extends IRComm
    {
        String text;
        public comment(String text) { this.text = text; }
        public void toMIPS()
        {
            MIPS.writer.printf("\t# %s\n", text);
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

    public static class jal extends IRComm
    {
        String label;
        public jal(String label) { this.label = label; }
        public void toMIPS()
        {
            MIPS.writer.printf("\tjal %s\n", label);
        }
    }

    public static class jr extends IRComm
    {
        IRReg reg;
        public jr(IRReg reg) { this.reg = reg; }
        public void toMIPS()
        {
            MIPS.writer.printf("\tjr %s\n", reg.toMIPS());
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

    public static class la extends IRComm
    {
        IRReg dst;
        String val;
        public la(IRReg dst, String val) { this.dst = dst; this.val = val; }
        public void toMIPS()
        {
            MIPS.writer.printf("\tla %s, %s\n", dst.toMIPS(), val);
        }
    }

    public static class lb extends IRComm
    {
        IRReg dst;
        IRReg src;
        int offset;
        public lb(IRReg dst, IRReg src, int offset) { this.dst = dst; this.src = src; this.offset = offset; }
        public void toMIPS()
        {
            MIPS.writer.printf("\tlb %s, %d(%s)\n", dst.toMIPS(), offset, src.toMIPS());
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

    public static class sb extends IRComm
    {
        IRReg dst;
        IRReg src;
        int offset;
        public sb(IRReg src, IRReg dst, int offset) { this.dst = dst; this.src = src; this.offset = offset; }
        public void toMIPS()
        {
            MIPS.writer.printf("\tsb %s, %d(%s)\n", src.toMIPS(), offset, dst.toMIPS());
        }
    }

    public static class sw extends IRComm
    {
        IRReg dst;
        IRReg src;
        int offset;
        public sw(IRReg src, IRReg dst, int offset) { this.dst = dst; this.src = src; this.offset = offset; }
        public void toMIPS()
        {
            MIPS.writer.printf("\tsw %s, %d(%s)\n", src.toMIPS(), offset, dst.toMIPS());
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
