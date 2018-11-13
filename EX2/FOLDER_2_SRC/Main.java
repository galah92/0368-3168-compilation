   
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
			Parser p = new Parser(new Lexer(new FileReader(argv[0])));
			AST_STMT_LIST AST = (AST_STMT_LIST) p.parse().value;
			AST.PrintMe();
			PrintWriter file_writer = new PrintWriter(argv[1]);
            file_writer.print("OK");
			file_writer.close();
			AST_GRAPHVIZ.getInstance().finalizeFile();
    		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}


