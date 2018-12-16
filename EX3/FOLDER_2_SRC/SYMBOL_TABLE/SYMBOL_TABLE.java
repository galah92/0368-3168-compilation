package SYMBOL_TABLE;
import java.io.PrintWriter;
import TYPES.*;

public class SYMBOL_TABLE
{
	private int hashArraySize = 13;
	
	private SYMBOL_TABLE_ENTRY[] table = new SYMBOL_TABLE_ENTRY[hashArraySize];
	private SYMBOL_TABLE_ENTRY top;
	private int top_index = 0;
	
	private int hash(String s)
	{
		if (s.charAt(0) == 'l') {return 1;}
		if (s.charAt(0) == 'm') {return 1;}
		if (s.charAt(0) == 'r') {return 3;}
		if (s.charAt(0) == 'i') {return 6;}
		if (s.charAt(0) == 'd') {return 6;}
		if (s.charAt(0) == 'k') {return 6;}
		if (s.charAt(0) == 'f') {return 6;}
		if (s.charAt(0) == 'S') {return 6;}
		return 12;
	}

	public void enter(String name,TYPE t)
	{
		int hashValue = hash(name);
		SYMBOL_TABLE_ENTRY next = table[hashValue];
		table[hashValue] = top = new SYMBOL_TABLE_ENTRY(name,
													    t,
														hashValue,
														next,
														top,
														top_index++);
		PrintMe();
	}

	public TYPE find(String name)
	{
		SYMBOL_TABLE_ENTRY e;
		
		for (e = table[hash(name)]; e != null; e = e.next)
		{
			if (name.equals(e.name)) return e.type;
		}
		
		return null;
	}

	public TYPE findInScope(String name)
	{
		SYMBOL_TABLE_ENTRY e = top;
		int i = top_index; // inner scope index
		while (e != null && e.name != "SCOPE-BOUNDARY")
		{
			i = i - 1;
			e = e.prevtop;
		}
		for (e = table[hash(name)]; e != null && e.prevtop_index > i; e = e.next)
		{
			if (name.equals(e.name)) return e.type;
		}
		return null;
	}

	public TYPE_FUNCTION findFunc()
	{
		SYMBOL_TABLE_ENTRY e = top;
		while (e != null && !(e.type instanceof TYPE_FUNCTION)) { e = e.prevtop; }
		return e != null ? (TYPE_FUNCTION)e.type : null;
	}

	public boolean isGlobalScope()
	{
		SYMBOL_TABLE_ENTRY e = top;
		while (e != null && e.name != "SCOPE-BOUNDARY") { e = e.prevtop; }
		return e == null; // no SCOPE-BOUNDARY in table
	}

	public void beginScope()
	{
		enter("SCOPE-BOUNDARY", new TYPE_FOR_SCOPE_BOUNDARIES("NONE"));
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
		String filename=String.format("SYMBOL_TABLE_%d_IN_GRAPHVIZ_DOT_FORMAT.txt",n++);

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
				for (SYMBOL_TABLE_ENTRY it=table[i];it!=null;it=it.next)
				{
					/* [4b] Print entry(i,it) node */
					fileWriter.format("node_%d_%d ",i,j);
					fileWriter.format("[label=\"<f0>%s|<f1>%s|<f2>prevtop=%d|<f3>next\"];\n",
						it.name,
						it.type.name,
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
	
	private static SYMBOL_TABLE instance = null;

	protected SYMBOL_TABLE() {}

	public static SYMBOL_TABLE getInstance()
	{
		if (instance == null)
		{
			instance = new SYMBOL_TABLE();

			// enter primitive types
			instance.enter("int", TYPE_INT.getInstance());
			instance.enter("string", TYPE_STRING.getInstance());

			// TODO: this is bad! as one can create a variable of type "void"
			instance.enter("void", TYPE_VOID.getInstance());

			// enter lib functions
			TYPE_FUNCTION printIntFunc = new TYPE_FUNCTION(TYPE_VOID.getInstance(), "PrintInt", new TYPE_LIST(TYPE_INT.getInstance(), null));
			TYPE_FUNCTION printStringFunc = new TYPE_FUNCTION(TYPE_VOID.getInstance(), "PrintString", new TYPE_LIST(TYPE_INT.getInstance(), null));
			TYPE_FUNCTION printTraceFunc = new TYPE_FUNCTION(TYPE_VOID.getInstance(), "PrintTrace", null);
			instance.enter("PrintInt", printIntFunc);
			instance.enter("PrintString", printStringFunc);
			instance.enter("PrintTrace", printTraceFunc);
		}
		return instance;
	}
}
