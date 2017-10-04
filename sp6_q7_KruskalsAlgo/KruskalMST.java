package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp6_q7_KruskalsAlgo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Graph;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Timer;

public class KruskalMST {
	public KruskalMST(Graph g) {
	}

	public int kruskal() {
		int wmst = 0;

		// SP6.Q7: Kruskal's algorithm:

		return wmst;
	}

	public static void main(String[] args) throws FileNotFoundException {
		Scanner in;

		if (args.length > 0) {
			File inputFile = new File(args[0]);
			in = new Scanner(inputFile);
		} else {
			in = new Scanner(System.in);
		}

		Graph g = Graph.readGraph(in);
		Graph.Vertex s = g.getVertex(1);

		Timer timer = new Timer();
		KruskalMST mst = new KruskalMST(g);
		int wmst = mst.kruskal();
		timer.end();
		System.out.println(wmst);
	}
}
