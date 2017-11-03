package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp8_q1to6_ShortestPathAlgos;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Graph;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Graph.Edge;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Graph.Vertex;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.GraphAlgorithm;

/**
 * This class finds the shortest path from a given source vertex in a graph
 * using Bellman Ford Algorithm (Take 1)
 *
 * @author Ashwin, Arun, Deepak, Haritha
 * @param <T>
 */
public class BellmanFordTake1 extends GraphAlgorithm<BellmanTake1Vertex> {

	/**
	 * @param g
	 */
	public BellmanFordTake1(Graph g) {
		super(g);
		node = new BellmanTake1Vertex[g.size()];
		for (Graph.Vertex u : g)
			node[u.getName()] = new BellmanTake1Vertex(u, 0, Integer.MAX_VALUE, g.size());
	}

	/**
	 * This method is an implementation for Bellman Ford Take 1 algorithm.
	 *
	 * @param g
	 * @param K
	 * @param src
	 * @return
	 */
	public boolean findShortestPathUsingAtmostKEdges(Graph g, int K, Vertex src) {
		getVertex(src).d[0] = 0;
		BellmanTake1Vertex bu, bp;
		Vertex p;
		boolean noChange;
		for (int k = 1; k <= K; k++) {
			noChange = true;
			for (Vertex u : g) {
				bu = getVertex(u);
				bu.d[k] = bu.d[k - 1];
				for (Edge e : u.revAdj) {
					p = e.otherEnd(u);
					bp = getVertex(p);
					if (bp.d[k - 1] != Integer.MAX_VALUE && bu.d[k] > bp.d[k - 1] + e.weight) {
						bu.d[k] = bp.d[k - 1] + e.weight;
						bu.parent = p;
						noChange = false;
					}
				}
			}
			if (noChange) {
				copyToDistance(k);
				return true;
			}
		}
		copyToDistance(K);
		return false; // G has a negative cycle
	}

	private void copyToDistance(int K) {
		BellmanTake1Vertex bu;
		for (Vertex u : g) {
			bu = getVertex(u);
			bu.distance = bu.d[K];
		}
	}

	/**
	 * Driver method.
	 *
	 * @param args
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException {
		Scanner in;
		boolean isDirectedGraph = true; // flag to read directed or undirected
										// graph
		Graph g;
		Graph.Vertex src, dest;

		if (args.length > 0) {
			File inputFile = new File(args[0]);
			in = new Scanner(inputFile);
		} else {
			in = new Scanner(System.in);
		}

		if (isDirectedGraph)
			g = Graph.readDirectedGraph(in);
		else
			g = Graph.readGraph(in);
		src = g.getVertex(in.nextInt());
		dest = g.getVertex(in.nextInt());
		int K = in.nextInt();
		BellmanFordTake1 sp = new BellmanFordTake1(g);
		sp.findShortestPathUsingAtmostKEdges(g, K, src);
		int d = sp.getVertex(dest).distance;
		System.out.println(d == Integer.MAX_VALUE ? "Infinity" : d);
	}
}
