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

public class PrimMST extends GraphAlgorithm<PrimVertex> {
	static final int Infinity = Integer.MAX_VALUE;

	public PrimMST(Graph g) {
		super(g);
		node = new PrimVertex[g.size()];
		// Create array for storing vertex properties
		for (Graph.Vertex u : g) {
			node[u.getName()] = new PrimVertex(u, Integer.MAX_VALUE, 0, false, null);
		}
	}

	public int prim1(Graph.Vertex s) {
		int wmst = 0;
		EdgeComparator comp = new EdgeComparator();
		PriorityQueue<Graph.Edge> edgeQueue = new PriorityQueue<>(comp);
		getVertex(s).seen = true;
		for (Graph.Edge e : s)
			edgeQueue.add(e);
		Edge e;
		PrimVertex v;
		while (!edgeQueue.isEmpty()) {
			e = edgeQueue.remove();
			v = getVertex(e.to);
			if (v.seen == true)
				continue;
			v.seen = true;
			// MST is implicitly stored by parent pointers
			v.parent = e.from;
			wmst += e.weight;
			for (Graph.Edge e2 : e.to)
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

	public int prim2(Graph.Vertex s) {
		int wmst = 0;

		// Node v in V-S (Priority Queue) stores in v.d, the weight of a
		// smallest edge that connects v to some u in S
		VertexComparator comp = new VertexComparator();
		IndexedHeap<PrimVertex> vertexQueue = new IndexedHeap<>(node, comp, node.length);
		getVertex(s).d = 0;
		PrimVertex v, v2;
		while (!vertexQueue.isEmpty()) {
			v = vertexQueue.remove();
			v.seen = true;
			wmst += v.d;
			for (Graph.Edge e : v.vertex) {
				v2 = getVertex(e.to);
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
	 * Comparator based on priorities of the Vertices to create heap of
	 * Vertices.
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
		PrimMST mst = new PrimMST(g);
		int wmst = mst.prim1(s);
		timer.end();
		System.out.println(wmst);
	}
}
