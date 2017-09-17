package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp3_q1_TopologicalOrdering;

/**
 * This class performs topological ordering of vertices of a directed graph using two methods
 * 
 * @author Ashwin, Arun, Deepak, Haritha
 *
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.DFS;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Graph;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.GraphVertexColor;

public class TopologicalOrder {
	
	/**
	 * Remove vertices with no incoming edges, one at a
	 * time, along with their incident edges, and add them to a list.
	 * @param g
	 * @return
	 * @throws List<Graph.Vertex>
	 */
	public static List<Graph.Vertex> toplogicalOrder1(Graph g) throws Exception {
		TopoGraph topoGraph = new TopoGraph(g);
		int topNum = 0;
		Queue<Graph.Vertex> q = new LinkedList<Graph.Vertex>();
		List<Graph.Vertex> topList = new ArrayList<Graph.Vertex>();

		for (Graph.Vertex u : g) {
			topoGraph.getVertex(u).inDegree = u.revAdj.size(); // get the number of incoming edges on each vertex
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
					q.add(e.to);
				}
			}
		}
		if (topNum != g.n)// if not all vertices are visited then there is a cycle.
			throw new IllegalStateException("Exception: Not a DAG");
		return topList;
	}

	/**
	 * Run DFS on g and add nodes to the front of the output list,
     * in the order in which they finish.
     * 
	 * @param g
	 * @return List<Graph.Vertex>
	 */
	public static List<Graph.Vertex> toplogicalOrder2(Graph g) {

		Iterator<Graph.Vertex> it = g.iterator();
		DFS dfsTopoGraph = new DFS(g);
		List<Graph.Vertex> decFinishList = new LinkedList<Graph.Vertex>(); // list to store vertices in decrerasing order of their finish time 
		Graph.Vertex u;
		while (it.hasNext()) {
			u = it.next();
			if (dfsTopoGraph.getVertexStatus(u) == GraphVertexColor.WHITE) {
				DFS.cno++;
				if(!dfsTopoGraph.dfsVisitAndIsDAG(u, decFinishList)){
					throw new IllegalStateException("Exception: Not a DAG");
				}
			}
		}
		return decFinishList; 
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
		Graph graph = Graph.readDirectedGraph(in);

		// if graph is not empty we call the topological sort method
		if (graph.n > 0) {
			try {
				System.out.println("The Topological Order of the given graph is " + toplogicalOrder1(graph));
				System.out.println("The DFS finish-time in decreasing order of the given graph  " + toplogicalOrder2(graph));
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		} else
			System.out.println("Empty Graph");

	}

}
