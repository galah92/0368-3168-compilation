package AST;

import pcomp.*;


public class StmtCall extends Stmt
{
    
    public ExpCall callExp;

    public StmtCall(ExpCall callExp)
    {
        this.callExp = callExp;
    }

    public void logGraphviz()
    {
        callExp.logGraphviz();
        logNode(String.format("StmtCall"));
        logEdge(callExp);
    }

    public Type Semant() throws Exception
    {
        return callExp.Semant();
    }

    public IRReg toIR()
    {
        if (callExp != null) callExp.toIR();
        return null;
    }

}
