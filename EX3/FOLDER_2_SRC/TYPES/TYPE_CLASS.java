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

	public TYPE getVarField(String varFieldName)
	{
		for (TYPE_LIST it = fields; it != null; it = it.tail)
		{
			if (it.head instanceof TYPE_CLASS_VAR_DEC)
			{
				TYPE_CLASS_VAR_DEC classVarType = (TYPE_CLASS_VAR_DEC)it.head;
				if (varFieldName.equals(classVarType.name)) { return classVarType.varType; }
			}
		}
		if (base != null) { return base.getVarField(varFieldName); }
		return null;
	}
}
