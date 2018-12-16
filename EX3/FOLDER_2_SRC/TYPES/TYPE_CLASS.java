package TYPES;

public class TYPE_CLASS extends TYPE
{
	public TYPE_CLASS base;
	public TYPE_LIST fields;
	
	public TYPE_CLASS(TYPE_CLASS base, String name, TYPE_LIST fields)
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

	public TYPE getVarField(String varFieldName)
	{
		for (TYPE_LIST it = fields; it != null; it = it.tail)
		{
			if (it.head instanceof TYPE_CLASS_VAR_DEC)
			{
				TYPE_CLASS_VAR_DEC varFieldType = (TYPE_CLASS_VAR_DEC)it.head;
				if (varFieldName.equals(varFieldType.name)) { return varFieldType.varType; }
			}
		}
		if (base != null) { return base.getVarField(varFieldName); }
		return null;
	}

	public TYPE_FUNCTION getFuncField(String funcFieldName)
	{
		for (TYPE_LIST it = fields; it != null; it = it.tail)
		{
			if (it.head instanceof TYPE_FUNCTION)
			{
				TYPE_FUNCTION funcFieldType = (TYPE_FUNCTION)it.head;
				if (funcFieldName.equals(funcFieldType.name)) { return funcFieldType; }
			}
		}
		if (base != null) { return base.getFuncField(funcFieldName); }
		return null;
	}
}
