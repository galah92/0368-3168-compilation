package pcomp;
import java.util.*;


public class TypeClass extends Type
{
	public TypeClass base;
	public TypeList fields;
	public List<Symbol> methods = new ArrayList<Symbol>();
	
	public TypeClass(TypeClass base, TypeList fields)
	{
		super("Class");
		this.base = base;
		this.fields = fields;
	}

	public boolean isInheritingFrom(TypeClass other)
	{
		if (this == other) { return true; }
		return base != null ? base.isInheritingFrom(other) : false;
	}

	public Type getVarField(String varFieldName)
	{
		for (TypeList it = fields; it != null; it = it.tail)
		{
			if (it.head instanceof TypeClassVar)
			{
				TypeClassVar varFieldType = (TypeClassVar)it.head;
				if (varFieldName.equals(varFieldType.name)) { return varFieldType.varType; }
			}
		}
		if (base != null) { return base.getVarField(varFieldName); }
		return null;
	}

	public TypeFunc getFuncField(String funcFieldName)
	{
		for (Symbol symbol : methods)
		{
			if (funcFieldName.equals(symbol.name))
			{
				return (TypeFunc)symbol.type;
			}
		}
		if (base != null) { return base.getFuncField(funcFieldName); }
		return null;
	}
}
