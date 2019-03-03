package AST;

import pcomp.*;


public class VarArrayElement extends Var
{

    public Var var;
    public Exp index;

    public VarArrayElement(Var var, Exp index)
    {
        this.var = var;
        this.index = index;
    }

    @Override
    public void logGraphviz()
    {
        var.logGraphviz();
        index.logGraphviz();
        logNode(String.format("VarArrayElement\n"));
        logEdge(var);
        logEdge(index);
    }

    @Override
    public Type Semant() throws Exception
    {
        if (index.Semant() != Type.INT) { throw new SemanticException("array index is not of type int"); }
        Type arrType = var.Semant();
        if (!(arrType instanceof TypeArray)) { throw new SemanticException("symbol is not of type array"); }
        numLocal = var.numLocal;
        numParam = var.numParam;
        return ((TypeArray)arrType).elementType;
    }

    @Override
    public TempReg toIR()
    {
        TempReg valReg = new TempReg();
        TempReg baseReg = var.toIR();
        IR.add(new IR.dereference(baseReg));
        IR.add(new IR.calcOffset(valReg, baseReg, index.toIR()));
        return valReg;
    }

}
