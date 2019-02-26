package AST;

import pcomp.*;

public class StmtReturn extends Stmt
{
	public Exp exp;

	public StmtReturn(Exp exp)
	{
		this.exp = exp;
	}

	public void logGraphviz()
	{
		if (exp != null) exp.logGraphviz();
		logNode("StmtReturn");
		if (exp != null) logEdge(exp);
	}

	public Type Semant() throws Exception
	{
		TypeFunc funcType = SymbolTable.findFunc();
		if (funcType == null) { throw new SemanticException(); }
		Type expType = exp != null ? exp.Semant() : Type.VOID;
		if (funcType.retType != expType) {
			if (!(expType == Type.NIL) || 
				(!(funcType.retType instanceof TypeArray) &&
				!(funcType.retType instanceof TypeClass))) {
				throw new SemanticException(funcType.retType + ", " + expType); 
			}
		}
		return expType;
	}

	@Override
	public TempReg toIR()
	{
		// TODO: implement
		TempReg tmp = exp == null ? null : exp.toIR();
		return null;
	}

}
