package AST;

import java.io.*;
import java.io.PrintWriter;

public class ASTGraphviz
{
	private static PrintWriter fileWriter;

	private ASTGraphviz() {}

	public static void initFile() throws Exception
	{
		String dirname = "./FOLDER_5_OUTPUT/";
		String filename = "AST_IN_GRAPHVIZ_DOT_FORMAT.txt";
		fileWriter = new PrintWriter(dirname + filename);
		fileWriter.print("digraph\n");
		fileWriter.print("{\n");
		fileWriter.print("graph [ordering = \"out\"]\n");
	}

	public static void logNode(int nodeSerialNumber, String nodeName)
	{
		if (fileWriter == null) { return; }
		fileWriter.format("v%d [label = \"%s\"];\n", nodeSerialNumber, nodeName);
	}

	public static void logEdge(int fatherNodeSerialNumber, int sonNodeSerialNumber)
	{
		if (fileWriter == null) { return; }
		fileWriter.format("v%d -> v%d;\n", fatherNodeSerialNumber, sonNodeSerialNumber);
	}
	
	public static void saveFile()
	{
		if (fileWriter == null) { return; }
		fileWriter.print("}\n");
		fileWriter.close();
	}
}
