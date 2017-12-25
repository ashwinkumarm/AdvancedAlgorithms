package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities;

/**
 * This class performs topological ordering of vertices of a directed graph using two methods
 *
 * @author Ashwin, Arun, Deepak, Haritha
 *
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class TopologicalOrder {

	/**
	 * Remove vertices with no incoming edges, one at a time, along with their
	 * incident edges, and add them to a list.
	 *
	 * @param g
	 * @return
	 * @throws List<Graph.Vertex>
	 */
	public static List<Graph.Vertex> toplogicalOrder1(Graph g) {
		TopoGraph topoGraph = new TopoGraph(g);
		int topNum = 0;
		Queue<Graph.Vertex> q = new LinkedList<Graph.Vertex>();
		List<Graph.Vertex> topList = new LinkedList<Graph.Vertex>();

		for (Graph.Vertex u : g) {
			topoGraph.getVertex(u).inDegree = u.revAdj.size(); // get the number of incoming edges on each vertex
			if (topoGraph.getVertex(u).inDegree == 0)
				q.add(u);
		}

		Graph.Vertex u = null;
		while (!q.isEmpty()) {
			u = q.remove();
			topoGraph.getVertex(u).top = ++topNum;
			topList.add(u);
			for (Graph.Edge e : u.adj) {
				if (topoGraph.reduceInDegree(e.to) == 0) {
					q.add(e.to);
				}
			}
		}
		if (topNum != g.n)// if not all vertices are visited then there is a cycle.
			return null;
		return topList;
	}

	/**
	 * Run DFS on g and add nodes to the front of the output list, in the order in
	 * which they finish.
	 *
	 * @param g
	 * @return List<Graph.Vertex>
	 */
	public static List<Graph.Vertex> toplogicalOrder2(Graph g) {

		Iterator<Graph.Vertex> it = g.iterator();
		DFSWithColoring dfsTopoGraph = new DFSWithColoring(g);
		List<Graph.Vertex> decFinishList = new LinkedList<Graph.Vertex>(); // list to store vertices in decrerasing
																			// order of their finish time
		Graph.Vertex u;
		while (it.hasNext()) {
			u = it.next();
			if (dfsTopoGraph.getVertexStatus(u) == GraphVertexColor.WHITE) {
				DFSWithColoring.cno++;
				if (!dfsTopoGraph.dfsVisitAndIsDAG(u, decFinishList)) {
					return null;
				}
			}
		}
		return decFinishList;
	}

	public static void main(String args[]) throws FileNotFoundException, IllegalStateException {
		Scanner in;
		List<Graph.Vertex> result1;
		List<Graph.Vertex> result2;
		if (args.length > 0) {
			File inputFile = new File(args[0]);
			in = new Scanner(inputFile);
		} else {
			in = new Scanner(System.in);
		}
		// passing the scanner object to graph class to construct the graph
		Graph graph = Graph.readDirectedGraph(in);

		// if graph is not empty we call the topological sort method
		if (graph.n > 0) {
			try {
				result1 = toplogicalOrder1(graph);
				result2 = toplogicalOrder2(graph);
				if (result1 != null)
					System.out.println("The Topological Order of the given graph: " + result1);
				else
					System.out.println("Algorithm1 Exception: Not a DAG");
				if (result2 != null)
					System.out.println("The DFS finish-time in decreasing order of the given graph: " + result2);
				else
					System.out.println("Algorithm2 Exception: Not a DAG");
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		} else
			System.out.println("Empty Graph");

	}

}
