package pcomp;
import java.util.*;


public class TypeFunc extends Type
{

	public final String fullname;
	public Type retType;
	public List<Symbol> params = new ArrayList<Symbol>();
	public List<Symbol> locals = new ArrayList<Symbol>();

	public TypeFunc(Type retType, String fullname)
	{
		super("Function");
		this.fullname = fullname;
		this.retType = retType;
	}

	public boolean isValidArgs(List<Type> args)
	{
		if (args.size() != params.size()) { return false; }
		Iterator<Symbol> it1 = params.iterator();
		Iterator<Type> it2 = args.iterator();
		while (it1.hasNext() && it2.hasNext())
		{
			Symbol param = it1.next();
			Type arg = it2.next();
			if (arg != Type.NIL)
			{
				if (arg instanceof TypeClass && param.type instanceof TypeClass)
				{
					if (!((TypeClass)arg).isInheritingFrom((TypeClass)param.type)) { return false; }
				}
				else if (arg != param.type) { return false; }
			}
			else
			{
				if (!(param.type instanceof TypeClass || param.type instanceof TypeArray)) { return false; }
			}
		}
		return true;
	}

}
