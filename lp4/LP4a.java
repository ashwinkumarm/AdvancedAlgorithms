package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.lp4;

import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Graph;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Timer;

public class LP4a {

	static int VERBOSE = 0;

	public static void main(String[] args) {
		if (args.length > 0) {
			VERBOSE = Integer.parseInt(args[0]);
		}
		Graph g = Graph.readDirectedGraph(new java.util.Scanner(System.in));
		Timer t = new Timer();
		LP4 handle = new LP4(g, null);
		long result = handle.countTopologicalOrders();
		if (VERBOSE > 0) {
			LP4.printGraph(g, null, null, null, 0);
		}
		System.out.println(result + "\n" + t.end());
	}

}
