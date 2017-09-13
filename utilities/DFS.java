package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities;

import java.util.LinkedList;
import java.util.Queue;

public class DFS extends GraphAlgorithm<DFS.DFSVertex> {
	public static final int INFINITY = Integer.MAX_VALUE;

	// Class to store information about a vertex in this algorithm
	static class DFSVertex {
		boolean seen;
		Graph.Vertex parent;
		int distance; // distance of vertex from source

		DFSVertex(Graph.Vertex u) {
			seen = false;
			parent = null;
			distance = INFINITY;
		}
	}

	Graph.Vertex src;

	public DFS(Graph g, Graph.Vertex src) {
		super(g);
		this.src = src;
		node = new DFSVertex[g.size()];
		// Create array for storing vertex properties
		for (Graph.Vertex u : g) {
			node[u.getName()] = new DFSVertex(u);
		}
		// Set source to be at distance 0
		getVertex(src).distance = 0;
	}

	// reinitialize allows running DFS many times, with different sources
	void reinitialize(Graph.Vertex newSource) {
		src = newSource;
		for (Graph.Vertex u : g) {
			DFSVertex bu = getVertex(u);
			bu.seen = false;
			bu.parent = null;
			bu.distance = INFINITY;
		}
		getVertex(src).distance = 0;
	}

	void dfs() {
		Queue<Graph.Vertex> q = new LinkedList<>();
		q.add(src);
		while (!q.isEmpty()) {
			Graph.Vertex u = q.remove();
			for (Graph.Edge e : u) {
				Graph.Vertex v = e.otherEnd(u);
				if (!seen(v)) {
					visit(u, v);
					q.add(v);
				}
			}
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