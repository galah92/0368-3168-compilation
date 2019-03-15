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
        cond.logGraphviz();
        body.logGraphviz();
        logNode("StmtWhile");
        logEdge(cond);
        logEdge(body);
    }

    public Type Semant() throws Exception
    {
        if (cond.Semant() != Type.INT) { throw new SemanticException(); }

        TypeFunc funcType = SymbolTable.findFunc();
        int y = funcType.currMaxLocals;
        funcType.currMaxLocals = funcType.locals.size();
        
        SymbolTable.beginScope(Type.Scope.WHILE);
        body.Semant();
        SymbolTable.endScope();
        
        funcType.currMaxLocals = y;
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
