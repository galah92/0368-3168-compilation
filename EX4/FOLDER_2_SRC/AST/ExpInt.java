package AST;

import pcomp.*;


public class ExpInt extends Exp
{

    public final int value;
    
    public ExpInt(int value)
    {
        this.value = value;
    }

    @Override
    public void logGraphviz()
    {
        logNode(String.format("ExpInt\n%s", value));
    }

    @Override
    public Type Semant() throws Exception
    {
        return Type.INT;
    }

    @Override
    public IRReg toIR()
    {
        IRReg reg = new IRReg.TempReg();
        IR.add(new IR.li(reg, value));
        return reg;
    }

}
