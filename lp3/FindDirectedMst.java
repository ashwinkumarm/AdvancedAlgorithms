package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.lp3;

import java.util.Iterator;

import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.lp3.BFS.BFSVertex;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.lp3.DMSTGraph.DMSTEdge;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.lp3.DMSTGraph.DMSTVertex;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Graph;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Graph.Edge;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Graph.Vertex;


// Very inefficient codes. Lots of iterations of nodes. Should optimize later. 
public class FindDirectedMst {
	
	
	public Graph minMst(DMSTGraph g, Vertex start) {
		DMSTGraph transformedGraph = transformWeights(g, start);
		DMSTGraph subGraph = findSubGraph(transformedGraph);
		DMSTVertex v = isMST(subGraph, start);
		if(v == null){
			return g;
		}		
		
		return g;
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
				Iterator<DMSTEdge> it = vertex.xadj.iterator();
				while (it.hasNext()) {
					DMSTEdge edge = it.next();
					if (edge.weight != 0) {
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
				for (Edge e : vertex.xadj) {
					e.weight = e.weight - vertex.minEdge;
				}
			}
		}
		return g;
	}

}
