package SymbolStack;

import java.util.*;
import java.io.*;
import TYPES.*;

public class SymbolStack
{

    private static class Symbol
    {
        String name;
        Type type;

        public Symbol(String name, Type type)
        {
            this.name = name;
            this.type = type;
        }
    }

    private static final Deque<Symbol> stack = new ArrayDeque<Symbol>();

	public static void enter(String name, Type t)
	{
        stack.push(new Symbol(name, t));
        updateGraphviz();
	}

	public static Type find(String name)
	{
        for (Symbol symbol : stack)
        {
            if (symbol.name.equals(name)) { return symbol.type; }
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

    public static Type findTypeName(String typeName)
	{
		for (Symbol symbol : stack)
        {
            if (symbol.type.name.equals(typeName)) { return symbol.type; }
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

	public static boolean isInScope(Type.Scope scopeType)
	{
		for (Symbol symbol : stack)
        {
            if (symbol.type == scopeType) { return true; }
        }
        return false;
	}

    public static void beginScope(Type.Scope scopeType)
	{
		enter(scopeType.name, scopeType);
        updateGraphviz();
	}

    public static void endScope()
	{
        while (!(stack.pop().type instanceof Type.Scope));
        updateGraphviz();
	}

    public static void Init()
	{
		// enter primitive types
		enter("int", Type.INT);
		enter("string", Type.STRING);

		// enter lib functions
		TypeFunc printIntFunc = new TypeFunc(Type.VOID, "PrintInt", new TypeList(Type.INT, null), null);
		TypeFunc printStringFunc = new TypeFunc(Type.VOID, "PrintString", new TypeList(Type.STRING, null), null);
		TypeFunc printTraceFunc = new TypeFunc(Type.VOID, "PrintTrace", null, null);
		enter("PrintInt", printIntFunc);
		enter("PrintString", printStringFunc);
		enter("PrintTrace", printTraceFunc);
	}

    private static final StringBuilder sb = new StringBuilder();
    private static int sbAppendCount = 0;

    private static void updateGraphviz()
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

    public static void toGraphviz() throws Exception
    {
        String dirPath = "./FOLDER_5_OUTPUT/";
		String fileName = "SymbolStackGraphviz.txt";
        PrintWriter writer = new PrintWriter(dirPath + fileName);

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
