package AST;

import pcomp.*;


public class VarClassField extends Var
{

    public Var instance;
    public String fieldName;
    public int numMember;
    
    public VarClassField(Var instance, String fieldName)
    {
        this.instance = instance;
        this.fieldName = fieldName;
    }

    public void logGraphviz()
    {
        instance.logGraphviz();
        logNode(String.format("VarClassField\n%s", fieldName));
        logEdge(instance);
    }

    public Type Semant() throws Exception
    {
        Type t = instance.Semant();
        if (!(t instanceof TypeClass)) { throw new SemanticException(); }
        TypeClass typeClass = (TypeClass)t;
        for (int i = 0; i < typeClass.members.size(); i++)
        {
            Symbol member = typeClass.members.get(i);
            if (fieldName.equals(member.name))
            {
                numMember = i;
                return member.type;
            }
        }
        throw new SemanticException("instance has no symbol: " + fieldName);
    }

    @Override
    public IRReg toIR()
    {
        IRReg reg = instance.toIR();
        IR.add(new IR.lw(reg, reg, 0));  // dereference instance
        IR.add(new IR.addi(reg, reg, numMember * 4));  // access member
        return reg;
    }
    
}
