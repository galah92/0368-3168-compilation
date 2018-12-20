package AST;
import TYPES.*;
import pcomp.*;
import IR.*;

public class StmtWhile extends Stmt
{
	public Exp cond;
	public StmtList body;

	public StmtWhile(Exp cond, StmtList body)
	{
		this.cond = cond;
		this.body = body;
	}

	public void logGraphviz()
	{
		if (cond != null) cond.logGraphviz();
		if (body != null) body.logGraphviz();

		logNode("StmtWhile");
		
		if (cond != null) logEdge(cond);
		if (body != null) logEdge(body);
	}

	public Type Semant() throws Exception
	{
		if (cond.Semant() != Type.INT) { throw new SemanticException(); }
		SymbolTable.beginScope(Type.Scope.WHILE);
		body.Semant();
		SymbolTable.endScope();
		return null;
	}

	public TempReg IRme()
	{
		String label_end   = IRcommand.getLabel("end");
		String label_start = IRcommand.getLabel("start");
	
		IR.getInstance().Add_IRcommand(new IRcommand_Label(label_start));

		TempReg cond_temp = cond.IRme();
		IR.getInstance().Add_IRcommand(new IRcommand_Jump_If_Eq_To_Zero(cond_temp,label_end));

		body.IRme();

		IR.getInstance().Add_IRcommand(new IRcommand_Jump_Label(label_start));		
		IR.getInstance().Add_IRcommand(new IRcommand_Label(label_end));

		return null;
	}

}
