package pcomp;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.regex.*;


public class RegAlloc {

    public static void allocate(String outputPath) throws IOException {

        List<TmpReg> tempRegs = new ArrayList<TmpReg>();
        Pattern p = Pattern.compile("(,|\\s|\\(|\\()");

        int tmp_num = 10000;
        int maxTemp = 0;
        for (int tmp_index = 0; tmp_index <= tmp_num; tmp_index++) {
            String line = null;
            BufferedReader bufferedReader = new BufferedReader(new FileReader(outputPath));
            int start = 0;
            int end = 0;
            int i = 1;
            while ((line = bufferedReader.readLine()) != null) {
                for (String splited : p.split(line)) {
                    String s = splited;
                    if (splited.contains(")")) { s = splited.substring(0, splited.indexOf(")") ); }
                    if (s.equals("Temp_" + tmp_index)) {
                        if (start == 0) { start = i++; }
                        end = i;
                        break;
                    }
                }
                i++;
            }
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(Arrays.toString(p.split(line)));
                for (String splited : p.split(line)) {
                    String s = splited;
                    if (splited.contains(")")) { s = splited.substring(0, splited.indexOf(")")); }
                    if (s.equals("Temp_" + tmp_index)) { end = i; }
                }
                i++;
            }
            bufferedReader.close();  // close the BufferedReader when we're done
            if (start == 0 && end == 0)
            {
                maxTemp = tmp_index;
                break;
            }
            tempRegs.add(new TmpReg(tmp_index, start, end));
        }

        for (TmpReg t : tempRegs) { t.end--; }  // end parameter is too large by 1, fix:

        Graph interferenceGraph = new Graph(tempRegs.size());
        for (TmpReg t : tempRegs) {
            for (TmpReg k : tempRegs) {
                if (k.isInterfering(t) && k.id != t.id) {
                    k.addInterferance(t);
                    interferenceGraph.addEdge(t.id, k.id);
                }
            }
        }
        
        // calculate the coloring
        HashMap<Integer, Integer> colors = interferenceGraph.doColor();

        // apply the coloring
        String text = new String(Files.readAllBytes(Paths.get(outputPath)));
        for (int i = maxTemp; i >= 0; i--) {
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
    HashSet<Integer> interferences = new HashSet<Integer>();

    public TmpReg(int id, int start, int end) {
        this.id = id;
        this.start = start;
        this.end = end;
    }

    public void addInterferance(TmpReg t) { interferences.add(t.id); }

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

	Graph(int v)
	{
		numVertices = v;
        adj = new LinkedList[v];
		for (int i = 0; i < v; i++) { adj[i] = new LinkedList(); }
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
		// value of available[cr] would mean that the color cr is
		// assigned to one of its adjacent vertices
		boolean available[] = new boolean[numVertices];
		Arrays.fill(available, true);  // initially, all colors are available

		for (int u = 1; u < numVertices; u++)  // assign colors to remaining numVertices-1 vertices
		{
			// process all adjacent vertices and flag their colors as unavailable
			Iterator<Integer> it = adj[u].iterator();
			while (it.hasNext())
			{
				int i = it.next();
				if (result[i] != -1) { available[result[i]] = false; }
			}

			int cr;  // find the first available color
			for (cr = 0; cr < numVertices; cr++) { if (available[cr]) break; }

			result[u] = cr; // assign the found color
			Arrays.fill(available, true);  // reset the values back to true for the next iteration
		}

        HashMap<Integer, Integer> colors = new HashMap<Integer, Integer>();
		for (int u = 0; u < numVertices; u++) { colors.put(u, result[u]); }
		return colors;
	}

}
