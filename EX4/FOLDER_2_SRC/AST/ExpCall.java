package AST;

import pcomp.*;
import java.util.*;


public class ExpCall extends Exp
{

    public String funcName;
    public Var instanceName;
    public ExpList args;
    public List<Exp> args2 = new ArrayList<Exp>();

    public ExpCall(String funcName, Var instanceName, ExpList args)
    {
        this.funcName = funcName;
        this.instanceName = instanceName;
        this.args = args;
        for (ExpList it = args; it != null; it = it.tail) { args2.add(it.head); }
    }

    public void logGraphviz()
    {
        if (args != null) args.logGraphviz();
        if (instanceName != null) instanceName.logGraphviz();

        logNode(String.format("ExpCall\n%s", funcName));
        if (args != null) logEdge(args);
        if (instanceName != null) logEdge(instanceName);
    }

    @Override
    public Type Semant() throws Exception
    {
        TypeList argsTypes = args != null ? args.Semant() : null;

        TypeFunc funcType = null;
        TypeClass classType = SymbolTable.findClass();

        if (instanceName != null) // class variable method
        {
            Type t = instanceName.Semant();
            if (!(t instanceof TypeClass)) { throw new SemanticException("symbol is not of class type: " + instanceName); }
            TypeClass instanceType = (TypeClass)t;
            funcType = instanceType.getFuncField(funcName);
            if (funcType == null) { throw new SemanticException("symbol not found: " + funcName); }
        }
        else if (classType != null) // own class method
        {
            funcType = classType.getFuncField(funcName);
        }
        
        if (funcType == null) // global functions
        {
            Type t = SymbolTable.find(funcName);
            if (t == null) { throw new SemanticException("symbol not found: " + funcName); }
            if (!(t instanceof TypeFunc)) { throw new SemanticException("symbol is not a function: " + funcName); }
            funcType = (TypeFunc)t;
        }

        List<Type> argsTypes2 = new ArrayList<Type>();
        for (TypeList it = argsTypes; it != null; it = it.tail) { argsTypes2.add(it.head); }
        if (!funcType.isValidArgs(argsTypes2)) { throw new SemanticException("invalid arguments to function: " + funcName); }
        return funcType.retType;
    }

    @Override
    public TempReg toIR()
    {
        switch (funcName)
        {
        case "PrintInt":
            IR.add(new IRComm_SysCalls.PrintInt(args2.get(0).toIR()));
            return TempReg.ZeroReg;
        case "PrintString":
            IR.add(new IRComm_SysCalls.PrintString(args2.get(0).toIR()));
            return TempReg.ZeroReg;
        case "PrintTrace":
            System.out.println("PrintTrace not supported yet");
            return TempReg.ZeroReg;
        default:
            IR.add(new IR.Stack.claim(args2.size()));
            for (int i = 0; i < args2.size(); i++)
            {
                IR.add(new IR.Stack.set(args2.get(i).toIR(), i));
            }
            IR.add(new IR.jal(funcName));
            IR.add(new IR.Stack.release(args2.size()));
        }
        TempReg retVal = new TempReg();
        IR.add(new IR.getRetVal(retVal));
        return retVal;
    }

}
