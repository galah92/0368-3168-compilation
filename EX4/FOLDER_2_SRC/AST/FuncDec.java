package AST;

import pcomp.*;


import java.util.*;


public class FuncDec extends ClassField
{
    public String retTypeName;
    public String funcName;
    public ParamsList params;
    public StmtList body;
    public ArrayList<Stmt> body2 = new ArrayList<Stmt>();
    public TypeFunc funcType;
    
    public FuncDec(String retTypeName, String funcName, ParamsList params, StmtList body)
    {
        this.retTypeName = retTypeName;
        this.funcName = funcName;
        this.params = params;
        this.body = body;
        for (StmtList it = body; it != null; it = it.next) { body2.add(it.value); }
    }

    public void logGraphviz()
    {
        if (params != null) params.logGraphviz();
        // if (body != null) body.logGraphviz();
        for (Stmt stmt : body2) { stmt.logGraphviz(); }

        logNode(String.format("FuncDec\n%s\n%s", retTypeName, funcName));
        if (params != null) logEdge(params);
        // if (body != null) logEdge(body);
        logEdge(body2.get(0));
        for (int i = 0; i < body2.size() - 1; i++)
        {
            body2.get(i).logEdge(body2.get(i + 1));
        }
    }

    public TypeFunc SemantDeclaration() throws Exception
    {
        if (funcName.equals(Type.VOID.name)) { throw new SemanticException("invalid function name: " + funcName); }
        Type retType = retTypeName.equals(Type.VOID.name) ? Type.VOID : SymbolTable.find(retTypeName);
        TypeList paramsTypes = params != null ? params.SemantDeclaration(): null;
        Type func = SymbolTable.findInScope(funcName);
        if (func == null)
        {
            func = SymbolTable.find(funcName);
            if (func != null && (func instanceof TypeClass)) { throw new SemanticException(); }
        } else {
            if (!(func instanceof TypeFunc)) { throw new SemanticException(); }
            TypeClass currentClass = SymbolTable.findClass();
            if (currentClass == null) { throw new SemanticException("function already declared: " + funcName); }
            TypeClass funcClass = ((TypeFunc)func).cls;
            if (currentClass == funcClass) { throw new SemanticException(); } // func declerated before
            OverrideFuncDecCheck(func, retType, paramsTypes);
        }
        funcType = new TypeFunc(retType, funcName, paramsTypes, SymbolTable.findClass());
        SymbolTable.enter(funcName, funcType);
        return funcType;
    }

    public void SemantBody() throws Exception
    {
        SymbolTable.beginScope(Type.Scope.FUNC);
        SymbolTable.enter(funcName, funcType);
        if (params != null) params.SemantBody();
        for (Stmt stmt : body2) { stmt.Semant(); }
        SymbolTable.endScope();
    }

    public TypeFunc Semant() throws Exception
    {
        funcType = SemantDeclaration();
        for (Stmt stmt : body2) { stmt.Semant(); }
        return funcType;
    }

    public void OverrideFuncDecCheck(Type func, Type retType, TypeList argsTypes) throws Exception
    {
        TypeFunc overloadedFuncType = (TypeFunc)func;
        if (overloadedFuncType.retType != retType) { throw new SemanticException(); }
        if (!(overloadedFuncType.isValidArgs(argsTypes))) { throw new SemanticException(); }
    }

    @Override
    public TempReg toIR()
    {
        IR.add(new IRComm_Label(funcName));
        IR.add(new IRComm_FuncPrologue(funcType.numLocals));
        StmtList it = body;
        for (Stmt stmt : body2) { stmt.toIR(); }
        IR.add(new IRComm_FuncEpilogue(funcType.numLocals));
        return null;
    }

}
