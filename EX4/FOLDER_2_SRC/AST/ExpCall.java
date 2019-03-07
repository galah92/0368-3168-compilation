package AST;

import pcomp.*;
import java.util.*;


public class ExpCall extends Exp
{

    public String funcName;
    public Var instanceVar;
    public ExpList args;
    public List<Exp> args2 = new ArrayList<Exp>();
    public String funcFullname;

    public ExpCall(String funcName, Var instanceVar, ExpList args)
    {
        this.funcName = funcName;
        this.instanceVar = instanceVar;
        this.args = args;
        for (ExpList it = args; it != null; it = it.tail) { args2.add(it.head); }
    }

    public void logGraphviz()
    {
        if (args != null) args.logGraphviz();
        if (instanceVar != null) instanceVar.logGraphviz();

        logNode(String.format("ExpCall\n%s", funcName));
        if (args != null) logEdge(args);
        if (instanceVar != null) logEdge(instanceVar);
    }

    @Override
    public Type Semant() throws Exception
    {
        TypeList argsTypes = args != null ? args.Semant() : null;

        TypeFunc funcType = null;
        TypeClass classType = SymbolTable.findClass();

        if (instanceVar != null) // class variable method
        {
            Type t = instanceVar.Semant();
            if (!(t instanceof TypeClass)) { throw new SemanticException("symbol is not of class type: " + instanceVar); }
            TypeClass instanceType = (TypeClass)t;
            funcType = instanceType.getMethod(funcName);
            if (funcType == null) { throw new SemanticException("symbol not found: " + funcName); }
        }
        else if (classType != null) // own class method
        {
            funcType = classType.getMethod(funcName);
        }
        
        if (funcType == null) // global functions
        {
            Type t = SymbolTable.find(funcName);
            if (t == null) { throw new SemanticException("symbol not found: " + funcName); }
            if (!(t instanceof TypeFunc)) { throw new SemanticException("symbol is not a function: " + funcName); }
            funcType = (TypeFunc)t;
        }

        funcFullname = funcType.fullname;
        List<Type> argsTypes2 = new ArrayList<Type>();
        for (TypeList it = argsTypes; it != null; it = it.tail) { argsTypes2.add(it.head); }
        if (!funcType.isValidArgs(argsTypes2)) { throw new SemanticException("invalid arguments to function: " + funcName); }
        return funcType.retType;
    }

    @Override
    public IRReg toIR()
    {
        switch (funcName)
        {
        case "PrintInt":
            IR.add(new IR.printInt(args2.get(0).toIR()));
            break;
        case "PrintString":
        IR.add(new IR.printString(args2.get(0).toIR()));
            break;
        case "PrintTrace":
            System.out.println("PrintTrace not supported yet");
            break;
        default:
            IR.add(new IR.addi(IRReg.sp, IRReg.sp, -(args2.size() + 1) * 4));
            if (instanceVar != null)
            {
                IRReg instanceReg = instanceVar.toIR();
                IR.add(new IR.lw(instanceReg, instanceReg, 0));
                IR.add(new IR.sw(instanceReg, IRReg.sp, 0 * 4));  // add "this" as first param
            }
            else
            {
                IR.add(new IR.sw(IRReg.zero, IRReg.sp, 0 * 4));
            }
            for (int i = 0; i < args2.size(); i++)
            {
                IR.add(new IR.sw(args2.get(i).toIR(), IRReg.sp, (i + 1) * 4));
            }
            IR.add(new IR.jal(funcFullname));
            IR.add(new IR.addi(IRReg.sp, IRReg.sp, (args2.size() + 1) * 4));
        }
        return IRReg.v0;  // v0 store the return value
    }

}
