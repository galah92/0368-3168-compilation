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
            if (classType.getMethod(funcName, false) != null) { throw new SemanticException("function already declared in current class: " + funcName); }
            TypeFunc overriddenFunc = classType.getMethod(funcName);
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
    public IRReg toIR()
    {
        boolean isMain = funcName.equals("main") && funcType.retType == Type.VOID && funcType.params.size() == 0;
        int numLocals = funcType.locals.size();
        
        IR.add(new IR.label(funcName));
        if (isMain) { for (String initLabel : IR.globalVars) { IR.add(new IR.jal(initLabel)); } }
        
        // prologue
        IR.add(new IR.addi(IRReg.sp, IRReg.sp, -8));
        IR.add(new IR.sw(IRReg.fp, IRReg.sp, 0));  // save fp
        IR.add(new IR.sw(IRReg.ra, IRReg.sp, 4));  // save ra
        IR.add(new IR.move(IRReg.fp, IRReg.sp));  // update to new fp
        IR.add(new IR.addi(IRReg.sp, IRReg.sp, -(numLocals + 1) * 4));  // allocate stack
        IR.add(new IR.sw(IRReg.s0, IRReg.fp, -1 * 4));
        IR.add(new IR.comment("end of prologue"));
        
        body.toIR();
        
        // epilogue
        IR.add(new IR.label(funcName + "_epilogue"));
        if (!isMain)
        {
            IR.add(new IR.lw(IRReg.s0, IRReg.fp, -1 * 4));
            IR.add(new IR.addi(IRReg.sp, IRReg.sp, (numLocals + 1) * 4));  // deallocate stack
            IR.add(new IR.lw(IRReg.ra, IRReg.sp, 4));  // retrieve ra
            IR.add(new IR.lw(IRReg.fp, IRReg.sp, 0));  // retrieve fp
            IR.add(new IR.addi(IRReg.sp, IRReg.sp, 8));
            IR.add(new IR.jr(IRReg.ra));  // return
        }
        
        return null;
    }

}
