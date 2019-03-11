import pcomp.*;
import java.io.*;


public class Main
{

    static public void main(String argv[])
    {
        try
        {
            Parser parser = new Parser(argv[0]);
            
            AST.Node ast = (AST.Node)parser.parse().value;
            // ast.toGraphviz("./FOLDER_5_OUTPUT/AST.txt");

            SymbolTable.init();
            ast.Semant();
            // SymbolTable.toGraphviz("./FOLDER_5_OUTPUT/SymbolTable.txt");

            IR.init();
            ast.toIR();
            IR.toMIPS();
            MIPS.toFile(argv[1]);

            RegAlloc.allocate(argv[1]);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
