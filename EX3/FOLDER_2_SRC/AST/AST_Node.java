package AST;
import java.io.PrintWriter;
import TYPES.*;

public abstract class AST_Node
{
	public int lineNumber;
	
	public abstract void PrintMe();

	public abstract Type SemantMe() throws Exception;

	public class SemanticException extends Exception
	{
		public SemanticException() { super(); }
		public SemanticException(String message) { super(message); }

		public int getLineNumber() { return lineNumber + 1; }
	}

	private static int instanceCount = 0;
	public final int instanceNumber = instanceCount++;

	private static PrintWriter fileWriter;

	public static void initFile() throws Exception
	{
		String dirname = "./FOLDER_5_OUTPUT/";
		String filename = "AST_IN_GRAPHVIZ_DOT_FORMAT.txt";
		fileWriter = new PrintWriter(dirname + filename);
		fileWriter.print("digraph\n");
		fileWriter.print("{\n");
		fileWriter.print("graph [ordering = \"out\"]\n");
	}

	public void logNode(String nodeName)
	{
		if (fileWriter == null) { return; }
		fileWriter.format("v%d [label = \"%s\"];\n", instanceNumber, nodeName);
	}

	public void logEdge(AST_Node otherNode)
	{
		if (fileWriter == null) { return; }
		fileWriter.format("v%d -> v%d;\n", instanceNumber, otherNode.instanceNumber);
	}
	
	public static void saveFile()
	{
		if (fileWriter == null) { return; }
		fileWriter.print("}\n");
		fileWriter.close();
	}
}
