package AST;
import TYPES.*;
import pcomp.*;
import IR.*;

public class ExpCall extends Exp
{
	public String funcName;
	public Var instanceName;
	public ExpList args;

	public ExpCall(String funcName, Var instanceName, ExpList args)
	{
		this.funcName = funcName;
		this.instanceName = instanceName;
		this.args = args;
	}

	public void logGraphviz()
	{
		if (args != null) args.logGraphviz();
		if (instanceName != null) instanceName.logGraphviz();

		logNode(String.format("ExpCall\n%s", funcName));
		if (args != null) logEdge(args);
		if (instanceName != null) logEdge(instanceName);
	}

	public Type Semant() throws Exception
	{
		TypeList argsTypes = args != null ? args.Semant() : null;

		TypeFunc funcType = null;

		if (instanceName != null) // class methods
		{
			Type t = instanceName.Semant();
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

	public TempReg IRme()
	{
		TempReg t = null;
		
		if (args != null) { t = args.head.IRme(); }
		
		IR.add(new IRcommand_PrintInt(t));
		
		return null;
	}
}
