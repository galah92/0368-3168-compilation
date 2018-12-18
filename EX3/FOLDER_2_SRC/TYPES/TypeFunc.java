package TYPES;

public class TypeFunc extends Type
{
	public Type retType;
	public TypeList params;
	
	public TypeFunc(Type retType, String name, TypeList params)
	{
		this.name = name;
		this.retType = retType;
		this.params = params;
	}

	public boolean isValidArgs(TypeList argsTypes)
	{
		TypeList param = params;
		TypeList arg = argsTypes;
		while (param != null && arg != null)
		{
			if (arg.head != Type.NIL && arg.head != param.head) { return false; }
			boolean isParamClassOrArray = param.head instanceof TypeClass || param.head instanceof TypeArray;
			if (arg.head == Type.NIL && !isParamClassOrArray) { return false; }
			param = param.tail;
			arg = arg.tail;
		}
		return param == null && arg == null;
	}
}
