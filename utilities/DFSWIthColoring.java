package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities;

/**
 * Graph class customized to do DFS
 * 
 * @author Ashwin, Arun, Deepak, Haritha
 *
 */

import java.util.List;

public class DFSWIthColoring extends GraphAlgorithm<DFSWIthColoring.DFSVertex> {

	public static int cno, time;

	// Class to store information about a vertex in this algorithm
	static class DFSVertex {

		Graph.Vertex element;
		Graph.Vertex parent;
		int cno, startTime, finishTime;
		// status of a vertex i.e. WHITE = not visited, GREY = visiting, BLACK = visited
		GraphVertexColor visitStatus;

		DFSVertex(Graph.Vertex u) {
			element = u;
			parent = null;
			visitStatus = GraphVertexColor.WHITE;
		}

	}

	public DFSWIthColoring(Graph g) {
		super(g);
		node = new DFSVertex[g.size()];
		// Create array for storing vertex properties
		for (Graph.Vertex u : g) {
			node[u.getName()] = new DFSVertex(u);
		}
		cno = 0;
		time = 0;
	}

	// reinitialize allows running DFS many times
	public void reinitialize(Graph g) {
		for (Graph.Vertex u : g) {
			DFSVertex du = getVertex(u);
			du.cno = -1;
			du.finishTime = -1;
			du.startTime = -1;
			du.parent = null;
			du.visitStatus = GraphVertexColor.WHITE;
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
		visit(u);
		List<Graph.Edge> EdgeList = flag ? u.revAdj : u.adj;
		for (Graph.Edge e : EdgeList) {
			Graph.Vertex v = e.otherEnd(u);
			if (getVertexStatus(v) == GraphVertexColor.WHITE) {
				setParent(u, v);
				dfsVisit(v, decFinList, flag);
			}
		}
		setVertexStatus(u, GraphVertexColor.BLACK);
		setFinishTime(u);
		decFinList.add(0, u);

	}

	/**
	 * DFS algorithm which keeps track of the vertex start and finish time and
	 * stores the visited vertices to the start of the list passed in the parameter
	 * and also checks if the graph has a cycle or not
	 * 
	 * @param u
	 * @param decFinList
	 * @return boolean
	 */
	public boolean dfsVisitAndIsDAG(Graph.Vertex u, List<Graph.Vertex> decFinList) {

		visit(u);
		for (Graph.Edge e : u) {
			Graph.Vertex v = e.otherEnd(u);
			if (getVertexStatus(v) == GraphVertexColor.WHITE) {
				setParent(u, v);
				if (!dfsVisitAndIsDAG(v, decFinList)) {
					return false;
				}
			} else if (getVertexStatus(v) == GraphVertexColor.GREY) {
				return false;
			}
		}
		setVertexStatus(u, GraphVertexColor.BLACK);
		setFinishTime(u);
		decFinList.add(0, u);
		return true;
	}

	/**
	 * This method sets the component number statTime and visitStatus while visiting
	 * a vertex
	 * 
	 * @param u
	 */
	public void visit(Graph.Vertex u) {
		setVertexStatus(u, GraphVertexColor.GREY);
		setCno(u);
		setStartTime(u);
	}

	public void setParent(Graph.Vertex u, Graph.Vertex v) {
		getVertex(v).parent = u;
	}

	public void setCno(Graph.Vertex u) {
		getVertex(u).cno = cno;
	}

	public void setFinishTime(Graph.Vertex u) {
		getVertex(u).finishTime = ++time;
	}

	public void setStartTime(Graph.Vertex u) {
		getVertex(u).startTime = ++time;
	}

	public void setVertexStatus(Graph.Vertex u, GraphVertexColor visitStatus) {
		getVertex(u).visitStatus = visitStatus;
	}

	public GraphVertexColor getVertexStatus(Graph.Vertex u) {
		return getVertex(u).visitStatus;
	}

}