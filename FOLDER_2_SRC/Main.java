import java.io.*;
import java.io.PrintWriter;
import java_cup.runtime.Symbol;
   
public class Main
{
	static public void main(String argv[])
	{
		try
		{
			FileReader file_reader = new FileReader(argv[0]);
			PrintWriter file_writer = new PrintWriter(argv[1]);
			Lexer l = new Lexer(file_reader);
			Symbol s = l.next_token();
			while (s.sym != TokenNames.EOF)
			{
				if (s.value != null) { System.out.print("(" + s.value + ")"); }
				System.out.print("[");
				System.out.print(l.getLine());
				System.out.println("," + l.getTokenStartPosition() + "]");

				if (s.value != null) { file_writer.print("(" + s.value + ")"); }
				file_writer.print("[");
				file_writer.print(l.getLine());
				file_writer.print("," + l.getTokenStartPosition() + "]\n");

				s = l.next_token();
			}
			l.yyclose();
			file_writer.close();
    		}
		catch (Exception e) { e.printStackTrace(); }
	}
}

