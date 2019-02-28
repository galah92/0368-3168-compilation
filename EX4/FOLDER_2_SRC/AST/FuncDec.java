package AST;

import pcomp.*;
import java.util.*;


public class FuncDec extends ClassField
{
    public String retTypeName;
    public String funcName;
    public ParamsList params;
    public StmtList body;
    public Deque<Stmt> body2 = new ArrayDeque<Stmt>();
    public TypeFunc funcType;
    
    public FuncDec(String retTypeName, String funcName, ParamsList params, StmtList body)
    {
        this.retTypeName = retTypeName;
        this.funcName = funcName;
        this.params = params;
        this.body = body;
        for (StmtList it = body; it != null; it = it.tail) { body2.add(it.head); }
    }

    public void logGraphviz()
    {
        if (params != null) params.logGraphviz();
        if (body != null) body.logGraphviz();
        logNode(String.format("FuncDec\n%s\n%s", retTypeName, funcName));
        if (params != null) logEdge(params);
        if (body != null) logEdge(body);
    }

    public TypeFunc SemantDeclaration() throws Exception
    {
        if (funcName.equals(Type.VOID.name)) { throw new SemanticException("invalid function name: " + funcName); }
        Type retType = retTypeName.equals(Type.VOID.name) ? Type.VOID : SymbolTable.find(retTypeName);
        TypeList paramsTypes = params != null ? params.SemantDeclaration(): null;

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

        funcType = new TypeFunc(retType, paramsTypes);

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

        if (classType != null) { classType.methods.add(new Symbol(funcName, funcType)); }
        
        SymbolTable.enter(funcName, funcType);
        return funcType;
    }

    public void SemantBody() throws Exception
    {
        SymbolTable.beginScope(Type.Scope.FUNC);
        SymbolTable.enter(funcName, funcType);
        if (params != null) params.SemantBody();
        if (body != null) { body.Semant();  }
        SymbolTable.endScope();
    }

    public TypeFunc Semant() throws Exception
    {
        funcType = SemantDeclaration();
        SemantBody();
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
        if (body != null) body.toIR();
        IR.add(new IRComm_FuncEpilogue(funcType.numLocals));
        return null;
    }

}
