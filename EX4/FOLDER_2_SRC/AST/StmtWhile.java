package AST;

import pcomp.*;



public class StmtWhile extends Stmt
{
    
    public Exp cond;
    public StmtList body;

    public StmtWhile(Exp cond, StmtList body)
    {
        this.cond = cond;
        this.body = body;
    }

    public void logGraphviz()
    {
        if (cond != null) cond.logGraphviz();
        if (body != null) body.logGraphviz();
        logNode("StmtWhile");
        if (cond != null) logEdge(cond);
        if (body != null) logEdge(body);
    }

    public Type Semant() throws Exception
    {
        if (cond.Semant() != Type.INT) { throw new SemanticException(); }
        SymbolTable.beginScope(Type.Scope.WHILE);
        body.Semant();
        SymbolTable.endScope();
        return null;
    }

    @Override
    public IRReg toIR()
    {
        String whileCondLabel = IR.uniqueLabel("while_cond");
        String whileEndLabel = IR.uniqueLabel("while_end");
        IR.add(new IR.label(whileCondLabel));
        IR.add(new IR.beqz(cond.toIR(), whileEndLabel));
        body.toIR();
        IR.add(new IR.jump(whileCondLabel));
        IR.add(new IR.label(whileEndLabel));
        return null;
    }

}
