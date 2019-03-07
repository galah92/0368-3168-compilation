package AST;

import pcomp.*;


public class StmtAssign extends Stmt
{

    public Var var;
    public Exp exp;

    public StmtAssign(Var var, Exp exp)
    {
        this.var = var;
        this.exp = exp;
    }

    @Override
    public void logGraphviz()
    {
        if (var != null) var.logGraphviz();
        if (exp != null) exp.logGraphviz();
        logNode("StmtAssign\n");
        logEdge(var);
        logEdge(exp);
    }

    @Override
    public Type Semant() throws Exception
    {
        Type varType = null;
        Type expType = null;
        
        if (var != null) varType = var.Semant();
        if (exp != null) expType = exp.Semant();
        
        if (expType == Type.NIL)
        {
            if (varType == Type.INT || varType == Type.STRING) { throw new SemanticException(); }
            return varType;
        }

        Type integralVarType = (exp instanceof NewExp && varType instanceof TypeArray) ? ((TypeArray)varType).elementType : varType;
        if (expType instanceof TypeClass)
        {
            if (!((TypeClass)expType).isInheritingFrom((TypeClass)integralVarType)) { throw new SemanticException(integralVarType + ", " + expType); }
        }
        else
        {
            if (expType != integralVarType) { throw new SemanticException(integralVarType + ", " + expType); }
        }


        return varType;
    }

    @Override
    public IRReg toIR()
    {
        // IRReg reg = var.toIR();  // eval left side first
        // IR.add(new IR.sw(exp.toIR(), reg, 0));  // eval right side and assign
        // return null;
        IR.add(new IR.move(IRReg.s0, var.toIR()));  // eval left side first and store it
        IR.add(new IR.sw(exp.toIR(), IRReg.s0, 0));  // eval right side and assign
        return IRReg.s0;
    }
    
}
