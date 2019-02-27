package pcomp;
import java.util.*;


public class TypeFunc extends Type
{
	public Type retType;
	public TypeList params;
	public TypeClass cls;
	public Deque<Type> params2 = new ArrayDeque<Type>();
	public int numLocals = 0;

	public TypeFunc(Type retType, TypeList params, TypeClass cls)
	{
		super("Function");
		this.retType = retType;
		this.params = params;
		this.cls = cls;
		for (TypeList it = params; it != null; it = it.tail)
		{
			params2.add(it.head);
		}
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
