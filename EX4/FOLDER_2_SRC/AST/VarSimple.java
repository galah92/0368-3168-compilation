package AST;

import pcomp.*;
import java.util.*;


public class VarSimple extends Var
{

    public String varName;
    
    public VarSimple(String varName)
    {
        this.varName = varName;
    }

    @Override
    public void logGraphviz()
    {
        logNode(String.format("VarSimple\n(%s)", varName));
    }
    
    @Override
    public Type Semant() throws Exception
    {
        Type varType = SymbolTable.find(varName);

        if (SymbolTable.isScope(Type.Scope.FUNC.name))
        {
            List<Symbol> funcLocals = SymbolTable.findFunc().locals;
            for (int i = 0; i < funcLocals.size(); i++)
            {
                if (varName.equals(funcLocals.get(i).name))
                {
                    numLocal = i;
                }
            }
            List<Symbol> funcParams = SymbolTable.findFunc().params;
            for (int i = 0; i < funcParams.size(); i++)
            {
                if (varName.equals(funcParams.get(i).name))
                {
                    numParam = i;
                }
            }
        }
        return varType;
    }

    @Override
    public IRReg toIR()
    {
        IRReg reg = new IRReg.TempReg();
        if (numParam != -1)
        {
            IR.add(new IR.addi(reg, IRReg.fp, (numParam + 2) * 4));
        }
        else if (numLocal != -1)
        {
            IR.add(new IR.addi(reg, IRReg.fp, (-numLocal - 1) * 4));
        }
        else
        {
            IR.add(new IR.getGlobal(varName, reg));
        }
        return reg;
    }

}
