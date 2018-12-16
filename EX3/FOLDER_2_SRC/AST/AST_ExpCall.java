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

		AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("ExpCall\n%s", funcName));
		if (args != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, args.SerialNumber);
		if (instanceName != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, instanceName.SerialNumber);
	}

	public TYPE SemantMe() throws Exception
	{
		TYPE_LIST argsTypes = args != null ? (TYPE_LIST)args.SemantMe() : null;

		// deal with class methods
		if (instanceName != null)
		{
			TYPE t = instanceName.SemantMe();
			if (!(t instanceof TYPE_CLASS)) { throw new SemanticException(); }
			TYPE_CLASS instanceType = (TYPE_CLASS)t;
			TYPE_FUNCTION tf = instanceType.getFuncField(funcName);
			if (tf != null) { return tf.retType; }
		}

		// deal with global functions
		TYPE t = SymbolTable.getInstance().find(funcName);
		if (t == null && !(t instanceof TYPE_FUNCTION)) { throw new SemanticException(); }
		TYPE_FUNCTION funcType = (TYPE_FUNCTION)t;
		return funcType.retType;
	}
}
