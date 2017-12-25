package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp6_q4and6_PrimAlgo2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;

import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Graph;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Graph.Edge;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.GraphAlgorithm;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Timer;

/**
 * This class performs two versions of Prims algorithm for finding the minimum spanning tree
 * 
 * @author Ashwin, Arun, Deepak, Haritha
 *
 * @param <T>
 */
public class PrimMST extends GraphAlgorithm<PrimVertex> {
	static final int Infinity = Integer.MAX_VALUE;

	public PrimMST(Graph g) {
		super(g);
		node = new PrimVertex[g.size()];
		// Create array for storing vertex properties
		int index = 0;
		for (Graph.Vertex u : g) {
			node[u.getName()] = new PrimVertex(u, Integer.MAX_VALUE, index++, false, null);
		}
	}

	/**
	 * This method performs the version1 of Prims algorithm that is based on the
	 * weights of the edges
	 * 
	 * @param s
	 * @return
	 */
	public int prim1(Graph.Vertex s) {
		int wmst = 0;
		EdgeComparator comp = new EdgeComparator();
		PriorityQueue<Graph.Edge> edgeQueue = new PriorityQueue<>(comp);
		getVertex(s).seen = true;
		for (Graph.Edge e : s)
			edgeQueue.add(e);
		Edge e;
		PrimVertex u, v;
		while (!edgeQueue.isEmpty()) {
			e = edgeQueue.remove();
			u = getVertex(e.from);
			v = getVertex(e.to);
			if (u.seen && v.seen)
				continue;
			v = u.seen ? v : u;
			v.seen = true;
			// MST is implicitly stored by parent pointers
			v.parent = e.otherEnd(v.vertex);
			wmst += e.weight;
			for (Graph.Edge e2 : v.vertex)
				if (getVertex(e2.otherEnd(v.vertex)).seen != true)
					edgeQueue.add(e2);
		}
		return wmst;
	}

	/**
	 * Comparator based on Edge weights to create heap of edges.
	 *
	 * @param <T>
	 */
	static class EdgeComparator implements Comparator<Graph.Edge> {

		@Override
		public int compare(Edge e1, Edge e2) {
			if (e1.weight > e2.weight)
				return 1;
			else if (e1.weight < e2.weight)
				return -1;
			else
				return 0;
		}
	}

	/**
	 * This method performs the version2 of Prims algorithm that is based on the
	 * weights of vertices
	 * 
	 * @param s
	 * @return
	 */
	public int prim2(Graph.Vertex s) {
		int wmst = 0;

		// Node v in V-S (Priority Queue) stores in v.d, the weight of a
		// smallest edge that connects v to some u in S
		VertexComparator comp = new VertexComparator();
		PrimVertex[] vertexArray = new PrimVertex[node.length];
		System.arraycopy(node, 0, vertexArray, 0, node.length);
		getVertex(s).d = 0;
		IndexedHeap<PrimVertex> vertexQueue = new IndexedHeap<>(vertexArray, comp, node.length);
		PrimVertex v, v2;
		while (!vertexQueue.isEmpty()) {
			v = vertexQueue.remove();
			v.seen = true;
			wmst += v.d;
			for (Graph.Edge e : v.vertex) {
				v2 = getVertex(e.otherEnd(v.vertex));
				if (v2.seen != true && v2.d > e.weight) {
					v2.d = e.weight;
					v2.parent = v.vertex;
					vertexQueue.decreaseKey(v2);
				}
			}
		}
		return wmst;
	}

	/**
	 * Comparator based on priorities of the Vertices to create heap of Vertices.
	 *
	 * @param <T>
	 */
	static class VertexComparator implements Comparator<PrimVertex> {

		@Override
		public int compare(PrimVertex v1, PrimVertex v2) {
			if (v1.d > v2.d)
				return 1;
			else if (v1.d < v2.d)
				return -1;
			else
				return 0;
		}
	}

	/**
	 * Main method for testing
	 * 
	 * @param args
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException {
		Scanner in;

		if (args.length > 0) {
			File inputFile = new File(args[0]);
			in = new Scanner(inputFile);
		} else {
			in = new Scanner(System.in);
		}

		Graph g = Graph.readGraph(in);
		Graph.Vertex s = g.getVertex(4);

		PrimMST mst = new PrimMST(g);

		Timer timer = new Timer();
		int wmst = mst.prim1(s);
		timer.end();
		System.out.println("Java's Priority Queue");
		System.out.println("MST weight: " + wmst);
		System.out.println(timer);
		System.out.println("---------------------");

		PrimMST mst1 = new PrimMST(g);

		timer.start();
		wmst = mst1.prim2(s);
		timer.end();
		System.out.println("Indexed Heap");
		System.out.println("MST weight: " + wmst);
		System.out.println(timer);
	}
}
