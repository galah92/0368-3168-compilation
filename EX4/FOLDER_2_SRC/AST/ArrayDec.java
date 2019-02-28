package AST;

import pcomp.*;


public class ArrayDec extends Dec
{
    public String arrName;
    public String arrTypeName;

    public ArrayDec(String arrName, String arrTypeName)
    {
        this.arrName = arrName;
        this.arrTypeName = arrTypeName;
    }

    public void logGraphviz()
    {
        logNode(String.format("ArrayDec\n%s\n%s", arrName, arrTypeName));
    }

    public Type Semant() throws Exception
    {
        if (!SymbolTable.isGlobalScope()) { throw new SemanticException(); }
        if (SymbolTable.find(arrName) != null) { throw new SemanticException(); }
        Type arrType = SymbolTable.find(arrTypeName);
        if (arrType == null) { throw new SemanticException("type not defined: " + arrType); }
        SymbolTable.enter(arrName, new TypeArray(arrType));
        return null;
    }

    @Override
    public TempReg toIR()
    {
        // TODO: implement
        return null;
    }

}
