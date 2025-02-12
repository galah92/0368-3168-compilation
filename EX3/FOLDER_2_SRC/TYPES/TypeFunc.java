package TYPES;

public class TypeFunc extends Type
{
	public Type retType;
	public TypeList params;
	public TypeClass cls;
	
	public TypeFunc(Type retType, String name, TypeList params)
	{
		super(name);
		this.retType = retType;
		this.params = params;
	}

	public TypeFunc(Type retType, String name, TypeList params, TypeClass cls)
	{
		super(name);
		this.retType = retType;
		this.params = params;
		this.cls = cls;

	}

	public boolean isValidArgs(TypeList argsTypes)
	{
		TypeList param = params;
		TypeList arg = argsTypes;
		while (param != null && arg != null)
		{
			if (arg.head != Type.NIL)
			{
				if (arg.head instanceof TypeClass && param.head instanceof TypeClass)
				{
					if (!((TypeClass)arg.head).isInheritingFrom(param.head.name)) { return false; }
				}
				else if (arg.head != param.head) { return false; }
			}
			else
			{
				if (!(param.head instanceof TypeClass || param.head instanceof TypeArray)) { return false; }
			}

			boolean isParamClassOrArray = param.head instanceof TypeClass || param.head instanceof TypeArray;

			param = param.tail;
			arg = arg.tail;
		}
		return param == null && arg == null;
	}
}
