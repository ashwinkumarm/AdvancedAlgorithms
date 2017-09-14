package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp3_q1_TopologicalOrdering;

import java.util.List;

import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Graph;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.GraphAlgorithm;

public class DFSFinishTimeOrderGraph extends GraphAlgorithm<DFSFinishTimeOrderGraph.DFSVertex> {
	public static final int INFINITY = Integer.MAX_VALUE;

	public static int componentNo, time;
	public static int topNum;

	// Class to store information about a vertex in this algorithm
	public static class DFSVertex {
		Graph.Vertex element;
		boolean seen;
		Graph.Vertex parent;
		int startTime;
		int componentNo;
		int finishTime;
		int top;


		DFSVertex(Graph.Vertex u) {
			element = u;
			seen = false;
			parent = null;
			componentNo = 0;
		}
	}



	public DFSFinishTimeOrderGraph(Graph g) {
		super(g);
		node = new DFSVertex[g.size()];
		// Create array for storing vertex properties
		for (Graph.Vertex u : g) {
			node[u.getName()] = new DFSVertex(u);
		}

		componentNo = 0;
		time = 0;
		topNum = g.size();
	}

	public void dfsVisit(Graph.Vertex u, List<Graph.Vertex> decFinList){

		DFSVertex bv = getVertex(u);
		bv.seen = true;
		bv.startTime = ++time;
		bv.componentNo = componentNo;

		for (Graph.Edge e : u) {
			Graph.Vertex v = e.otherEnd(u);
			if (!seen(v)) {
				setParent(u, v);
				dfsVisit(v,decFinList);
			}
		}
		bv.finishTime = ++time;
		bv.top = topNum--;
		decFinList.add(0, u);
	}


	void setParent(Graph.Vertex u, Graph.Vertex v){
		DFSVertex bv = getVertex(v);
		bv.parent = u;
	}

	public boolean seen(Graph.Vertex u) {
		return getVertex(u).seen;
	}

	Graph.Vertex getParent(Graph.Vertex u) {
		return getVertex(u).parent;
	}
}
