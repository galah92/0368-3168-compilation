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
			PrintWriter pwriter = new PrintWriter(argv[1]);
			Parser p = new Parser(new Lexer(new FileReader(argv[0])), pwriter);
			AST_decList AST = (AST_decList)p.parse().value;
			AST.PrintMe();
            pwriter.println("OK");
			pwriter.close();
			AST_GRAPHVIZ.getInstance().finalizeFile();
    	}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}


