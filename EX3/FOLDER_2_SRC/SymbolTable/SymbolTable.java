package SymbolTable;
import java.io.PrintWriter;
import TYPES.*;

public class SymbolTable
{
	private class Entry
	{
		int index;
		public String name;
		public Type type;
		public Entry prevtop;
		public Entry next;
		public int prevtop_index;
		
		public Entry(String name, Type Type, int index, Entry next, Entry prevtop, int prevtop_index)
		{
			this.index = index;
			this.name = name;
			this.type = Type;
			this.next = next;
			this.prevtop = prevtop;
			this.prevtop_index = prevtop_index;
		}
	}

	private static final int TABLE_SIZE = 13;
	
	private Entry[] table = new Entry[TABLE_SIZE];
	private Entry top;
	private int numEntries = 0;
	
	private int hash(String s)
	{
		if (s.charAt(0) == 'l') { return 1; }
		if (s.charAt(0) == 'm') { return 1; }
		if (s.charAt(0) == 'r') { return 3; }
		if (s.charAt(0) == 'i') { return 6; }
		if (s.charAt(0) == 'd') { return 6; }
		if (s.charAt(0) == 'k') { return 6; }
		if (s.charAt(0) == 'f') { return 6; }
		if (s.charAt(0) == 'S') { return 6; }
		return 12;
	}

	public void enter(String name, Type t)
	{
		int hashVal = hash(name);
		table[hashVal] = top = new Entry(name, t, hashVal, table[hashVal], top, numEntries++);
		PrintMe();
	}

	public Type find(String name)
	{
		for (Entry e = table[hash(name)]; e != null; e = e.next)
		{
			if (name.equals(e.name)) return e.type;
		}	
		return null;
	}

	public Type findInScope(String name)
	{
		Entry e = top;
		int i = numEntries; // inner scope index
		while (e != null && !(e.type instanceof TypeScope))
		{
			i--;
			e = e.prevtop;
		}
		for (e = table[hash(name)]; e != null && e.prevtop_index > i; e = e.next)
		{
			if (name.equals(e.name)) return e.type;
		}
		return null;
	}

	public TypeFunc findFunc()
	{
		Entry e = top;
		while (e != null && !(e.type instanceof TypeFunc)) { e = e.prevtop; }
		return e != null ? (TypeFunc)e.type : null;
	}

	public boolean isGlobalScope()
	{
		Entry e = top;
		while (e != null && !(e.type instanceof TypeScope)) { e = e.prevtop; }
		return e == null; // no TypeScope in table
	}

	public void beginScope()
	{
		enter("SCOPE-BOUNDARY", new TypeScope("NONE"));
		PrintMe();
	}

	public void endScope()
	{
		// pop until a TypeScope is hit
		while (!(top.type instanceof TypeScope))
		{
			table[top.index] = top.next;
			numEntries--;
			top = top.prevtop;
		}
		// pop TypeScope itself
		table[top.index] = top.next;
		numEntries--;
		top = top.prevtop;

		PrintMe();
	}
	
	public static int printCount = 0;
	
	public void PrintMe()
	{
		String dirname = "./FOLDER_5_OUTPUT/";
		String filename = String.format("SymbolTable_%d_IN_GRAPHVIZ_DOT_FORMAT.txt", printCount++);
		try
		{
			PrintWriter fileWriter = new PrintWriter(dirname + filename);
			fileWriter.print("digraph structs {\n");
			fileWriter.print("rankdir = LR\n");
			fileWriter.print("node [shape=record];\n");

			fileWriter.print("hashTable [label=\"");
			for (int i = 0; i < TABLE_SIZE - 1; i++) { fileWriter.format("<f%d>\n%d\n|", i, i); }
			fileWriter.format("<f%d>\n%d\n\"];\n", TABLE_SIZE - 1, TABLE_SIZE - 1);
		
			for (int i = 0; i < TABLE_SIZE; i++)
			{
				if (table[i] != null) { fileWriter.format("hashTable:f%d -> node_%d_0:f0;\n", i, i); }
				int j = 0;
				for (Entry it = table[i]; it != null; it = it.next)
				{
					fileWriter.format("node_%d_%d ",i,j);
					fileWriter.format("[label=\"<f0>%s|<f1>%s|<f2>prevtop=%d|<f3>next\"];\n", it.name, it.type.name, it.prevtop_index);

					if (it.next != null)
					{
						fileWriter.format("node_%d_%d -> node_%d_%d [style=invis,weight=10];\n", i, j, i, j+1);
						fileWriter.format("node_%d_%d:f3 -> node_%d_%d:f0;\n", i, j, i, j+1);
					}
					j++;
				}
			}
			fileWriter.print("}\n");
			fileWriter.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}		
	}
	
	private static SymbolTable instance = null;

	protected SymbolTable() {}

	public static SymbolTable getInstance()
	{
		if (instance == null)
		{
			instance = new SymbolTable();

			// enter primitive types
			instance.enter("int", TypeInt.getInstance());
			instance.enter("string", TypeString.getInstance());

			// TODO: this is bad! as one can create a variable of Type "void"
			instance.enter("void", TypeVoid.getInstance());

			// enter lib functions
			TypeFunc printIntFunc = new TypeFunc(TypeVoid.getInstance(), "PrintInt", new TypeList(TypeInt.getInstance(), null));
			TypeFunc printStringFunc = new TypeFunc(TypeVoid.getInstance(), "PrintString", new TypeList(TypeInt.getInstance(), null));
			TypeFunc printTraceFunc = new TypeFunc(TypeVoid.getInstance(), "PrintTrace", null);
			instance.enter("PrintInt", printIntFunc);
			instance.enter("PrintString", printStringFunc);
			instance.enter("PrintTrace", printTraceFunc);
		}
		return instance;
	}
}
