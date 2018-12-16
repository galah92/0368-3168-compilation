package SymbolTable;
import java.io.PrintWriter;
import TYPES.*;

public class SymbolTable
{
	private class SymbolTableEntry
	{
		int index;
		public String name;
		public Type Type;
		public SymbolTableEntry prevtop;
		public SymbolTableEntry next;
		public int prevtop_index;
		
		public SymbolTableEntry(String name, Type Type, int index, SymbolTableEntry next, SymbolTableEntry prevtop, int prevtop_index)
		{
			this.index = index;
			this.name = name;
			this.Type = Type;
			this.next = next;
			this.prevtop = prevtop;
			this.prevtop_index = prevtop_index;
		}
	}

	private int hashArraySize = 13;
	
	private SymbolTableEntry[] table = new SymbolTableEntry[hashArraySize];
	private SymbolTableEntry top;
	private int top_index = 0;
	
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
		table[hashVal] = top = new SymbolTableEntry(name,
													t,
													hashVal,
													table[hashVal],
													top,
													top_index++);
		PrintMe();
	}

	public Type find(String name)
	{
		SymbolTableEntry e;
		
		for (e = table[hash(name)]; e != null; e = e.next)
		{
			if (name.equals(e.name)) return e.Type;
		}
		
		return null;
	}

	public Type findInScope(String name)
	{
		SymbolTableEntry e = top;
		int i = top_index; // inner scope index
		while (e != null && e.name != "SCOPE-BOUNDARY")
		{
			i = i - 1;
			e = e.prevtop;
		}
		for (e = table[hash(name)]; e != null && e.prevtop_index > i; e = e.next)
		{
			if (name.equals(e.name)) return e.Type;
		}
		return null;
	}

	public TypeFunc findFunc()
	{
		SymbolTableEntry e = top;
		while (e != null && !(e.Type instanceof TypeFunc)) { e = e.prevtop; }
		return e != null ? (TypeFunc)e.Type : null;
	}

	public boolean isGlobalScope()
	{
		SymbolTableEntry e = top;
		while (e != null && e.name != "SCOPE-BOUNDARY") { e = e.prevtop; }
		return e == null; // no SCOPE-BOUNDARY in table
	}

	public void beginScope()
	{
		enter("SCOPE-BOUNDARY", new TypeScope("NONE"));
		PrintMe();
	}

	public void endScope()
	{
		/* Pop elements from the symbol table stack until a SCOPE-BOUNDARY is hit */
		while (top.name != "SCOPE-BOUNDARY")
		{
			table[top.index] = top.next;
			top_index = top_index-1;
			top = top.prevtop;
		}
		/* Pop the SCOPE-BOUNDARY sign itself */		
		table[top.index] = top.next;
		top_index = top_index-1;
		top = top.prevtop;

		PrintMe();
	}
	
	public static int n = 0;
	
	public void PrintMe()
	{
		int i=0;
		int j=0;
		String dirname="./FOLDER_5_OUTPUT/";
		String filename=String.format("SymbolTable_%d_IN_GRAPHVIZ_DOT_FORMAT.txt",n++);

		try
		{
			/* [1] Open Graphviz text file for writing */
			PrintWriter fileWriter = new PrintWriter(dirname+filename);

			/* [2] Write Graphviz dot prolog */
			fileWriter.print("digraph structs {\n");
			fileWriter.print("rankdir = LR\n");
			fileWriter.print("node [shape=record];\n");

			/* [3] Write Hash Table Itself */
			fileWriter.print("hashTable [label=\"");
			for (i=0;i<hashArraySize-1;i++) { fileWriter.format("<f%d>\n%d\n|",i,i); }
			fileWriter.format("<f%d>\n%d\n\"];\n",hashArraySize-1,hashArraySize-1);
		
			/* [4] Loop over hash table array and print all linked lists per array cell */
			for (i=0;i<hashArraySize;i++)
			{
				if (table[i] != null)
				{
					/* [4a] Print hash table array[i] -> entry(i,0) edge */
					fileWriter.format("hashTable:f%d -> node_%d_0:f0;\n",i,i);
				}
				j=0;
				for (SymbolTableEntry it=table[i];it!=null;it=it.next)
				{
					/* [4b] Print entry(i,it) node */
					fileWriter.format("node_%d_%d ",i,j);
					fileWriter.format("[label=\"<f0>%s|<f1>%s|<f2>prevtop=%d|<f3>next\"];\n",
						it.name,
						it.Type.name,
						it.prevtop_index);

					if (it.next != null)
					{
						/* [4c] Print entry(i,it) -> entry(i,it.next) edge */
						fileWriter.format(
							"node_%d_%d -> node_%d_%d [style=invis,weight=10];\n",
							i,j,i,j+1);
						fileWriter.format(
							"node_%d_%d:f3 -> node_%d_%d:f0;\n",
							i,j,i,j+1);
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
