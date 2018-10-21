import java.io.*;
import java.io.PrintWriter;
import java_cup.runtime.Symbol;
   
public class Main
{
	static public void main(String argv[])
	{
		try
		{
			Lexer l = new Lexer(new FileReader(argv[0]));
			PrintWriter fileWriter = new PrintWriter(argv[1]);
			Symbol s = l.next_token();
			while (s.sym != TokenNames.EOF)
			{
				if (s.sym == TokenNames.COMMENT)
				{
					s = l.next_token();
					continue;
				}

				fileWriter.print(TokenNames.toString(s.sym));
				if (s.value != null) { fileWriter.print("(" + s.value + ")"); }
				fileWriter.print("[" + l.getLine());
				fileWriter.print("," + l.getTokenStartPosition() + "]\n");

				s = l.next_token();
			}
			l.yyclose();
			fileWriter.close();
    		}
		catch (Exception e) { e.printStackTrace(); }
	}
}

