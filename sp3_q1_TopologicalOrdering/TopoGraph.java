package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp3_q1_TopologicalOrdering;

import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Graph;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.GraphAlgorithm;

public class TopoGraph extends GraphAlgorithm<TopoGraph.TopoVertex> {

	class TopoVertex {
		Graph.Vertex element;
		Graph.Vertex parent;
		int inDegree, top,cno,disTime, finishTime;
		boolean seen;
		
		TopoVertex(Graph.Vertex u) {
			element = u;
			top = -1;
			seen = false;
		}
	}

	public TopoGraph(Graph g) {
		super(g);
		node = new TopoVertex[g.size()];
		for (Graph.Vertex u : g) {
			node[u.getName()] = new TopoVertex(u);
		}
	}

	int reduceInDegree(Graph.Vertex u) {
		return --getVertex(u).inDegree;
	}
}
