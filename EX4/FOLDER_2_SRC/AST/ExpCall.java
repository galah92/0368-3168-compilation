package AST;

import pcomp.*;


public class ExpCall extends Exp
{

    public String funcName;
    public Var instanceName;
    public ExpList args;

    public ExpCall(String funcName, Var instanceName, ExpList args)
    {
        this.funcName = funcName;
        this.instanceName = instanceName;
        this.args = args;
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

        if (instanceName != null) // class methods
        {
            Type t = instanceName.Semant();
            if (!(t instanceof TypeClass)) { throw new SemanticException(t.name); }
            TypeClass instanceType = (TypeClass)t;
            funcType = instanceType.getFuncField(funcName);
        }
        else // global functions
        {
            Type t = SymbolTable.find(funcName);
            if (t == null && !(t instanceof TypeFunc)) { throw new SemanticException(); }
            funcType = (TypeFunc)t;
        }

        if (funcType == null) { throw new SemanticException("function symbol not found"); }

        if (!funcType.isValidArgs(argsTypes)) { throw new SemanticException("invalid arguments to function"); }
        return funcType.retType;
    }

    @Override
    public TempReg toIR()
    {
        TempReg firstArg = null;
        if (args != null) { firstArg = args.head.toIR(); }
        switch (funcName)
        {
        case "PrintInt":
            IR.add(new IRComm_SysCalls.PrintInt(firstArg));
            return TempReg.ZeroReg;
        case "PrintString":
            IR.add(new IRComm_SysCalls.PrintString(firstArg));
            return TempReg.ZeroReg;
        case "PrintTrace":
            System.out.println("PrintTrace not supported yet");
            return TempReg.ZeroReg;
        default:
            System.out.println("Default func call should go here");
        }
        TempReg retVal = new TempReg();
        return retVal;
    }

}
