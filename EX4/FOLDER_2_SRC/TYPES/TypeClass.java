package pcomp;
import java.util.*;


public class TypeClass extends Type
{
	public TypeClass base;
	public List<Symbol> members = new ArrayList<Symbol>();
	public List<Symbol> methods = new ArrayList<Symbol>();
	
	public TypeClass(TypeClass base)
	{
		super("Class");
		this.base = base;
	}

	public boolean isInheritingFrom(TypeClass other)
	{
		if (this == other) { return true; }
		return base != null ? base.isInheritingFrom(other) : false;
	}

	public Type getVarField(String memberName)
	{
		for (Symbol symbol : members)
		{
			if (memberName.equals(symbol.name)) { return symbol.type; }
		}
		return (base != null) ? base.getVarField(memberName) : null;
	}

	public TypeFunc getFuncField(String funcName)
	{
		for (Symbol symbol : methods)
		{
			if (funcName.equals(symbol.name)) { return (TypeFunc)symbol.type; }
		}
		if (base != null) { return base.getFuncField(funcName); }
		return (base != null) ? base.getFuncField(funcName) : null;
	}

	public TypeFunc getFuncField(String funcName, boolean isRecursive)
	{
		for (Symbol symbol : methods)
		{
			if (funcName.equals(symbol.name)) { return (TypeFunc)symbol.type; }
		}
		return (isRecursive && base != null) ? base.getFuncField(funcName) : null;
	}
}
