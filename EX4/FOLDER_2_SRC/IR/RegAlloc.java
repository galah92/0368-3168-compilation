package pcomp;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.regex.*;


public class RegAlloc {

    public static void allocate(String outputPath) throws IOException {

        List<TmpReg> tempRegs = new ArrayList<TmpReg>();
        Pattern p = Pattern.compile("(,|\\s|\\(|\\()");

        int numTempRegs = 0;
        while (true) {
            String line = null;
            BufferedReader bufferedReader = new BufferedReader(new FileReader(outputPath));
            int start = 0;
            int end = 0;
            int i = 1;
            while ((line = bufferedReader.readLine()) != null) {
                for (String splited : p.split(line)) {
                    String s = splited;
                    if (splited.contains(")")) { s = splited.substring(0, splited.indexOf(")") ); }
                    if (s.equals("Temp_" + numTempRegs)) {
                        if (start == 0) { start = i; }
                        end = i;
                        break;
                    }
                }
                i++;
            }
            bufferedReader.close();
            if (start == 0 && end == 0) { break; }  // break if no more temp registers
            tempRegs.add(new TmpReg(numTempRegs, start, end));
            numTempRegs++;
        }

        Graph interferenceGraph = new Graph(tempRegs.size());
        for (TmpReg reg1 : tempRegs) {
            for (TmpReg reg2 : tempRegs) {
                if (reg1.isInterfering(reg2) && reg1.id != reg2.id) {
                    interferenceGraph.addEdge(reg1.id, reg2.id);
                }
            }
        }
        
        // calculate the coloring
        HashMap<Integer, Integer> colors = interferenceGraph.doColor();

        // apply the coloring
        String text = new String(Files.readAllBytes(Paths.get(outputPath)));
        for (int i = numTempRegs; i >= 0; i--) {
            Pattern pat = Pattern.compile("Temp_" + i);
            text = pat.matcher(text).replaceAll("\\$t" + colors.get(i));
        }
        Files.write(Paths.get(outputPath), text.getBytes());
    }

}


class TmpReg {

    int id;
    int start;
    int end;

    public TmpReg(int id, int start, int end) {
        this.id = id;
        this.start = start;
        this.end = end;
    }

    public boolean isInterfering(TmpReg t) {
    int x1 = t.start;
    int x2 = t.end;
    int y1 = this.start;
    int y2 = this.end;
    return (x1 >= y1 && x1 <= y2) || (x2 >= y1 && x2 <= y2) ||
           (y1 >= x1 && y1 <= x2) || (y2 >= x1 && y2 <= x2);
    }

}


class Graph
{

	private final int numVertices; // number of vertices
	private LinkedList<Integer> adj[];  // adjacency List

	Graph(int numVertices)
	{
		this.numVertices = numVertices;
        adj = new LinkedList[numVertices];
		for (int i = 0; i < numVertices; i++) { adj[i] = new LinkedList(); }
	}

	void addEdge(int v, int w)
	{
		adj[v].add(w);
		adj[w].add(v); // graph is undirected
	}

	HashMap<Integer, Integer> doColor()
	{
		int result[] = new int[numVertices];
		Arrays.fill(result, -1);  // initialize all vertices as unassigned
		result[0] = 0;  // assign the first color to first vertex

		// A temporary array to store the available colors. False
		// value of available[color] would mean that the color is
		// assigned to one of its adjacent vertices
		boolean available[] = new boolean[numVertices];
		Arrays.fill(available, true);  // initially, all colors are available

		for (int u = 1; u < numVertices; u++)  // assign colors to remaining numVertices-1 vertices
		{
			// process all adjacent vertices and flag their colors as unavailable
            for (Integer v : adj[u])
            {
                if (result[v] != -1) { available[result[v]] = false; }
            }
			int color;  // find the first available color
			for (color = 0; color < numVertices; color++) { if (available[color]) break; }
			result[u] = color; // assign the found color
			Arrays.fill(available, true);  // reset the values back to true for the next iteration
		}

        HashMap<Integer, Integer> colors = new HashMap<Integer, Integer>();
		for (int u = 0; u < numVertices; u++) { colors.put(u, result[u]); }
		return colors;
	}

}
