import java.io.*;
import java.io.PrintWriter;
import java_cup.runtime.Symbol;
import AST.*;

public class Main
{
	static public void main(String argv[])
	{
		try
		{
			Lexer lexer = new Lexer(new FileReader(argv[0]));
			Parser parser = new Parser(lexer, new PrintWriter(argv[1]));
			AST_DEC_LIST AST = (AST_DEC_LIST) parser.parse().value;
//			AST.PrintMe();
			AST.SemantMe();
			AST_GRAPHVIZ.getInstance().finalizeFile();			
    	}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}


