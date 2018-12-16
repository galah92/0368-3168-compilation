package TYPES;

public class TypeClass extends Type
{
	public TypeClass base;
	public TypeList fields;
	
	public TypeClass(TypeClass base, String name, TypeList fields)
	{
		this.name = name;
		this.base = base;
		this.fields = fields;
	}

	public boolean isInheritingFrom(String ancestor)
	{
		if (ancestor.equals(name)) { return true; }
		return base != null ? base.isInheritingFrom(ancestor) : false;
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
		for (TypeList it = fields; it != null; it = it.tail)
		{
			if (it.head instanceof TypeFunc)
			{
				TypeFunc funcFieldType = (TypeFunc)it.head;
				if (funcFieldName.equals(funcFieldType.name)) { return funcFieldType; }
			}
		}
		if (base != null) { return base.getFuncField(funcFieldName); }
		return null;
	}
}
