package pcomp;
import java.util.*;


public class TypeClass extends Type
{
    
    public final String className;
    public TypeClass base;
    public List<Symbol> members = new ArrayList<Symbol>();
    public List<Symbol> methods = new ArrayList<Symbol>();
    public List<Object> initVals = new ArrayList<Object>();
    
    public TypeClass(TypeClass base, String className)
    {
        super("Class");
        this.className = className;
        this.base = base;
        if (base != null)
        {
            for(Symbol s : base.methods){methods.add((new Symbol(s.name, s.type)));}
            members = new ArrayList<Symbol>(base.members);
            initVals = new ArrayList<Object>(base.initVals);
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
        return null;
    }

    public TypeFunc getMethod(String funcName)
    {
        for (Symbol symbol : methods)
        {
            if (funcName.equals(symbol.name)) { return (TypeFunc)symbol.type; }
        }
        return null;
    }
    
}
