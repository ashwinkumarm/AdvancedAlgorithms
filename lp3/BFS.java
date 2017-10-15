package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.lp3;

/** Breadth-first search
 *  @author rbk
 *  Version 1.0: 2017/09/08
 */

import java.util.Queue;

import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.lp3.DMSTGraph.DMSTEdge;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.lp3.DMSTGraph.DMSTVertex;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Graph;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.GraphAlgorithm;

import java.util.Iterator;
import java.util.LinkedList;

public class BFS extends GraphAlgorithm<BFS.BFSVertex> {
	public static final int INFINITY = Integer.MAX_VALUE;

	// Class to store information about a vertex in this algorithm
	static class BFSVertex {
		boolean seen;
		Graph.Vertex parent;
		int distance; // distance of vertex from source

		BFSVertex(Graph.Vertex u) {
			seen = false;
			parent = null;
			distance = INFINITY;
		}
	}

	DMSTGraph.DMSTVertex src;

	public BFS(DMSTGraph g, DMSTGraph.DMSTVertex src) {
		super(g);
		this.src = src;
		node = new BFSVertex[g.size()];
		// Create array for storing vertex properties
		for (Graph.Vertex u : g) {
			node[u.getName()] = new BFSVertex(u);
		}
		// Set source to be at distance 0
		getVertex(src).distance = 0;
	}

	// reinitialize allows running BFS many times, with different sources
	void reinitialize(DMSTGraph.DMSTVertex newSource) {
		src = newSource;
		for (Graph.Vertex u : g) {
			BFSVertex bu = getVertex(u);
			bu.seen = false;
			bu.parent = null;
			bu.distance = INFINITY;
		}
		getVertex(src).distance = 0;
	}

	void bfs() {
		Queue<DMSTGraph.DMSTVertex> q = new LinkedList<>();
		q.add(src);
		getVertex(src).seen = true;
		while (!q.isEmpty()) {
			DMSTGraph.DMSTVertex u = q.remove();
			for (Graph.Edge e : u) {
				DMSTGraph.DMSTVertex v = (DMSTVertex) e.otherEnd(u);
				if (!seen(v)) {
					visit(u, v);
					q.add(v);
				}
			}
		}
	}

	public boolean seen(Graph.Vertex u) {
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
		BFSVertex bv = getVertex(v);
		bv.seen = true;
		bv.parent = u;
		bv.distance = distance(u) + 1;
	}
}