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
    public String className;
    
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
        
        TypeClass classType = SymbolTable.isScope(Type.Scope.CLASS.name) ? SymbolTable.findClass() : null;
        if (classType != null)
        {
            TypeFunc overriddenFunc = classType.getMethod(funcName);
            if (overriddenFunc != null)
            {
                if (overriddenFunc.className.equals(className)) { throw new SemanticException("function already declared in current class: " + funcName); }
                if (overriddenFunc.retType != retType) { throw new SemanticException("overridding method with different return type: " + funcName); }
                // TODO: should also check args equality
            }
            className = classType.className;
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

        funcType = new TypeFunc(retType, className == null ? funcName : className + "_" + funcName);
        funcType.funcName = funcName;
        if (className != null) { funcType.className = className; }
        if (classType != null)
        {
            boolean isFound = false;
            for (Symbol symbol : classType.methods)
            {
                if (funcName.equals(symbol.name))
                {
                    symbol.type = funcType;
                    isFound = true;
                }
            }
            if (!isFound) { classType.methods.add(new Symbol(funcName, funcType)); }
        }
        SymbolTable.enter(funcName, funcType);
        if (params != null) { params.SemantDeclaration(); }
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
    public IRReg toIR()
    {
        boolean isMain = funcName.equals("main") && funcType.retType == Type.VOID && funcType.params.size() == 0;
        int numLocals = funcType.locals.size();

        IR.add(new IR.label(funcType.fullname));
        if (isMain) { for (String initLabel : IR.globalVars) { IR.add(new IR.jal(initLabel)); } }
        
        // prologue
        IR.add(new IR.addi(IRReg.sp, IRReg.sp, -8));
        IR.add(new IR.sw(IRReg.fp, IRReg.sp, 0));  // save fp
        IR.add(new IR.sw(IRReg.ra, IRReg.sp, 4));  // save ra
        IR.add(new IR.move(IRReg.fp, IRReg.sp));  // update to new fp
        IR.add(new IR.addi(IRReg.sp, IRReg.sp, -(numLocals + 8) * 4));  // allocate stack
        IR.add(new IR.jal("store_tmp_regs"));
        
        body.toIR();
        
        // epilogue
        IR.add(new IR.label(funcType.fullname + "_epilogue"));
        if (isMain) { IR.add(new IR.exit()); }
        IR.add(new IR.jal("retrieve_tmp_regs"));
        IR.add(new IR.addi(IRReg.sp, IRReg.sp, (numLocals + 8) * 4));  // deallocate stack
        IR.add(new IR.lw(IRReg.ra, IRReg.sp, 4));  // retrieve ra
        IR.add(new IR.lw(IRReg.fp, IRReg.sp, 0));  // retrieve fp
        IR.add(new IR.addi(IRReg.sp, IRReg.sp, 8));
        IR.add(new IR.jr(IRReg.ra));  // return
        
        return null;
    }

}
