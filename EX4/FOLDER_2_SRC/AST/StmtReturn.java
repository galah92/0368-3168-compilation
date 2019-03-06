package AST;

import pcomp.*;


public class StmtReturn extends Stmt
{

    public Exp exp;
    String funcName;

    public StmtReturn(Exp exp)
    {
        this.exp = exp;
    }

    @Override
    public void logGraphviz()
    {
        if (exp != null) exp.logGraphviz();
        logNode("StmtReturn");
        if (exp != null) logEdge(exp);
    }

    @Override
    public Type Semant() throws Exception
    {
        TypeFunc funcType = SymbolTable.findFunc();
        if (funcType == null) { throw new SemanticException(); }
        funcName = SymbolTable.findName(funcType);
        Type expType = exp != null ? exp.Semant() : Type.VOID;
        if (funcType.retType != expType) {
            if (!(expType == Type.NIL) || 
                (!(funcType.retType instanceof TypeArray) &&
                !(funcType.retType instanceof TypeClass))) {
                throw new SemanticException(funcType.retType + ", " + expType); 
            }
        }
        return expType;
    }

    @Override
    public IRReg toIR()
    {
        IR.add(new IR.setRetVal(exp == null ? IRReg.zero : exp.toIR()));
        IR.add(new IR.jump(funcName + "_end"));
        return null;
    }

}
