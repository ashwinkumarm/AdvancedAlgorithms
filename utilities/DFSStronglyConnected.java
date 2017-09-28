package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities;

/**
 * Graph class customized to do DFS
 * 
 * @author Ashwin, Arun, Deepak, Haritha
 *
 */

import java.util.List;

public class DFSStronglyConnected extends GraphAlgorithm<DFSStronglyConnected.DFSVertex> {


	public static int cno;

	// Class to store information about a vertex in this algorithm
	static class DFSVertex {

		Graph.Vertex element;
		Graph.Vertex parent;
		int cno;
		boolean seen;

		DFSVertex(Graph.Vertex u) {
			element = u;
			parent = null;
			seen = false;
		}

	}

	public DFSStronglyConnected(Graph g) {
		super(g);
		node = new DFSVertex[g.size()];
		// Create array for storing vertex properties
		for (Graph.Vertex u : g) {
			node[u.getName()] = new DFSVertex(u);
		}
		cno = 0;
	}

	// reinitialize allows running DFS many times
	public void reinitialize(Graph g) {
		for (Graph.Vertex u : g) {
			DFSVertex du = getVertex(u);
			du.cno = -1;
			du.parent = null;
			du.seen = false;
		}
	}

	public void dfsVisit(Graph.Vertex u, List<Graph.Vertex> decFinList) {
		dfsVisit(u, decFinList, false);
	}

	public void dfsVisitReverse(Graph.Vertex u, List<Graph.Vertex> decFinList) {
		dfsVisit(u, decFinList, true);
	}

	/**
	 * DFS algorithm which keeps track of the vertex start and finish time and
	 * stores the visited vertices to the start of the list passed in the parameter
	 * 
	 * @param u
	 * @param decFinList
	 */
	public void dfsVisit(Graph.Vertex u, List<Graph.Vertex> decFinList, boolean flag) {
		DFSVertex du = getVertex(u);
		du.seen = true;
		du.cno = cno;
		List<Graph.Edge> EdgeList = flag ? u.revAdj : u.adj;
		for (Graph.Edge e : EdgeList) {
			Graph.Vertex v = e.otherEnd(u);
			DFSVertex dv =  getVertex(v);
			if (!dv.seen) {
				setParent(u, v);
				dfsVisit(v, decFinList, flag);
			}
		}
		decFinList.add(0, u);
	}

	/**
	 * This method sets the component number statTime and visitStatus while visiting
	 * a vertex
	 * 
	 * @param u
	 */
	
	public void setParent(Graph.Vertex u, Graph.Vertex v) {
		getVertex(v).parent = u;
	}

}