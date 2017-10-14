package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.lp3;

import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.lp3.DMSTGraph.DMSTVertex;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Graph;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Graph.Edge;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Graph.Vertex;

public class FindDirectedMst {
	
	
	public Graph minMst(DMSTGraph g, Vertex start) {
		g = transformWeights(g, start);
		g = findSubGraph(g);
		return g;
	}
	
	private DMSTGraph findSubGraph(DMSTGraph g) {
	
		return null;
	}

	public DMSTGraph transformWeights(DMSTGraph g, Vertex start) {
		DMSTVertex[] vertexArray = g.getDMSTVertexArray();
		for (DMSTVertex vertex : vertexArray) {
			// try to remove this if condition
			if (vertex.getName() != start.getName()) {
				for (Edge e : vertex.revAdj) {
					e.weight = e.weight - vertex.minEdge;
				}
			}
		}
		return g;
	}

}
