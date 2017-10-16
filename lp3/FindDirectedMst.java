package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.lp3;

import java.util.HashSet;
import java.util.Iterator;

import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.lp3.BFS.BFSVertex;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.lp3.DMSTGraph.DMSTEdge;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.lp3.DMSTGraph.DMSTVertex;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Graph;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Graph.Edge;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Graph.Vertex;


// Very inefficient codes. Lots of iterations of nodes. Should optimize later. 
public class FindDirectedMst {
	
	//Should think of a better way - Used for disabling the edges of DMSTadj. DMSTadj is used in running bfs.
	HashSet<Integer> set = new HashSet<Integer>();
	HashSet<Integer> cycle = new HashSet<Integer>();
	
	public Graph minMst(DMSTGraph g, Vertex start) {
		DMSTGraph transformedGraph = transformWeights(g, start);
		DMSTGraph subGraph = findSubGraph(transformedGraph);
		DMSTVertex v = isMST(subGraph, start);
		if(v == null){
			return g;
		}		
		cycle.add(v.getName());
		StringBuilder sb = new StringBuilder();
		sb.append(v.getName() );
		String cycle = detectCycle(sb,v);
		return g;
	}
	
	public String detectCycle(StringBuilder sb, DMSTVertex v) {
		while (v != null) {
			Iterator<Edge> it = v.revIterator();
			while (it.hasNext()) {
				Edge e = it.next();
				if (!cycle.contains(e.from.getName())) {
					cycle.add(e.from.getName());
					sb.append(e.from.getName());
					v = (DMSTVertex) e.from;
				} else {
					String s = sb.toString();
					int i = s.indexOf(Integer.toString(e.from.getName()));
					sb = new StringBuilder(s.substring(i, s.length()));
					return s.substring(i, s.length());
				}
			}
		}
		return null;
	}
		
	public DMSTVertex isMST(DMSTGraph g, Vertex start){
		BFS bfs = new BFS(g,g.getDMSTVertex(start.getName() +1));
		bfs.bfs();
		BFSVertex[] vertexArr = bfs.node;
		// should be changed definitely. This is just a work around.
		int name = 0;
		for(BFSVertex vertex : vertexArr){
			name++;
			if(!vertex.seen){
				return g.getDMSTVertex(name);
			}
		}
		return null;
	}
	
	private DMSTGraph findSubGraph(DMSTGraph g) {
		DMSTVertex[] vertexArr = g.getDMSTVertexArray();
		for (DMSTVertex vertex : vertexArr) {
			if (vertex != null) {
				Iterator<DMSTEdge> it = vertex.DMSTadj.iterator();
				while (it.hasNext()) {
					DMSTEdge edge = it.next();
					if (set.contains(edge.getName())) {
						edge.disabled();
					}
				}
			}
		}
		return g;
	}

	public DMSTGraph transformWeights(DMSTGraph g, Vertex start) {
		DMSTVertex[] vertexArray = g.getDMSTVertexArray();
		for (DMSTVertex vertex : vertexArray) {
			// try to remove this if condition
			if (vertex != null && vertex.getName() != start.getName() ) {
				for (DMSTEdge e : vertex.DMSTrevadj) { 
					e.weight = e.weight - vertex.minEdge;
					if(e.weight != 0){
						e.disabled();
						set.add(e.getName());
						
					}
				}
			}
		}
		return g;
	}

}
