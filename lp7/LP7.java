package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.lp7;

import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Graph;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Graph.Edge;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Graph.Vertex;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Timer;

public class LP7 {
	static int VERBOSE = 1;

	public static void main(String[] args) {
		if (args.length > 0) {
			VERBOSE = Integer.parseInt(args[0]);
		}
		java.util.Scanner in = new java.util.Scanner(System.in);
		Graph g = Graph.readDirectedGraph(in);
		Timer timer = new Timer();
		int s = in.nextInt();
		int t = in.nextInt();
		java.util.HashMap<Edge, Integer> capacity = new java.util.HashMap<>();
		for (Vertex u : g) {
			for (Edge e : u) {
				capacity.put(e, in.nextInt());
				// capacity.put(e, 1);
			}
		}
		Flow f = new Flow(g, g.getVertex(s), g.getVertex(t), capacity);
		int value = f.relabelToFront();
		// int value = f.dinitzMaxFlow();

		System.out.println(value);
		System.out.println(f.minCutS());
		System.out.println(f.minCutT());

		if (VERBOSE > 0) {
			for (Vertex u : g) {
				System.out.print(u + " : ");
				for (Edge e : u) {
					System.out.print(e + ":" + f.flow(e) + "/" + f.capacity(e) + " | ");
				}
				System.out.println();
			}
		}

		System.out.println(timer.end());
	}
}
