import java.io.*;
import java.io.PrintWriter;
import java_cup.runtime.Symbol;
import AST.*;
import SymbolTable.*;

public class Main
{
	static public void main(String argv[])
	{
		PrintWriter writer = null;
		try
		{
			SymbolTable.Init();
			AST_Node.initFile();
			writer = new PrintWriter(argv[1]);
			Lexer lexer = new Lexer(new FileReader(argv[0]));
			Parser parser = new Parser(lexer, writer);
			AST_Node AST = (AST_Node) parser.parse().value;
			AST.PrintMe();
			AST_Node.saveFile();
			AST.SemantMe(); // will exit here if error exists
			writer.println("OK");
    	}
		catch (AST_Node.SemanticException e)
		{
			e.printStackTrace();
			if (writer != null) { writer.println("ERROR(" + e.getLineNumber() + ")"); }
		}
		catch (Exception e)
		{
			e.printStackTrace();
			if (writer != null) { writer.println("UnknownError"); }
		}
		finally
		{
			if (writer != null) { writer.close(); }
		}
	}
}
