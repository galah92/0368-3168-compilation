package AST;

import pcomp.*;


public class StmtVarDec extends Stmt
{

    public VarDec var;
    
    public StmtVarDec(VarDec var)
    {
        this.var = var;
    }

    @Override
    public void logGraphviz()
    {
        var.logGraphviz();
        logNode(String.format("StmtVarDec"));
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
        return var.toIR();
    }
    
}
