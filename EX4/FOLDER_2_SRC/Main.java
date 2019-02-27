import pcomp.*;
import java.io.*;


public class Main
{

    static public void main(String argv[])
    {
        PrintWriter writer = null;
        try
        {
            writer = new PrintWriter(argv[1]);
            Parser parser = new Parser(argv[0], writer);
            
            AST.Node ast = (AST.Node)parser.parse().value;
            ast.toGraphviz("./FOLDER_5_OUTPUT/AST.txt");

            SymbolTable.init();
            ast.Semant();
            SymbolTable.toGraphviz("./FOLDER_5_OUTPUT/SymbolTable.txt");

            ast.toIR();
            IR.toMIPS();
            MIPSGen.toFile("./FOLDER_5_OUTPUT/MIPS.txt");
            
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
