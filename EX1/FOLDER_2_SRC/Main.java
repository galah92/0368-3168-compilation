import java.io.*;
import java.io.PrintWriter;
import java_cup.runtime.Symbol;
   
public class Main
{
	static public void main(String argv[]) throws IOException
	{
		Lexer l = new Lexer(new FileReader(argv[0]));
		PrintWriter writer = new PrintWriter(argv[1]);
		try
		{
			Symbol s = l.next_token();
			while (s.sym != TokenNames.EOF)
			{
				if (s.sym == TokenNames.COMMENT)
				{
					s = l.next_token();
					continue;
				}

				writer.print(TokenNames.toString(s.sym));
				if (s.value != null) { writer.print("(" + s.value + ")"); }
				writer.print("[" + l.getLine());
				writer.print("," + l.getTokenStartPosition() + "]");

				s = l.next_token();
			}
    		}
		catch (Error e)
		{
			writer = new PrintWriter(argv[1]); // clear content
			writer.println("ERROR");
		}
		catch (Exception e) { e.printStackTrace(); }
		finally
		{
			l.yyclose();
			writer.close();
		}
	}
}

