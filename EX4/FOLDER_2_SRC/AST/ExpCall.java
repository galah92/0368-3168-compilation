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
    public int numMethod = -1;

    public ExpCall(String funcName, Var instanceVar, ExpList args)
    {
        this.funcName = funcName;
        this.instanceVar = instanceVar;
        this.args = args;
        for (ExpList it = args; it != null; it = it.tail) { args2.add(it.head); }
    }

    @Override
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
            for (int i = 0; i < instanceType.methods.size(); i++)
            {
                if (funcName.equals(instanceType.methods.get(i).name)) { numMethod = i; break; }
            }
            if (funcType == null) { throw new SemanticException("symbol not found: " + funcName); }
        }
        else if (classType != null) // own class method
        {
            for (int i = 0; i < classType.methods.size(); i++)
            {
                Symbol method = classType.methods.get(i);
                if (funcName.equals(method.name))
                {
                    funcType = (TypeFunc)method.type;
                    numMethod = i;
                    break;
                }
            }
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
            IRReg addrReg = new IRReg.TempReg();
            String printTraceLabel = IR.uniqueLabel("print_trace_loop");
            IR.add(new IR.move(addrReg, IRReg.fp)); // we loop over all fp's
            IR.add(new IR.label(printTraceLabel));  // start of loop
            IR.add(new IR.lw(IRReg.a0, addrReg, 2 * 4));  // get function name
            IR.add(new IR.printString(IRReg.a0));
            IR.add(new IR.lw(addrReg, addrReg, 0));  // deference and get next fp
            IR.add(new IR.bne(addrReg, IRReg.zero, printTraceLabel));  // loop condition
            break;
        default:
            IR.add(new IR.addi(IRReg.sp, IRReg.sp, -(args2.size() + 1) * 4));  // lower stack
            if (numMethod != -1)  // class method
            {
                IRReg instanceReg;
                if (instanceVar != null)
                {
                    instanceReg = instanceVar.toIR();  // get instance
                    IR.add(new IR.lw(instanceReg, instanceReg, 0));  // dereference instance
                }
                else
                {
                    instanceReg = new IRReg.TempReg();
                    IR.add(new IR.lw(instanceReg, IRReg.fp, 3 * 4));  // get "this"
                }
                IR.add(new IR.beq(instanceReg, IRReg.zero, "exit_invalid_dereference"));  // runtime check
                IR.add(new IR.sw(instanceReg, IRReg.sp, 0 * 4));  // add "this" as first argument
                for (int i = 0; i < args2.size(); i++)  // add arguments
                {
                    IR.add(new IR.sw(args2.get(i).toIR(), IRReg.sp, (i + 1) * 4));
                }
                IR.add(new IR.lw(instanceReg, instanceReg, 0));  // dereference vtable
                IR.add(new IR.lw(instanceReg, instanceReg, numMethod * 4));  // dereference method
                IR.add(new IR.jalr(instanceReg));  // actual call to method
            }
            else  // global function
            {
                IR.add(new IR.sw(IRReg.zero, IRReg.sp, 0 * 4));  // add zero as first argument
                for (int i = 0; i < args2.size(); i++)  // add arguments
                {
                    IR.add(new IR.sw(args2.get(i).toIR(), IRReg.sp, (i + 1) * 4));
                }
                IR.add(new IR.jal("_" + funcFullname));  // actual call to function
            }
            IR.add(new IR.addi(IRReg.sp, IRReg.sp, (args2.size() + 1) * 4));  // fold stack
            IRReg retVal = new IRReg.TempReg();
            IR.add(new IR.move(retVal, IRReg.v0));  // v0 store the return value
            return retVal;
        }
        return null;
    }

}
