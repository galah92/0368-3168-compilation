package pcomp;
import java.util.*;


public class TypeClass extends Type
{
    
    public final String className;
    public TypeClass base;
    public List<Symbol> members = new ArrayList<Symbol>();
    public List<Symbol> methods = new ArrayList<Symbol>();
    public List<Integer> initVals = new ArrayList<Integer>();
    
    public TypeClass(TypeClass base, String className)
    {
        super("Class");
        this.className = className;
        this.base = base;
        if (base != null)
        {
            members.addAll(base.members);
            initVals.addAll(base.initVals);
        }
    }

    public boolean isInheritingFrom(TypeClass other)
    {
        if (this == other) { return true; }
        return base != null ? base.isInheritingFrom(other) : false;
    }

    public Type getMember(String memberName)
    {
        for (Symbol symbol : members)
        {
            if (memberName.equals(symbol.name)) { return symbol.type; }
        }
        return (base != null) ? base.getMember(memberName) : null;
    }

    public TypeFunc getMethod(String funcName)
    {
        for (Symbol symbol : methods)
        {
            if (funcName.equals(symbol.name)) { return (TypeFunc)symbol.type; }
        }
        if (base != null) { return base.getMethod(funcName); }
        return (base != null) ? base.getMethod(funcName) : null;
    }

    public TypeFunc getMethod(String funcName, boolean isRecursive)
    {
        for (Symbol symbol : methods)
        {
            if (funcName.equals(symbol.name)) { return (TypeFunc)symbol.type; }
        }
        return (isRecursive && base != null) ? base.getMethod(funcName) : null;
    }
    
}
