package pcomp;
import java.util.*;


public class TypeFunc extends Type
{
	public Type retType;
	public TypeList params;
	public TypeClass cls;
	public Deque<Type> params2 = new ArrayDeque<Type>();
	public int numLocals = 0;
	// public ArrayList<String

	public TypeFunc(Type retType, String name, TypeList params, TypeClass cls)
	{
		super(name);
		this.retType = retType;
		this.params = params;
		this.cls = cls;
		for (TypeList it = params; it != null; it = it.next)
		{
			params2.add(it.value);
		}
	}

	public boolean isValidArgs(TypeList argsTypes)
	{
		TypeList param = params;
		TypeList arg = argsTypes;
		while (param != null && arg != null)
		{
			if (arg.value != Type.NIL)
			{
				if (arg.value instanceof TypeClass && param.value instanceof TypeClass)
				{
					if (!((TypeClass)arg.value).isInheritingFrom(param.value.name)) { return false; }
				}
				else if (arg.value != param.value) { return false; }
			}
			else
			{
				if (!(param.value instanceof TypeClass || param.value instanceof TypeArray)) { return false; }
			}

			boolean isParamClassOrArray = param.value instanceof TypeClass || param.value instanceof TypeArray;

			param = param.next;
			arg = arg.next;
		}
		return param == null && arg == null;
	}
}
