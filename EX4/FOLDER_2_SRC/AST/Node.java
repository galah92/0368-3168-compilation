package AST;
import java.io.*;
import TYPES.*;
import pcomp.*;

public abstract class Node
{
	public int lineNumber;
	
	public abstract void logGraphviz();

	public abstract Type Semant() throws Exception;

	public abstract TempReg toIR();

	public class SemanticException extends Exception
	{
		public SemanticException() { super(); }
		public SemanticException(String message) { super(message); }

		public int getLineNumber() { return lineNumber + 1; }
	}

	private static int serialCounter = 0;
	public final int serialNum = serialCounter++;

	private static final StringBuilder sb = new StringBuilder();

	public void logNode(String nodeName)
	{
		sb.append(String.format("v%d [label = \"%s\"];\n", serialNum, nodeName));
	}

	public void logEdge(Node otherNode)
	{
		sb.append(String.format("v%d -> v%d;\n", serialNum, otherNode.serialNum));
	}
	
	public void toGraphviz(String outFile) throws Exception
	{
		logGraphviz();
		PrintWriter writer = new PrintWriter(outFile);
		writer.println("digraph");
		writer.println("{");
		writer.println("graph [ordering = \"out\"]");
		writer.println(sb.toString());
		writer.println("}");
		writer.close();
	}
}
