package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp3_q2_ConnectedComponent;

/**
 * This class checks if the given directed graph is strongly connected or not and returns the number 
 * of components in the graph
 * 
 * @author Ashwin, Arun, Deepak, Haritha
 *
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.DFS;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Graph;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.GraphVertexColor;

public class ConnectedComponentsOfGraph {

	/**
	 * This method runs DFS on the given graph and get the list of vertices in the
	 * decreasing order of their finish time and reverse's the graph and again run
	 * DFS on the reversed graph in the order of the decreasing finish time of
	 * vertices from the original graph
	 * 
	 * @param g
	 * @return cno
	 */

	public static int stronglyConnectedComponents(Graph g) {

		int cno = 0; // no of components
		Iterator<Graph.Vertex> it = g.iterator();
		DFS dfsGraph = new DFS(g);
		List<Graph.Vertex> decFinishList = new LinkedList<Graph.Vertex>();
		Graph.Vertex u;
		while (it.hasNext()) {
			u = it.next();
			if (dfsGraph.getVertexStatus(u) == GraphVertexColor.WHITE) {
				DFS.cno++;
				dfsGraph.dfsVisit(u, decFinishList);
			}
		}

		DFS.cno = 0;
		dfsGraph.reinitialize(g);
		List<Graph.Vertex> decFinishList2 = new LinkedList<Graph.Vertex>();
		for (Graph.Vertex v : decFinishList) {
			if (dfsGraph.getVertexStatus(v) == GraphVertexColor.WHITE) {
				DFS.cno++;
				dfsGraph.dfsVisitReverse(v, decFinishList2);
			}
		}

		if (decFinishList2.size() == g.size()) { // if not all vertices are processed then the graph is not strongly
													// connected
			cno = DFS.cno;
			return cno;
		}
		return cno;

	}

	public static void main(String[] args) throws FileNotFoundException {

		Scanner in;
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

			int cno = stronglyConnectedComponents(graph);
			if (cno > 0) {
				System.out.println("\nNumber of strongly connected components in a given graph: " + cno);
			} else {
				System.out.println("The given graph is not strongly connected");
			}

		} else
			System.out.println("Empty Graph");

	}

}
