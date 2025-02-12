package AST;

import pcomp.*;


public class VarDec extends ClassField
{
    
    public String varTypeName;
    public String varName;
    public Exp initVal;
    public int numLocal = -1;
    public int numMember = -1;

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
        Type initValType = initVal == null ? Type.NIL : initVal.Semant();

        if (SymbolTable.isScope(Type.Scope.FUNC.name))  // local variable
        {
            TypeFunc funcType = SymbolTable.findFunc();
            numLocal = funcType.locals.size();
            funcType.locals.add(new Symbol(varName, varType));
            funcType.currMaxLocals++;
        }
        else if (SymbolTable.isScope(Type.Scope.CLASS.name))  // class member
        {
            numMember = classType.members.size();
            classType.members.add(new Symbol(varName, varType));

            if (initValType == Type.INT && varType == Type.INT)
            {
                classType.initVals.add(((ExpInt)initVal).value);
            }
            else if (initValType == Type.STRING && varType == Type.STRING)
            {
                classType.initVals.add(((ExpString)initVal).label);
            }
            else if (initValType == Type.NIL)
            {
                classType.initVals.add(0);
            }
            else
            {
                throw new SemanticException("invalid initialization type for class member");
            }
        }

        initValType = initVal != null ? initValType : null;

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
                if (((TypeArray)varType).elementType != initValType && varType != initValType) { throw new SemanticException(varType + ", " + initValType); }
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
    public IRReg toIR()
    {
        if (numLocal != -1)  // local variable
        {
            IRReg valReg = initVal == null ? IRReg.zero : initVal.toIR();
            IR.add(new IR.sw(valReg, IRReg.fp, (-numLocal - 9) * 4));
        }
        else if (numMember != -1)
        {
            String initLabel = IR.uniqueLabel("init_" + varName);
            IR.globalVars.add(initLabel);
            IR.add(new IR.label(initLabel));
            if (initVal != null) { initVal.toIR(); }
            IR.add(new IR.jr(IRReg.ra));
        }
        else  // global variable
        {
            String initLabel = IR.uniqueLabel("init_" + varName);
            IR.globalVars.add(initLabel);
            IR.add(new IR.label(initLabel));
            IRReg valReg = initVal == null ? IRReg.zero : initVal.toIR();
            IR.add(new IR.declareGlobal(varName, valReg));
            IR.add(new IR.jr(IRReg.ra));
        }
        return null;
    }

}
