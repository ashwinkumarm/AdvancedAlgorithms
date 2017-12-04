package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.lp8;

import java.io.File;

// Driver for LP8

import java.util.HashMap;
import java.util.Scanner;

import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Graph;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Graph.Edge;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Graph.Vertex;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Timer;

public class LP8 {
	static int VERBOSE = 0;

	public static void main(String[] args) throws Exception {
		Scanner in;
		if (args.length > 0) {
			File inputFile = new File(args[0]);
			in = new Scanner(inputFile);
		} else {
			in = new Scanner(System.in);
		}
		if (args.length > 1) {
			VERBOSE = Integer.parseInt(args[1]);
		}

		Graph g = Graph.readDirectedGraph(in);
		int s = in.nextInt();
		int t = in.nextInt();
		HashMap<Edge, Integer> capacity = new HashMap<>();
		HashMap<Edge, Integer> cost = new HashMap<>();
		int[] arr = new int[1 + g.edgeSize()];
		for (int i = 1; i <= g.edgeSize(); i++) {
			arr[i] = 1; // default capacity
		}
		while (in.hasNextInt()) {
			int i = in.nextInt();
			int cap = in.nextInt();
			arr[i] = cap;
		}

		Vertex src = g.getVertex(s);
		Vertex target = g.getVertex(t);

		for (Vertex u : g) {
			for (Edge e : u) {
				capacity.put(e, arr[e.getName()]);
				cost.put(e, e.getWeight());
			}
		}

		Timer timer = new Timer();

		/*
		 * // Find max-flow first and then a min-cost max-flow Flow f = new
		 * Flow(g, src, target, capacity); int value = f.relabelToFront();
		 */
		MinCostFlow mcf = new MinCostFlow(g, src, target, capacity, cost);
		int minCost = mcf.cycleCancellingMinCostFlow(0);
		System.out.println(minCost);

		if (VERBOSE > 0) {
			for (Vertex u : g) {
				System.out.print(u + " : ");
				for (Edge e : u) {
					if (mcf.flow(e) != 0) {
						System.out.print(e + ":" + mcf.flow(e) + "/" + mcf.capacity(e) + "@" + mcf.cost(e) + "| ");
					}
				}
				System.out.println();
			}
		}

		System.out.println(timer.end());
	}
}
