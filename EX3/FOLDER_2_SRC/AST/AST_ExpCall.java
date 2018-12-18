package AST;
import TYPES.*;
import SymbolTable.*;

public class AST_ExpCall extends AST_Exp
{
	public String funcName;
	public AST_Var instanceName;
	public AST_ExpList args;

	public AST_ExpCall(String funcName, AST_Var instanceName, AST_ExpList args)
	{
		this.funcName = funcName;
		this.instanceName = instanceName;
		this.args = args;
	}

	public void PrintMe()
	{
		if (args != null) args.PrintMe();
		if (instanceName != null) instanceName.PrintMe();

		logNode(String.format("ExpCall\n%s", funcName));
		if (args != null) logEdge(args);
		if (instanceName != null) logEdge(instanceName);
	}

	public Type SemantMe() throws Exception
	{
		TypeList argsTypes = args != null ? args.SemantMe() : null;

		TypeFunc funcType = null;

		if (instanceName != null) // class methods
		{
			Type t = instanceName.SemantMe();
			if (!(t instanceof TypeClass)) { throw new SemanticException(t.name); }
			TypeClass instanceType = (TypeClass)t;
			funcType = instanceType.getFuncField(funcName);
		}
		else // global functions
		{
			Type t = SymbolTable.find(funcName);
			if (t == null && !(t instanceof TypeFunc)) { throw new SemanticException(); }
			funcType = (TypeFunc)t;
		}

		if (funcType == null) { throw new SemanticException("function symbol not found"); }

		if (!funcType.isValidArgs(argsTypes)) { throw new SemanticException("invalid arguments to function"); }
		return funcType.retType;
	}
}
