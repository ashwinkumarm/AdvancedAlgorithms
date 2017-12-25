package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.lp4;

import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Graph;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Timer;

public class LP4e {
	static int VERBOSE = 0;

	public static void main(String[] args) {
		if (args.length > 0) {
			VERBOSE = Integer.parseInt(args[0]);
		}
		java.util.Scanner in = new java.util.Scanner(System.in);
		Graph g = Graph.readDirectedGraph(in);
		int source = in.nextInt();
		int target = in.nextInt();
		int maxEdges = in.nextInt();
		Timer t = new Timer();
		LP4 handle = new LP4(g, g.getVertex(source));
		int result = handle.constrainedShortestPath(g.getVertex(target), maxEdges);
		if (VERBOSE > 0) {
			LP4.printGraph(g, null, g.getVertex(source), null, maxEdges);
		}
		System.out.println(result + "\n" + t.end());
	}

}
