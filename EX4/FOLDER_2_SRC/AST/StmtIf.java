package AST;

import pcomp.*;



public class StmtIf extends Stmt
{
    public Exp cond;
    public StmtList body;

    public StmtIf(Exp cond, StmtList body)
    {
        this.cond = cond;
        this.body = body;
    }

    public void logGraphviz()
    {
        if (cond != null) cond.logGraphviz();
        if (body != null) body.logGraphviz();

        logNode("StmtIf");

        if (cond != null) logEdge(cond);
        if (body != null) logEdge(body);
    }

    public Type Semant() throws Exception
    {
        if (cond.Semant() != Type.INT) { throw new SemanticException(); }

        SymbolTable.beginScope(Type.Scope.IF);
        body.Semant();
        SymbolTable.endScope();

        return null;
    }

    @Override
    public TempReg toIR()
    {
        String endIfLabel = IRcommand.getLabel("endIf");
        TempReg condTemp = cond.toIR();
        IR.add(new IRcommand_Jump_If_Eq_To_Zero(condTemp, endIfLabel));
        body.toIR();
        IR.add(new IRcommand_Label(endIfLabel));
        return null;
    }

}
