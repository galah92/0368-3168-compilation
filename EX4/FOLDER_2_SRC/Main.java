import java.io.*;
import pcomp.*;


public class Main
{
	static public void main(String argv[])
	{
		PrintWriter writer = null;
		try
		{
			SymbolTable.init();
			writer = new PrintWriter(argv[1]);
			Parser parser = new Parser(argv[0], writer);
			AST.Node tree = (AST.Node)parser.parse().value;
			tree.logGraphviz();
			tree.Semant();
			writer.println("OK");
			SymbolTable.toGraphviz();
			AST.Node.toGraphviz();
    	}
		catch (AST.Node.SemanticException e)
		{
			e.printStackTrace();
			if (writer != null) { writer.println("ERROR(" + e.getLineNumber() + ")"); }
		}
		catch (Exception e)
		{
			e.printStackTrace();
			if (writer != null) { writer.println("ERROR"); }
		}
		finally
		{
			if (writer != null) { writer.close(); }
		}
	}
}
