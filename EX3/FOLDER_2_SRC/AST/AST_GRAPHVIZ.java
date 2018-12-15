package AST;

import java.io.*;
import java.io.PrintWriter;

public class AST_GRAPHVIZ
{
	private PrintWriter fileWriter;
	private static AST_GRAPHVIZ instance = null;

	private AST_GRAPHVIZ() {}

	public static AST_GRAPHVIZ getInstance()
	{
		if (instance == null)
		{
			instance = new AST_GRAPHVIZ();
			try
			{
				String dirname = "./FOLDER_5_OUTPUT/";
				String filename = "AST_IN_GRAPHVIZ_DOT_FORMAT.txt";
				instance.fileWriter = new PrintWriter(dirname + filename);
			}
			catch (Exception e) { e.printStackTrace(); }

			instance.fileWriter.print("digraph\n");
			instance.fileWriter.print("{\n");
			instance.fileWriter.print("graph [ordering = \"out\"]\n");
		}
		return instance;
	}

	public void logNode(int nodeSerialNumber, String nodeName)
	{
		fileWriter.format("v%d [label = \"%s\"];\n", nodeSerialNumber, nodeName);
	}

	public void logEdge(int fatherNodeSerialNumber, int sonNodeSerialNumber)
	{
		fileWriter.format("v%d -> v%d;\n", fatherNodeSerialNumber, sonNodeSerialNumber);
	}
	
	public void finalizeFile()
	{
		fileWriter.print("}\n");
		fileWriter.close();
	}
}
