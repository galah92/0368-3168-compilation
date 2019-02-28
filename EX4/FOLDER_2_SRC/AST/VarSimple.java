package AST;

import pcomp.*;
import java.util.*;


public class VarSimple extends Var
{
    public String varName;
    public int id = -1;
    public int numParam = -1;
    
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
                    id = i;
                }
            }
            List<Symbol> funcParams = SymbolTable.findFunc().params2;
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

        }
        // System.out.println(varName + " : " + id);
        IR.add(new IRComm_Load(reg, varName));
        return reg;
    }
}
