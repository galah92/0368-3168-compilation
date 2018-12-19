package AST;
import java.io.*;
import TYPES.*;

public abstract class Node
{
	public int lineNumber;
	
	public abstract void toGraphviz();

	public abstract Type Semant() throws Exception;

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
		String filename = "IN_GRAPHVIZ_DOT_FORMAT.txt";
		fileWriter = new PrintWriter(dirname + filename);
		fileWriter.println("digraph");
		fileWriter.println("{");
		fileWriter.println("graph [ordering = \"out\"]");
	}

	public void logNode(String nodeName)
	{
		if (fileWriter == null) { return; }
		fileWriter.format("v%d [label = \"%s\"];\n", instanceNumber, nodeName);
	}

	public void logEdge(Node otherNode)
	{
		if (fileWriter == null) { return; }
		fileWriter.format("v%d -> v%d;\n", instanceNumber, otherNode.instanceNumber);
	}
	
	public static void saveFile()
	{
		if (fileWriter == null) { return; }
		fileWriter.println("}");
		fileWriter.close();
	}
}
