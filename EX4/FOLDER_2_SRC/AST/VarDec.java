package AST;

import pcomp.*;


public class VarDec extends ClassField
{
    
    public String varTypeName;
    public String varName;
    public Exp initVal;
    public int numLocal = -1;

    public VarDec(String varTypeName, String varName, Exp initVal)
    {
        this.varTypeName = varTypeName;
        this.varName = varName;
        this.initVal = initVal;
    }

    @Override
    public void logGraphviz()
    {
        if (initVal != null) initVal.logGraphviz();
        logNode(String.format("VarDec\n%s\n%s", varName, varTypeName));
        if (initVal != null) logEdge(initVal);
    }

    @Override
    public Type SemantDeclaration() throws Exception
    {
        if (varName.equals(Type.VOID.name)) { throw new SemanticException("invalid variable name: " + varName); }
        if (SymbolTable.findInScope(varName) != null) { throw new SemanticException("symbol already defined: " + varName); }

        Type varType = SymbolTable.find(varTypeName);
        if (varType == null) { throw new SemanticException("type not defined: " + varTypeName); }

        TypeClass classType = SymbolTable.findClass();

        if (SymbolTable.isScope(Type.Scope.FUNC.name))  // local variable
        {
            TypeFunc funcType = SymbolTable.findFunc();
            numLocal = funcType.locals.size();
            funcType.locals.add(new Symbol(varName, varType));
        }
        else if (SymbolTable.isScope(Type.Scope.CLASS.name))  // class member
        {
            classType.members.add(new Symbol(varName, varType));
        }

        Type initValType = initVal != null ? initVal.Semant() : null;

        if (initValType == Type.NIL)
        {
            if (varType instanceof Type.Primitive) { throw new SemanticException("assign NIL to primitive"); }
            SymbolTable.enter(varName, varType);
            return varType;
        }

        if (initValType != null)
        {
            if (varType instanceof TypeArray)
            {
                if (((TypeArray)varType).elementType != initValType) { throw new SemanticException(varType + ", " + initValType); }
            }
            else if (initValType instanceof TypeClass)
            {
                if (!((TypeClass)initValType).isInheritingFrom((TypeClass)varType)) { throw new SemanticException("type mismatch: " + varName); }
            }
            else
            {
                if (initValType != varType) { throw new SemanticException(); }
            }
        }

        SymbolTable.enter(varName, varType);
        return varType;
    }

    @Override
    public void SemantBody() throws Exception
    {
        // nothing to do here
    }

    @Override
    public Type Semant() throws Exception
    {
        Type varType = SemantDeclaration();
        return varType;
    }

    @Override
    public TempReg toIR()
    {
        TempReg valReg = initVal == null ? TempReg.ZeroReg : initVal.toIR();
        if (numLocal == -1)
        {
            // IR.add(new IRComm_Allocate(varName));
            // if (initVal != null)
            // {
            //     IR.add(new IRComm_Store(varName, initVal.toIR()));
            // }
        }
        else  // local variable?
        {
            IR.add(new IR.Stack.set(valReg, numLocal - 1));
            // IR.add(new IRComm_StoreLocal(initVal == null ? TempReg.ZeroReg : initVal.toIR(), numLocal));
        }
        return null;
    }

}
