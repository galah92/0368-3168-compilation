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
        cond.logGraphviz();
        body.logGraphviz();
        logNode("StmtIf");
        logEdge(cond);
        logEdge(body);
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
    public IRReg toIR()
    {
        String endIfLabel = IR.uniqueLabel("if_end");
        IR.add(new IR.beqz(cond.toIR(), endIfLabel));
        body.toIR();
        IR.add(new IR.label(endIfLabel));
        return null;
    }

}
