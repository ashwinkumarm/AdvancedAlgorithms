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
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.DFS.DFSVertex;

public class ConnectedComponentsOfGraph {
	public List<DFSVertex> dfsFinList, dfsFinListReverse;
	public int numberOfComponents, numberOfSCCs;
	public DFS dfsGraph;

	/**
	 * This method runs DFS on the given graph and get the list of vertices in
	 * the decreasing order of their finish time and reverses the graph and
	 * again run DFS on the reversed graph in the order of the decreasing finish
	 * time of vertices from the original graph
	 *
	 * @param g
	 * @return cno
	 */

	public int stronglyConnectedComponents(Graph g) {
		Iterator<Graph.Vertex> it = g.iterator();
		/*
		 * System.out.println("Inside Strongly Connected Components"); for
		 * (Vertex v : g) { for (Edge e : v) { System.out.print(e); } }
		 */
		System.out.println();
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
		ConnectedComponentsOfGraph connectedComponentsOfGraph = new ConnectedComponentsOfGraph();
		if (graph.n > 0) {
			int cno = connectedComponentsOfGraph.stronglyConnectedComponents(graph);
			if (cno > 1) {
				System.out.println("\nNumber of strongly connected components in a given graph: " + cno);
			} else {
				System.out.println("The given graph is not strongly connected");
			}

		} else
			System.out.println("Empty Graph");

		HashSet<Graph.Vertex>[] stronglyConnectedComponents = new HashSet[connectedComponentsOfGraph.numberOfSCCs];
		int index;
		for (DFSVertex dv : connectedComponentsOfGraph.dfsFinListReverse) {
			index = dv.getCno() - 1;
			if (stronglyConnectedComponents[index] == null)
				stronglyConnectedComponents[index] = new HashSet<>();
			stronglyConnectedComponents[index].add(dv.getElement());
		}

		for (HashSet<Graph.Vertex> h : stronglyConnectedComponents)
			System.out.println(h);

	}

}
