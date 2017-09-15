package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp3_q2_ConnectedComponent;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.DFS;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Graph;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.GraphVertexColor;

public class ConnectedComponentsOfGraph {

	
	public static Graph reverseGraph(Graph g){
		
		Graph reversedGraph = new Graph(g.size());
		for (Graph.Vertex vertex : g) {
			for (Graph.Edge edge : vertex.revAdj) {
				reversedGraph.addEdge(edge.to, edge.from, edge.weight);
			}
		}
		return reversedGraph;
		
	}
	
	public static int stronglyConnectedComponents(Graph g) {

		int cno = 0;
		Iterator<Graph.Vertex> it = g.iterator();
		DFS dfsTopoGraph = new DFS(g);
		List<Graph.Vertex> decFinishList = new LinkedList<Graph.Vertex>();
		Graph.Vertex u;
		while (it.hasNext()) {
			u = it.next();
			if (dfsTopoGraph.getVertexStatus(u) == GraphVertexColor.WHITE) {
				DFS.cno++;
				dfsTopoGraph.dfsVisit(u, decFinishList);
			}
		}
		
		DFS dfsTopoReversedGraph = new DFS(reverseGraph(g));
		List<Graph.Vertex> decFinishList2 = new LinkedList<Graph.Vertex>();
		for (Graph.Vertex v : decFinishList) {
			if (dfsTopoReversedGraph.getVertexStatus(v) == GraphVertexColor.WHITE) {
				DFS.cno++;
				dfsTopoReversedGraph.dfsVisit(v,decFinishList2);
			}
		}
		
		if(decFinishList2.size() == g.size()){
			cno = DFS.cno;
			return cno;
		}
		return cno;
		
	}

	public static void main(String[] args) throws FileNotFoundException {
		
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
			
			int cno = stronglyConnectedComponents(graph);
			if(cno > 0){
				System.out.println("No of Strongly connected components in a given graph: " + cno);
			} else {
				System.out.println("The given graph is not Strongly connected");
			}
			
		} else
			System.out.println("Empty Graph");

	}

}
