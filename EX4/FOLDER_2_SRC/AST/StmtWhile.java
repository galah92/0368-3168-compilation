package AST;

import pcomp.*;
import IR.*;


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
    public TempReg toIR()
    {
        String whileCondLabel = IRcommand.getLabel("whileCond");
        String endWhileLabel = IRcommand.getLabel("endWhile");
        IR.add(new IRcommand_Label(whileCondLabel));
        TempReg condTemp = cond.toIR();
        IR.add(new IRcommand_Jump_If_Eq_To_Zero(condTemp, endWhileLabel));
        body.toIR();
        IR.add(new IRcommand_Jump_Label(whileCondLabel));		
        IR.add(new IRcommand_Label(endWhileLabel));
        return null;
    }

}
