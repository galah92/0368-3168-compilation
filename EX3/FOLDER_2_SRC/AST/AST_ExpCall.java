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
		TypeList argsTypes = args != null ? (TypeList)args.SemantMe() : null;

		// deal with class methods
		if (instanceName != null)
		{
			Type t = instanceName.SemantMe();
			if (!(t instanceof TypeClass)) { throw new SemanticException(); }
			TypeClass instanceType = (TypeClass)t;
			TypeFunc tf = instanceType.getFuncField(funcName);
			if (tf != null) { return tf.retType; }
		}

		// deal with global functions
		Type t = SymbolTable.find(funcName);
		if (t == null && !(t instanceof TypeFunc)) { throw new SemanticException(); }
		TypeFunc funcType = (TypeFunc)t;
		return funcType.retType;
	}
}
