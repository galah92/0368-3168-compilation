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
    
    public static void toMIPS()
    {
        for (IRComm comm : commands) { comm.toMIPS(); }
    }

    private static int uniqueLabelCounter = 0;

    public static String uniqueLabel(String label)
    {
        return String.format("_%d_%s", uniqueLabelCounter++, label);
    }

    public static final Deque<String> globalVars = new ArrayDeque<String>();

    public static void init()
    {
        MIPS.writer.println(".text");
        MIPS.writer.println(".globl main");
        MIPS.writer.println("main:");
        // TODO: find a way to init globals here instead of _main
        MIPS.writer.println("\t j _main");
        
        IR.add(new IR.label("store_tmp_regs"));
        IR.add(new IR.sw(IRReg.t0, IRReg.fp, -1 * 4));  // save t0
        IR.add(new IR.sw(IRReg.t1, IRReg.fp, -2 * 4));  // save t1
        IR.add(new IR.sw(IRReg.t2, IRReg.fp, -3 * 4));  // save t2
        IR.add(new IR.sw(IRReg.t3, IRReg.fp, -4 * 4));  // save t3
        IR.add(new IR.sw(IRReg.t4, IRReg.fp, -5 * 4));  // save t4
        IR.add(new IR.sw(IRReg.t5, IRReg.fp, -6 * 4));  // save t5
        IR.add(new IR.sw(IRReg.t6, IRReg.fp, -7 * 4));  // save t6
        IR.add(new IR.sw(IRReg.t7, IRReg.fp, -8 * 4));  // save t7
        IR.add(new IR.jr(IRReg.ra));

        IR.add(new IR.label("retrieve_tmp_regs"));
        IR.add(new IR.lw(IRReg.t0, IRReg.fp, -1 * 4));  // retrieve t0
        IR.add(new IR.lw(IRReg.t1, IRReg.fp, -2 * 4));  // retrieve t1
        IR.add(new IR.lw(IRReg.t2, IRReg.fp, -3 * 4));  // retrieve t2
        IR.add(new IR.lw(IRReg.t3, IRReg.fp, -4 * 4));  // retrieve t3
        IR.add(new IR.lw(IRReg.t4, IRReg.fp, -5 * 4));  // retrieve t4
        IR.add(new IR.lw(IRReg.t5, IRReg.fp, -6 * 4));  // retrieve t5
        IR.add(new IR.lw(IRReg.t6, IRReg.fp, -7 * 4));  // retrieve t6
        IR.add(new IR.lw(IRReg.t7, IRReg.fp, -8 * 4));  // retrieve t7
        IR.add(new IR.jr(IRReg.ra));

        // exit function for invalid dereference
        IR.add(new IR.label("exit_invalid_dereference"));
        IR.add(new IR.la(IRReg.a0, "string_invalid_ptr_dref"));
        IR.add(new IR.printString(IRReg.a0));
        IR.add(new IR.exit());
        
        // exit function for invalid array index
        IR.add(new IR.label("exit_access_violation"));
        IR.add(new IR.la(IRReg.a0, "string_access_violation"));
        IR.add(new IR.printString(IRReg.a0));
        IR.add(new IR.exit());

        // exit function for division by zero
        IR.add(new IR.label("exit_division_by_zero"));
        IR.add(new IR.la(IRReg.a0, "string_illegal_div_by_0"));
        IR.add(new IR.printString(IRReg.a0));
        IR.add(new IR.exit());

        // strings for runtime checks
        MIPS.dataWriter.println(".data");
        MIPS.dataWriter.println("string_access_violation: .asciiz \"Access Violation\"");
        MIPS.dataWriter.println("string_illegal_div_by_0: .asciiz \"Division By Zero\"");
        MIPS.dataWriter.println("string_invalid_ptr_dref: .asciiz \"Invalid Pointer Dereference\"");
    }

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
    
    public static class slt extends IRComm
    {
        IRReg dst;
        IRReg src1;
        IRReg src2;
        public slt(IRReg dst, IRReg src1, IRReg src2) { this.dst = dst; this.src1 = src1; this.src2 = src2; }
        public void toMIPS()
        {
            MIPS.writer.printf("\tslt %s, %s, %s\n", dst.toMIPS(), src1.toMIPS(), src2.toMIPS());
        }
    }

    public static class sltu extends IRComm
    {
        IRReg dst;
        IRReg src1;
        IRReg src2;
        public sltu(IRReg dst, IRReg src1, IRReg src2) { this.dst = dst; this.src1 = src1; this.src2 = src2; }
        public void toMIPS()
        {
            MIPS.writer.printf("\tsltu %s, %s, %s\n", dst.toMIPS(), src1.toMIPS(), src2.toMIPS());
        }
    }

    public static class xori extends IRComm
    {
        IRReg dst;
        IRReg src;
        int imm;
        public xori(IRReg dst, IRReg src, int imm) { this.dst = dst; this.src = src; this.imm = imm; }
        public void toMIPS()
        {
            MIPS.writer.printf("\txori %s, %s, %d\n", dst.toMIPS(), src.toMIPS(), imm);
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

    public static class exit extends IRComm
    {
        public void toMIPS()
        {
            // print newline
            MIPS.writer.printf("\tli $a0, 0xA\n");  // 0xA is newline char
            MIPS.writer.printf("\tli $v0, 11\n");
            MIPS.writer.printf("\tsyscall\n");
            // now really exit
            MIPS.writer.printf("\tli $v0, 10\n");
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

    public static class jalr extends IRComm
    {
        IRReg reg;
        public jalr(IRReg reg) { this.reg = reg; }
        public void toMIPS()
        {
            MIPS.writer.printf("\tjalr %s\n", reg.toMIPS());
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
        String str;
        String label;
        public stringLiteral(String str, String label) { this.label = label; this.str = str; }
        public void toMIPS()
        {
            MIPS.dataWriter.printf("%s: .asciiz %s\n", label, str);
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

    public static class declareData extends IRComm
    {
        String name;
        int numBytes;
        public declareData(String name, int numBytes) { this.name = name; this.numBytes = numBytes; }
        public void toMIPS()
        {
            MIPS.dataWordsWriter.printf("%s:\n\t.align 4\n\t.word %d\n", name, numBytes);
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
