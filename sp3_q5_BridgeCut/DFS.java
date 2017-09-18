package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp3_q5_BridgeCut;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Graph;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.GraphAlgorithm;

public class DFS extends GraphAlgorithm<DFS.DFSVertex> {
	public static final int INFINITY = Integer.MAX_VALUE;
	int time = 0;
	Queue<Graph.Vertex> q = new LinkedList<>();

	// Class to store information about a vertex in this algorithm
	static class DFSVertex {
		boolean seen, cutVertex;
		Graph.Vertex parent;
		int distance;// distance of vertex from source
		int dis, low, childrenCount;

		DFSVertex(Graph.Vertex u) {
			seen = false;
			parent = null;
			cutVertex = false;
			distance = INFINITY;
		}
	}

	Graph.Vertex src;

	public DFS(Graph g) {
		super(g);
		// this.src = src;
		node = new DFSVertex[g.size()];
		// Create array for storing vertex properties
		for (Graph.Vertex u : g) {
			node[u.getName()] = new DFSVertex(u);
		}
	}

	// reinitialize allows running DFS many times, with different sources
	void reinitialize(Graph.Vertex newSource) {
		src = newSource;
		for (Graph.Vertex u : g) {
			DFSVertex bu = getVertex(u);
			bu.seen = false;
			bu.parent = null;
			bu.distance = INFINITY;
			bu.childrenCount = 0;
		}
		getVertex(src).distance = 0;
	}

	void dfs(Graph.Vertex u, List<Graph.Edge> bridgeList) {
		DFSVertex du = getVertex(u);
		du.seen = true;
		du.dis = du.low = ++time;
		DFSVertex dv = null;
		for (Graph.Edge e : u) {
			Graph.Vertex v = e.otherEnd(u);
			dv = getVertex(v);
			if (!seen(v)) {
				visit(u, v);
				du.childrenCount++;
				dfs(v, bridgeList);

				du.low = Math.min(du.low, dv.low);

				if (dv.low >= dv.dis)
					bridgeList.add(e);
			}

			else if (v != getParent(u)) { // Back Edge
				du.low = Math.min(du.low, dv.dis);
			}
		}
		if ((du.parent != null && dv.low >= du.dis) || (du.parent == null && du.childrenCount >= 2)) {
			du.cutVertex = true;
		}
	}

	boolean seen(Graph.Vertex u) {
		return getVertex(u).seen;
	}

	Graph.Vertex getParent(Graph.Vertex u) {
		return getVertex(u).parent;
	}

	int distance(Graph.Vertex u) {
		return getVertex(u).distance;
	}

	// Visit a node v from u
	void visit(Graph.Vertex u, Graph.Vertex v) {
		DFSVertex bv = getVertex(v);
		bv.seen = true;
		bv.parent = u;
		bv.distance = distance(u) + 1;
	}
}