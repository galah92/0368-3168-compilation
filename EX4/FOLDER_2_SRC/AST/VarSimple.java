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
            for (int i = SymbolTable.findFunc().currMaxLocals - 1; i >= 0; i--)
            {
                if (varName.equals(SymbolTable.findFunc().locals.get(i).name))
                {
                    numLocal = i;
                    break;
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
        if (SymbolTable.isScope(Type.Scope.CLASS.name))
        {
            List<Symbol> classMembers = SymbolTable.findClass().members;
            for (int i = 0; i < classMembers.size(); i++)
            {
                if (varName.equals(classMembers.get(i).name))
                {
                    numMember = i;
                }
            }
        }
        return varType;
    }

    @Override
    public IRReg toIR()
    {
        IRReg reg = new IRReg.TempReg();
        if (numLocal != -1)
        {
            IR.add(new IR.addi(reg, IRReg.fp, (-numLocal - 9) * 4));
        }
        else if (numParam != -1)
        {
            IR.add(new IR.addi(reg, IRReg.fp, (numParam + 4) * 4));
        }
        else if (numMember != -1)
        {
            IR.add(new IR.lw(reg, IRReg.fp, 3 * 4));  // "this"
            IR.add(new IR.addi(reg, reg, (numMember + 1) * 4));  // calculate address of member
        }
        else
        {
            IR.add(new IR.getGlobal(varName, reg));
        }
        return reg;
    }

}
