import java.io.*;
import SymbolTable.*;

public class Main
{
	static public void main(String argv[])
	{
		PrintWriter writer = null;
		try
		{
			SymbolTable.Init();
			AST.Node.initFile();
			writer = new PrintWriter(argv[1]);
			Lexer lexer = new Lexer(new FileReader(argv[0]));
			Parser parser = new Parser(lexer, writer);
			AST.Node tree = (AST.Node)parser.parse().value;
			tree.toGraphviz();
			tree.Semant();
			writer.println("OK");
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
			AST.Node.saveFile();
			if (writer != null) { writer.close(); }
		}
	}
}
