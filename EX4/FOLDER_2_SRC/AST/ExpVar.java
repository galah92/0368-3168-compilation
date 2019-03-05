package AST;

import pcomp.*;


public class ExpVar extends Exp
{

    private Var var;
    
    public ExpVar(Var var)
    {
        this.var = var;
    }

    @Override
    public void logGraphviz()
    {
        var.logGraphviz();
        logNode(String.format("ExpVar"));
        logEdge(var);
    }

    @Override
    public Type Semant() throws Exception
    {
        return var.Semant();
    }

    @Override
    public IRReg toIR()
    {
        IRReg reg = var.toIR();
        IR.add(new IR.dereference(reg));
        return reg;
    }

}
