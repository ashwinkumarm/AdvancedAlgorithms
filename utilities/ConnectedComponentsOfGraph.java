package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities;

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

import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.DFS.DFSVertex;

public class ConnectedComponentsOfGraph {
	public static List<DFSVertex> dfsFinList, dfsFinListReverse;
	public static int numberOfComponents, numberOfSCCs;
	public static DFS dfsGraph;
	public static DFSVertex[] firstDfsNode;

	/**
	 * This method runs DFS on the given graph and get the list of vertices in the
	 * decreasing order of their finish time and reverses the graph and again run
	 * DFS on the reversed graph in the order of the decreasing finish time of
	 * vertices from the original graph
	 *
	 * @param g
	 * @return cno
	 */

	public static int stronglyConnectedComponents(Graph g) {
		Iterator<Graph.Vertex> it = g.iterator();
		dfsGraph = new DFS(g);
		dfsFinList = new LinkedList<DFSVertex>();
		Graph.Vertex u;
		while (it.hasNext()) {
			u = it.next();
			if (!dfsGraph.getVertex(u).seen) {
				DFS.cno++;
				dfsGraph.dfsVisit(u, dfsFinList);
			}
		}
		numberOfComponents = DFS.cno;
		firstDfsNode = new DFSVertex[g.size()];
		for (Graph.Vertex vertex : g) {
			DFSVertex newNode = new DFSVertex(vertex);
			DFSVertex oldNode = dfsGraph.node[vertex.getName()];
			newNode.parent = oldNode.parent;
			newNode.seen = oldNode.seen;
			newNode.cno = oldNode.cno;
			firstDfsNode[vertex.getName()] = newNode;
		}
		DFS.cno = 0;
		dfsGraph.reinitialize(g);
		dfsFinListReverse = new LinkedList<DFSVertex>();
		for (DFSVertex dv : dfsFinList) {
			if (!dfsGraph.getVertex(dv.element).seen) {
				DFS.cno++;
				dfsGraph.dfsVisitReverse(dv.element, dfsFinListReverse);
			}
		}
		numberOfSCCs = DFS.cno;
		return numberOfSCCs;
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
			int cno = ConnectedComponentsOfGraph.stronglyConnectedComponents(graph);
			if (cno > 1) {
				System.out.println("\nNumber of strongly connected components in a given graph: " + cno);
			} else {
				System.out.println("The given graph is not strongly connected");
			}

		} else
			System.out.println("Empty Graph");

	}

}
