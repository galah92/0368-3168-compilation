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
        String endIfLabel = IRComm.getLabel("endIf");
        TempReg condTemp = cond.toIR();
        IR.add(new IR.beqz(condTemp, endIfLabel));
        body.toIR();
        IR.add(new IR.label(endIfLabel));
        return null;
    }

}
