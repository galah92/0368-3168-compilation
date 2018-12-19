package AST;
import java.io.*;
import TYPES.*;
import SymbolStack.*;

public abstract class Node
{
	public int lineNumber;
	
	public abstract void logGraphviz();

	public abstract Type Semant() throws Exception;

	public class SemanticException extends Exception
	{
		public SemanticException() { super(); }
		public SemanticException(String message) { super(message); }

		public int getLineNumber() { return lineNumber + 1; }
	}

	private static int instanceCount = 0;
	public final int instanceNumber = instanceCount++;

	private static final StringBuilder sb = new StringBuilder();

	public void logNode(String nodeName)
	{
		sb.append(String.format("v%d [label = \"%s\"];\n", instanceNumber, nodeName));
	}

	public void logEdge(Node otherNode)
	{
		sb.append(String.format("v%d -> v%d;\n", instanceNumber, otherNode.instanceNumber));
	}
	
	public static void toGraphviz() throws Exception
	{
		String dirname = "./FOLDER_5_OUTPUT/";
		String filename = "IN_GRAPHVIZ_DOT_FORMAT.txt";
		PrintWriter writer = new PrintWriter(dirname + filename);
		writer.println("digraph");
		writer.println("{");
		writer.println("graph [ordering = \"out\"]");
		writer.println(sb.toString());
		writer.println("}");
		writer.close();
	}
}
