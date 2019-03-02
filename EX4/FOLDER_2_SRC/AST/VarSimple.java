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
    public TempReg toIR()
    {
        TempReg reg = new TempReg();
        if (numParam != -1)
        {
            IR.add(new IR.frameGet(reg, numParam + 2));
        }
        else if (numLocal != -1)
        {
            IR.add(new IR.frameGet(reg, -numLocal - 1));
        }
        else
        {
            System.out.println("VarSimple.toIR() not implemented yet");
        }
        return reg;
    }

}
