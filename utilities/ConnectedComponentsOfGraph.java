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

import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.DFSStronglyConnected.DFSVertex;

public class ConnectedComponentsOfGraph {

	/**
	 * Class used to hold the result required by the calling program. *
	 */
	public static class SCCResult {
		List<DFSVertex> connectedComponents;
		int numberOfComponents;
		HashSet<Graph.Vertex>[] stronglyConnectedComponents;

		public List<DFSVertex> getConnectedComponents() {
			return connectedComponents;
		}

		public int getNumberOfComponents() {
			return numberOfComponents;
		}

		public HashSet<Graph.Vertex>[] getStronglyConnectedComponents() {
			return stronglyConnectedComponents;
		}
	}

	/**
	 * This method runs DFS on the given graph and get the list of vertices in
	 * the decreasing order of their finish time and reverses the graph and
	 * again run DFS on the reversed graph in the order of the decreasing finish
	 * time of vertices from the original graph
	 *
	 * @param g
	 * @return cno
	 */

	public static SCCResult stronglyConnectedComponents(Graph g) {
		Iterator<Graph.Vertex> it = g.iterator();
		DFSStronglyConnected dfsGraph = new DFSStronglyConnected(g);
		SCCResult result = new SCCResult();
		result.connectedComponents = new LinkedList<DFSVertex>();
		Graph.Vertex u;
		while (it.hasNext()) {
			u = it.next();
			if (!dfsGraph.getVertex(u).seen) {
				DFSStronglyConnected.cno++;
				dfsGraph.dfsVisit(u, result.connectedComponents);
			}
		}
		result.numberOfComponents = DFSStronglyConnected.cno;
		DFSStronglyConnected.cno = 0;
		dfsGraph.reinitialize(g);
		List<DFSVertex> dfsFinList = new LinkedList<DFSVertex>();
		for (DFSVertex dv : result.connectedComponents) {
			if (!dfsGraph.getVertex(dv.element).seen) {
				DFSStronglyConnected.cno++;
				dfsGraph.dfsVisitReverse(dv.element, dfsFinList);
			}
		}
		HashSet<Graph.Vertex>[] stronglyConnectedComponents = new HashSet[DFSStronglyConnected.cno];
		int index;
		for (DFSVertex dv : dfsFinList) {
			index = dv.cno - 1;
			if (stronglyConnectedComponents[index] == null)
				stronglyConnectedComponents[index] = new HashSet<>();
			stronglyConnectedComponents[index].add(dv.element);
		}
		return result;
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

			int cno = stronglyConnectedComponents(graph).getStronglyConnectedComponents().length;
			if (cno > 1) {
				System.out.println("\nNumber of strongly connected components in a given graph: " + cno);
			} else {
				System.out.println("The given graph is not strongly connected");
			}

		} else
			System.out.println("Empty Graph");

	}

}
