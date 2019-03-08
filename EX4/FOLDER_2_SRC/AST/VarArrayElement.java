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
    public IRReg toIR()
    {
        IRReg baseReg = var.toIR();
        IR.add(new IR.lw(baseReg, baseReg, 0));  // dereference array
        IRReg indexReg = index.toIR();  // get index
        
        // boundary check
        IRReg sizeReg = new IRReg.TempReg();
        IR.add(new IR.lw(sizeReg, baseReg, 0));
        String arrAccessEndLabel = IR.uniqueLabel("arr_access_end");
        IR.add(new IR.blt(indexReg, sizeReg, arrAccessEndLabel));
        IR.add(new IR.la(sizeReg, "string_access_violation"));
        IR.add(new IR.printString(sizeReg));
        IR.add(new IR.exit());
        IR.add(new IR.label(arrAccessEndLabel));

        IR.add(new IR.addi(indexReg, indexReg, 1)); // first element is size
        IR.add(new IR.sll(indexReg, indexReg, 4));  // convert to index in bytes
        IR.add(new IR.add(baseReg, baseReg, indexReg));  // calculate address of element
        return baseReg;
    }

}
