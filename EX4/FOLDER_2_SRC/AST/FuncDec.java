package AST;
import TYPES.*;
import pcomp.*;
import IR.*;

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

    public TempReg toIR()
    {
        IR.add(new IRcommand_Label(funcName));
        int numLocals = funcType.params2.size();
        IR.add(new IRcommand_FuncPrologue(numLocals));
        if (body != null) body.toIR();
        IR.add(new IRcommand_FuncEpilogue(numLocals));
        return null;
    }

}
