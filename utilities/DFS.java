package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities;

/**
 * Graph class customized to do DFS
 *
 * @author Ashwin, Arun, Deepak, Haritha
 *
 */

import java.util.List;

public class DFS extends GraphAlgorithm<DFS.DFSVertex> {

	public static int cno;

	// Class to store information about a vertex in this algorithm
	public static class DFSVertex {

		Graph.Vertex element;
		Graph.Vertex parent;
		int cno;
		boolean seen;

		DFSVertex(Graph.Vertex u) {
			element = u;
			parent = null;
			seen = false;
		}

		public Graph.Vertex getElement() {
			return element;
		}

		public Graph.Vertex getParent() {
			return parent;
		}

		public int getCno() {
			return cno;
		}

		public boolean isSeen() {
			return seen;
		}

	}

	public DFS(Graph g) {
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
			du.cno = 0;
			du.parent = null;
			du.seen = false;
		}
	}

	public void dfsVisit(Graph.Vertex u, List<DFSVertex> connectedComponents) {
		dfsVisit(u, connectedComponents, false);
	}

	public void dfsVisitReverse(Graph.Vertex u, List<DFSVertex> decFinList) {
		dfsVisit(u, decFinList, true);
	}

	/**
	 * DFS algorithm which keeps track of the vertex start and finish time and
	 * stores the visited vertices to the start of the list passed in the
	 * parameter
	 *
	 * @param u
	 * @param connectedComponents
	 */
	public void dfsVisit(Graph.Vertex u, List<DFSVertex> connectedComponents, boolean flag) {
		DFSVertex du = getVertex(u);
		du.seen = true;
		du.cno = cno;
		List<Graph.Edge> EdgeList = flag ? u.revAdj : u.adj;
		for (Graph.Edge e : EdgeList) {
			Graph.Vertex v = e.otherEnd(u);
			DFSVertex dv = getVertex(v);
			if (!dv.seen) {
				setParent(u, v);
				dfsVisit(v, connectedComponents, flag);
			}
		}
		connectedComponents.add(0, du);
	}

	/**
	 * This method sets the component number statTime and visitStatus while
	 * visiting a vertex
	 *
	 * @param u
	 */

	public void setParent(Graph.Vertex u, Graph.Vertex v) {
		getVertex(v).parent = u;
	}

}