package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp3_q1_TopologicalOrdering;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Graph;

public class TopologicalOrder {
	public static List<Graph.Vertex> toplogicalOrder1(Graph g) throws Exception {
		TopoGraph topoGraph = new TopoGraph(g);
		int topNum = 0;
		Queue<Graph.Vertex> q = new LinkedList<Graph.Vertex>();
		List<Graph.Vertex> topList = new ArrayList<Graph.Vertex>();

		for (Graph.Vertex u : g) {
			topoGraph.getVertex(u).inDegree = u.revAdj.size();
			if (topoGraph.getVertex(u).inDegree == 0)
				q.add(u);
		}

		Graph.Vertex u = null;
		while (!q.isEmpty()) {
			u = q.remove();
			topoGraph.getVertex(u).top = ++topNum;
			topList.add(u);
			for (Graph.Edge e : u.adj) {
				if (topoGraph.reduceInDegree(e.to) == 0) {
					q.add(u);
				}
			}
		}
		if (topNum != g.n)
			throw new IllegalStateException("Exception: Not a DAG");
		return topList;
	}

	public static List<Graph.Vertex> toplogicalOrder2(Graph g) {

		Iterator<Graph.Vertex> it = g.iterator();
		DFSFinishTimeOrderGraph dfsTopoGraph = new DFSFinishTimeOrderGraph(g);
		List<Graph.Vertex> decFinList = new ArrayList<Graph.Vertex>();
		while (it.hasNext()) {
			Graph.Vertex u = it.next();
			if (!dfsTopoGraph.seen(u)) {
				DFSFinishTimeOrderGraph.componentNo++;
				dfsTopoGraph.dfsVisit(u, decFinList);
			}
		}
		return decFinList;

	}

	public static void main(String args[]) throws FileNotFoundException, IllegalStateException {
		Scanner in;
		if (args.length > 0) {
			File inputFile = new File(args[0]);
			in = new Scanner(inputFile);
		} else {
			in = new Scanner(System.in);
		}
		// passing the scanner object to graph class to construct the graph
		Graph graph = Graph.readGraph(in);

		// if graph is not empty we call the topological sort method
		if (graph.n > 0) {
			try {
				System.out.println("The Topological Order of the given graph is " + toplogicalOrder1(graph));
				System.out
						.println("The DFS decreasing finish-time order of the given graph  " + toplogicalOrder2(graph));
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		} else
			System.out.println("Empty Graph");

	}

}
