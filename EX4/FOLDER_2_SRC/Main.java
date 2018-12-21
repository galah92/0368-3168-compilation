import java.io.*;
import pcomp.*;
import IR.*;


public class Main
{
	static public void main(String argv[])
	{
		PrintWriter writer = null;
		try
		{
			writer = new PrintWriter(argv[1]);
			Parser parser = new Parser(argv[0], writer);
			AST.Node tree = (AST.Node)parser.parse().value;

			SymbolTable.init();
			tree.Semant();
			// tree.toIR();
			// IR.toMIPS();
			
			tree.toGraphviz("./FOLDER_5_OUTPUT/AST.txt");
			SymbolTable.toGraphviz("./FOLDER_5_OUTPUT/SymbolTable.txt");
			// MIPSGen.toFile("./FOLDER_5_OUTPUT/MIPS.txt");
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
			if (writer != null) { writer.close(); }
		}
	}
}
