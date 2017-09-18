package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp3_q5_BridgeCut;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Graph;

/**
 * This class returns the bridges of the graph and marks the cutVertices in the graph
 * 
 * @author Ashwin, Arun, Deepak, Haritha
 *
 */
public class BridgeCut {

	static List<Graph.Edge> findBridgeCut(Graph g) {
		DFS DFSGraph = new DFS(g);
		List<Graph.Edge> bridgeList = new LinkedList<>();
		for (Graph.Vertex u : g) {
			if (!DFSGraph.seen(u)) {
				DFSGraph.dfs(u, bridgeList);
			}
		}
		return bridgeList;

	}

	public static void main(String args[]) throws FileNotFoundException {
		Scanner in;
		if (args.length > 0) {
			File inputFile = new File(args[0]);
			in = new Scanner(inputFile);
		} else {
			in = new Scanner(System.in);
		}
		// passing the scanner object to graph class to construct the graph
		Graph graph = Graph.readGraph(in);

		// if graph is not empty we call the topological sort method
		if (graph.n > 0) {
			try {
				System.out.println("The Bridges of the given graph is " + findBridgeCut(graph));
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		} else
			System.out.println("Empty Graph");

	}
}
