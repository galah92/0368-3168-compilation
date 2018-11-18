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
			AST_STMT_LIST AST = (AST_STMT_LIST)p.parse().value;
			AST.PrintMe();
            pwriter.print("OK");
			pwriter.close();
			AST_GRAPHVIZ.getInstance().finalizeFile();
    	}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}


