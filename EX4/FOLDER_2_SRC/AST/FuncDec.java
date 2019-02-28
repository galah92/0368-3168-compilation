package AST;

import pcomp.*;
import java.util.*;


public class FuncDec extends ClassField
{
    public String retTypeName;
    public String funcName;
    public ParamsList params;
    public StmtList body;
    public TypeFunc funcType;
    
    public FuncDec(String retTypeName, String funcName, ParamsList params, StmtList body)
    {
        this.retTypeName = retTypeName;
        this.funcName = funcName;
        this.params = params;
        this.body = body;
    }

    @Override
    public void logGraphviz()
    {
        if (params != null) params.logGraphviz();
        if (body != null) body.logGraphviz();
        logNode(String.format("FuncDec\n%s\n%s", retTypeName, funcName));
        if (params != null) logEdge(params);
        if (body != null) logEdge(body);
    }

    @Override
    public TypeFunc SemantDeclaration() throws Exception
    {
        if (funcName.equals(Type.VOID.name)) { throw new SemanticException("invalid function name: " + funcName); }
        Type retType = retTypeName.equals(Type.VOID.name) ? Type.VOID : SymbolTable.find(retTypeName);

        TypeClass classType = SymbolTable.findClass();
        if (classType != null)
        {
            if (classType.getFuncField(funcName, false) != null) { throw new SemanticException("function already declared in current class: " + funcName); }
            TypeFunc overriddenFunc = classType.getFuncField(funcName);
            if (overriddenFunc != null)
            {
                if (retType != overriddenFunc.retType) { throw new SemanticException("overridding method with different return type: " + funcName); }
                // TODO: should also check args equality
            }
        }

        Type func = SymbolTable.findInScope(funcName);
        if (func == null)
        {
            func = SymbolTable.find(funcName);
            if (func != null && (func instanceof TypeClass)) { throw new SemanticException(); }
        }
        else
        {
            if (!(func instanceof TypeFunc)) { throw new SemanticException(); }
            if (classType == null) { throw new SemanticException("function already declared: " + funcName); }
        }

        funcType = new TypeFunc(retType);
        if (classType != null) { classType.methods.add(new Symbol(funcName, funcType)); }
        SymbolTable.enter(funcName, funcType);
        if (params != null) params.SemantDeclaration();
        return funcType;
    }

    @Override
    public void SemantBody() throws Exception
    {
        SymbolTable.beginScope(Type.Scope.FUNC);
        SymbolTable.enter(funcName, funcType);
        if (params != null) params.SemantBody();
        if (body != null) { body.Semant();  }
        SymbolTable.endScope();
    }

    @Override
    public TypeFunc Semant() throws Exception
    {
        funcType = SemantDeclaration();
        SemantBody();
        return funcType;
    }

    @Override
    public TempReg toIR()
    {
        int numLocals = funcType.locals.size();
        IR.add(new IRComm_Label(funcName));
        IR.add(new IRComm_FuncPrologue(numLocals));
        if (body != null) body.toIR();
        IR.add(new IRComm_FuncEpilogue(numLocals));
        return null;
    }

}
