package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.lp2;

import java.util.Scanner;

import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public class LP2 {
	static int VERBOSE = 1;

	public static void main(String[] args) throws FileNotFoundException {
		Scanner in;
		if (args.length > 0) {
			File inputFile = new File(args[0]);
			in = new Scanner(inputFile);
		} else {
			in = new Scanner(System.in);
		}
		int start = 1;
		if (args.length > 1) {
			start = Integer.parseInt(args[1]);
		}
		if (args.length > 2) {
			VERBOSE = Integer.parseInt(args[2]);
		}
		Timer t = new Timer();
		Graph g = Graph.readDirectedGraph(in);
		System.out.println("Read Graph" + t.end());
		Graph.Vertex startVertex = g.getVertex(start);

		Euler euler = new Euler(g, startVertex);
		euler.setVerbose(VERBOSE);
		t.start();
		boolean eulerian = euler.isEulerian();
		System.out.println("Is Eulerian" + t.end());
		if (!eulerian) {
			return;
		}
		t.start();
		List<Graph.Edge> tour = euler.findEulerTour();
		System.out.println(t.end());

		if (VERBOSE > 0) {
			System.out.println("Output:\n_________________________");
			System.out.print(tour);
			System.out.println();
			System.out.println("_________________________");
		}
	}
}
