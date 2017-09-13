package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp3_q1_TopologicalOrdering;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp3_q1_TopologicalOrdering.CC.CCVertex;

public class TopologicalOrder {
	public static List<Graph.Vertex> toplogicalOrder1(Graph g) throws Exception {
		CC cc = new CC(g);
		int topNum = 0;
		Queue<Graph.Vertex> q = new LinkedList<Graph.Vertex>();
		List<Graph.Vertex> topList = new ArrayList<Graph.Vertex>();

		for (Graph.Vertex u : g.v) {
			cc.getCCVertex(u).inDegree = u.revAdj.size();
			if (cc.getCCVertex(u).inDegree == 0)
				q.add(u);
		}
		
		Graph.Vertex u = null;
		while (!q.isEmpty()) {
			u = q.remove();
			cc.getCCVertex(u).top = ++topNum;
			topList.add(u);
			for (Graph.Edge e : u.adj) {
				CCVertex v = cc.getCCVertex(e.to);
				v.inDegree--;
				if(v.inDegree == 0) {
					q.add(cc.getVertex(v));
				}
			}
		}
		if(topNum != g.v.length)
			throw new IllegalStateException("Exception: Not a DAG");
		return topList;
	}

	public static void main(String args[]) throws FileNotFoundException,IllegalStateException{
		Scanner in;
		if (args.length > 0) {
			File inputFile = new File(args[0]);
			in = new Scanner(inputFile);
		} else {
			in = new Scanner(System.in);
		}
		// passing the scanner object to graph class to construct the graph
		Graph graph = Graph.readGraph(in);

		// if graph is not empty we call the diameter method
		if (graph.v.length > 0 && graph.n > 0) {
			try {
				System.out.println("The Topological Order of the given graph is " + toplogicalOrder1(graph));
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		} else
			System.out.println("Empty Graph");

	}

}
