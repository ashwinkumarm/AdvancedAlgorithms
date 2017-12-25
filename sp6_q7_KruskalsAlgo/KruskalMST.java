package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp6_q7_KruskalsAlgo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.DisjointNode;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.DisjointSets;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Graph;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Graph.Edge;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Graph.Vertex;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Timer;

public class KruskalMST {
	List<Graph.Edge> allEdges;
	DisjointSets<KruskalVertex> set;

	class KruskalVertex extends DisjointNode {
		Graph.Vertex vertex;

		KruskalVertex(Graph.Vertex v, int index, KruskalVertex p, int rank) {
			super(index, p, rank);
			vertex = v;
		}
	}

	public KruskalMST(Graph g) {
		EdgeComparator comp = new EdgeComparator();
		set = new DisjointSets<KruskalVertex>(g.size());
		allEdges = new LinkedList<Graph.Edge>();
		Iterator<Vertex> it = g.iterator();
		Graph.Vertex v;
		while (it.hasNext()) {
			v = it.next();
			set.makeSet(new KruskalVertex(v, getIndex(v), null, 0));
			for (Graph.Edge e : v)
				allEdges.add(e);
		}
		Collections.sort(allEdges, comp);
	}

	public int kruskal() {
		int wmst = 0;
		List<Graph.Edge> mst = new LinkedList<Graph.Edge>();
		Iterator<Graph.Edge> it = allEdges.iterator();
		Graph.Edge e;
		int ru, rv;
		while (it.hasNext()) {
			e = it.next();
			ru = set.find(getIndex(e.from));
			rv = set.find(getIndex(e.to));
			if (ru != rv) {
				mst.add(e);
				wmst += e.weight;
				set.union(ru, rv);
			}
		}
		return wmst;
	}

	private int getIndex(Vertex v) {
		return v.getName();
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

	public static void main(String[] args) throws FileNotFoundException {
		Scanner in;

		if (args.length > 0) {
			File inputFile = new File(args[0]);
			in = new Scanner(inputFile);
		} else {
			in = new Scanner(System.in);
		}
		Graph g = Graph.readGraph(in);
		Timer timer = new Timer();
		KruskalMST mst = new KruskalMST(g);
		int wmst = mst.kruskal();
		timer.end();
		System.out.println(wmst);
		System.out.println(timer);
	}
}
