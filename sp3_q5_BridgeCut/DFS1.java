package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp3_q5_BridgeCut;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Graph;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.GraphAlgorithm;

/**
 * This class forms the graph with DFS Vertices and contains the method to
 * perform DFS
 * 
 * @author Ashwin, Arun, Deepak, Haritha
 *
 */
public class DFS1 extends GraphAlgorithm<DFS1.DFSVertex> {

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

	public DFS1(Graph g) {
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

	/**
	 * Performs Depth First Search on the graph maintaining the low time and
	 * discovery time for each vertex to find whether it is a cut vertex or not Also
	 * finds the bridge(Edge) when (low time >= discovery time) for both vertices
	 * 
	 * @param u
	 *            : Vertex
	 * @param bridgeList
	 */
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
				//Update the low time for the parent depending on each of its children
				du.low = Math.min(du.low, dv.low);
				//Bridge Condition
				if (dv.low >= dv.dis)
					bridgeList.add(e);
			}
			// To detect the Back Edge
			else if (v != getParent(u)) { 
				du.low = Math.min(du.low, dv.dis);
			}
		}
		//Condition to find the cut Vertex
		if ((du.parent != null && dv.low >= du.dis) || (du.parent == null && du.childrenCount >= 2)) {
			du.cutVertex = true;
		}
	}

	/**
	 * Return the visitStatus for the given vertex
	 * @param u
	 * @return
	 */
	boolean seen(Graph.Vertex u) {
		return getVertex(u).seen;
	}

	/**
	 * Return the parent for the given vertex
	 * @param u
	 * @return
	 */
	Graph.Vertex getParent(Graph.Vertex u) {
		return getVertex(u).parent;
	}

	
	int distance(Graph.Vertex u) {
		return getVertex(u).distance;
	}

	/**
	 * Sets the parent of 'v' Vertex as 'u' Vertex
	 * Also marks the visit Status as true
	 * @param u
	 * @param v
	 */
	void visit(Graph.Vertex u, Graph.Vertex v) {
		DFSVertex bv = getVertex(v);
		bv.seen = true;
		bv.parent = u;
		bv.distance = distance(u) + 1;
	}
}