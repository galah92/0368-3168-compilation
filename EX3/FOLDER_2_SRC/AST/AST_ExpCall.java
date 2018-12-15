package AST;
import TYPES.*;
import SYMBOL_TABLE.*;

public class AST_ExpCall extends AST_EXP
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
		if (instanceName != null) args.PrintMe();

		AST_GRAPHVIZ.getInstance().logNode(SerialNumber, String.format("ExpCall\n%s", funcName));
		if (args != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, args.SerialNumber);
		if (instanceName != null) AST_GRAPHVIZ.getInstance().logEdge(SerialNumber, instanceName.SerialNumber);
	}

	public TYPE SemantMe() throws Exception
	{
		TYPE t = SYMBOL_TABLE.getInstance().find(funcName);
		if (t == null && !(t instanceof TYPE_FUNCTION)) { throw new Exception(); }
		TYPE_FUNCTION funcType = (TYPE_FUNCTION)t;
		
		if (args != null) args.SemantMe();

		if (instanceName != null)
		{
			t = instanceName.SemantMe();
			if (!(t instanceof TYPE_CLASS)) { throw new Exception(); }
			TYPE instanceType = (TYPE_CLASS)t;
			// TODO: check that funcType is a member of instanceType
		}

		return funcType.retType;
	}
}
