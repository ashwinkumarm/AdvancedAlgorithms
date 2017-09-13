package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp3_q1_TopologicalOrdering;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class TopologicalOrder {
	public static List<Graph.Vertex> toplogicalOrder1(Graph g) {
		int topNum = 0;
		Queue<Graph.Vertex> q = new Queue<>();
		
		return null;
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

		// if graph is not empty we call the diameter method
		if (graph.v.length > 0 && graph.n > 0) {
			System.out.println("The longest diameter path in the given graph is " + toplogicalOrder1(graph));
		} else
			System.out.println("Empty Graph");

	}

}
