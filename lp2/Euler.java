package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.lp2;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * Class to find Euler's tour in a given graph
 *
 * @author Ashwin, Arun, Deepak, Haritha
 *
 */

import java.util.List;
import java.util.ListIterator;
import java.util.Queue;

import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.ConnectedComponentsOfGraph;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Graph;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Graph.Edge;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Graph.Vertex;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.GraphAlgorithm;

public class Euler extends GraphAlgorithm<Euler.EulerVertex> {
	int VERBOSE;
	List<Graph.Edge> tour;
	Graph.Vertex startVertex;
	static Queue<Graph.Vertex> q;

	static class EulerVertex {

		Graph.Vertex element;
		List<Graph.Edge> subTour; // tour starting a vertex if any
		boolean isTourExplored; // flag to indicate if a tour starting at vertex
								// has been explored or not
		Iterator<Graph.Edge> iterator;

		EulerVertex(Graph.Vertex u) {
			element = u;
			subTour = new LinkedList<>();
			isTourExplored = false;
			iterator = element.adj.listIterator();
		}

	}

	// Constructor
	public Euler(Graph g, Graph.Vertex start) {
		super(g);
		startVertex = start;
		node = new EulerVertex[g.size()];
		for (Graph.Vertex u : g) {
			node[u.getName()] = new EulerVertex(u);
		}
		VERBOSE = 1;
		tour = new LinkedList<>();
		q = new LinkedList<>();
	}

	// To do: function to find an Euler tour
	public List<Graph.Edge> findEulerTour() {
		findTours();
		if (VERBOSE > 9) {
			printTours();
		}
		stitchTours();
		return tour;
	}

	/*
	 * To do: test if the graph is Eulerian. If the graph is not Eulerian, it
	 * prints the message: "Graph is not Eulerian" and one reason why, such as
	 * "inDegree = 5, outDegree = 3 at Vertex 37" or
	 * "Graph is not strongly connected"
	 */
	public boolean isEulerian() {

		// check whether the given graph is strongly connected or not
		if (new ConnectedComponentsOfGraph().stronglyConnectedComponents(g) != 1) {
			System.out.println("Graph is not Eulerian");
			System.out.println("Reason: Graph is not strongly connected");
			return false;
		}

		// check if indegree == outdegree at every vertex
		for (Graph.Vertex v : g) {
			int indegree = v.revAdj.size();
			int outdegree = v.adj.size();

			if (indegree != outdegree) {
				System.out.println("Graph is not Eulerian");
				System.out.println("Reason: the indegree(" + indegree + ") is not equal to outdegree(" + outdegree
						+ ") at vertex " + v.toString());
				return false;
			}
		}
		return true;
	}

	void findTours() {
		findSubTour(startVertex, getSubTour(startVertex));
		while (!q.isEmpty()) {
			Graph.Vertex u = q.poll();
			findSubTour(u, getSubTour(u));
		}
	}

	// Find tours starting at vertices with unexplored edges
	void findSubTour(Graph.Vertex start, List<Graph.Edge> subTour) {

		Graph.Vertex u = start;
		ListIterator<Graph.Edge> edgeIterator = getIterator(u);

		while (edgeIterator.hasNext()) {
			Graph.Edge e = edgeIterator.next();
			if (!e.seen) {
				q.add(u);
				subTour.add(e);
				e.seen = true;
				Graph.Vertex v = e.otherEnd(u);
				edgeIterator = getIterator(v);
				u = v;
			}
		}
	}

	private ListIterator<Edge> getIterator(Vertex u) {
		return (ListIterator<Edge>) getVertex(u).iterator;
	}

	/*
	 * Print tours found by findTours() using following format: Start vertex of
	 * tour: list of edges with no separators Example: lp2-in1.txt, with start
	 * vertex 3, following tours may be found. 3:
	 * (3,1)(1,2)(2,3)(3,4)(4,5)(5,6)(6,3) 4: (4,7)(7,8)(8,4) 5: (5,7)(7,9)(9,5)
	 *
	 * Just use System.out.print(u) and System.out.print(e)
	 */
	void printTours() {
		for (Graph.Vertex u : g) {
			if (!getSubTour(u).isEmpty()) {
				System.out.println(u.toString() + ": " + getSubTour(u));
			}
		}
	}

	// Stitch tours into a single tour using the algorithm discussed in class
	void stitchTours() {
		setIsTourExplored(startVertex, true);
		exploreTour(startVertex);
	}

	/**
	 * Method to find or explore sub tours inside a particular tour
	 *
	 * @param u
	 */
	void exploreTour(Graph.Vertex u) {
		Graph.Vertex v = u;
		for (Graph.Edge e : getSubTour(u)) {
			tour.add(e);
			v = e.otherEnd(v);
			if (!getSubTour(v).isEmpty() && !getIsTourExplored(v)) {
				setIsTourExplored(v, true);
				exploreTour(v);

			}
		}
	}

	void setVerbose(int v) {
		VERBOSE = v;
	}

	List<Graph.Edge> getSubTour(Graph.Vertex u) {
		return getVertex(u).subTour;
	}

	boolean getIsTourExplored(Graph.Vertex u) {
		return getVertex(u).isTourExplored;
	}

	void setIsTourExplored(Graph.Vertex u, boolean flag) {
		getVertex(u).isTourExplored = flag;
	}
}
