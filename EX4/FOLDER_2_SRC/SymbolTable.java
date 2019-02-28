package pcomp;

import java.util.*;
import java.io.*;


public class SymbolTable
{

    private static final Deque<Symbol> stack = new ArrayDeque<Symbol>();

	public static void enter(String name, Type t)
	{
        stack.push(new Symbol(name, t));
        logGraphviz();
	}

	public static Type find(String name)
	{
        for (Symbol symbol : stack)
        {
            if (symbol.name.equals(name)) { return symbol.type; }
        }
        return null;
    }

    public static String findName(Type type)
	{
        for (Symbol symbol : stack)
        {
            if (symbol.type == type) { return symbol.name; }
        }
        return null;
    }

    public static TypeFunc findFunc()
    {
        for (Symbol symbol : stack)
        {
            if (symbol.type instanceof TypeFunc) { return (TypeFunc)symbol.type; }
        }
        return null;
    }

    public static TypeClass findClass()
    {
        for (Symbol symbol : stack)
        {
            if (symbol.type instanceof TypeClass) { return (TypeClass)symbol.type; }
        }
        return null;
    }

    public static Type findInScope(String name)
	{
		for (Symbol symbol : stack)
        {
            if (symbol.type instanceof Type.Scope) { break; }
            if (symbol.name.equals(name)) { return symbol.type; }
        }
        return null;
	}

    public static boolean isGlobalScope()
	{
		for (Symbol symbol : stack)
        {
            if (symbol.type instanceof Type.Scope) { return false; }
        }
        return true;
	}

    public static boolean isScope(String scopeName)
	{
		for (Symbol symbol : stack)
        {
            if (scopeName.equals(symbol.name)) { return true; }
        }
        return false;
	}

    public static void beginScope(Type.Scope scopeType)
	{
		enter(scopeType.name, scopeType);
        logGraphviz();
	}

    public static void endScope()
	{
        while (!(stack.pop().type instanceof Type.Scope));
        logGraphviz();
	}

    public static void init()
	{
		// enter primitive types
		enter(Type.INT.name, Type.INT);
		enter(Type.STRING.name, Type.STRING);

		// enter lib functions
		TypeFunc printIntFunc = new TypeFunc(Type.VOID);
        printIntFunc.params.add(new Symbol("n", Type.INT));
		enter("PrintInt", printIntFunc);
		TypeFunc printStringFunc = new TypeFunc(Type.VOID);
        printStringFunc.params.add(new Symbol("s", Type.STRING));
		enter("PrintString", printStringFunc);
		TypeFunc printTraceFunc = new TypeFunc(Type.VOID);
		enter("PrintTrace", printTraceFunc);
	}

    public static void printDebug()
    {
        for (Symbol symbol : stack)
        {
            System.out.printf("[%s] %s\n", symbol.type.name, symbol.name);
        }
    }

    private static final StringBuilder sb = new StringBuilder();
    private static int sbAppendCount = 0;

    private static void logGraphviz()
    {
        sb.append(String.format("node_%d [label=\"<head>%d", sbAppendCount, sbAppendCount++));
        Iterator<Symbol> it = stack.descendingIterator();
        while (it.hasNext())
        {
            Symbol sym = it.next();
            sb.append(String.format(" | [%s] %s \\l", sym.type.name, sym.name));
        }
        sb.append("\"];\n");
    }

    public static void toGraphviz(String outFile) throws Exception
    {
        PrintWriter writer = new PrintWriter(outFile);
        writer.println("digraph structs {");
        writer.println("rankdir = LR");
        writer.println("node [shape=record];");
        writer.println();
        for (int i = 0; i < sbAppendCount - 1; i++)
        {
            sb.append(String.format("node_%d:head -> node_%d:head\n", i, i + 1));
        }
        writer.println(sb.toString());
        writer.println("}");
        writer.close();
    }

}
