package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp3_q4_IsADAG;

/**
 * This class checks whether if a directed graph has a cycle or not 
 * 
 * @author Ashwin, Arun, Deepak, Haritha
 *
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.DFS;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Graph;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.GraphVertexColor;

public class DFSCheckDAG {

	/**
	 * This method runs DFS on the given graph checks if a vertex already in the recursion 
	 * stack is being visited again
	 * 
	 * @param g
	 * @return boolean
	 */
	
	public static boolean isDAG(Graph g) { 
		
		Iterator<Graph.Vertex> it = g.iterator();
		DFS dfsGraph = new DFS(g);
		List<Graph.Vertex> decFinishList = new LinkedList<Graph.Vertex>();
		Graph.Vertex u;
		while (it.hasNext()) {
			u = it.next();
			if (dfsGraph.getVertexStatus(u) == GraphVertexColor.WHITE) {
				DFS.cno++;
				if(!dfsGraph.dfsVisitAndIsDAG(u, decFinishList)){
					return false;
				}
			}
		}
		
		return true;
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
			if(isDAG(graph)){
				System.out.println("The given graph is a DAG");
			} else {
				System.out.println("The given graph is not a DAG");
			}
			
		} else
			System.out.println("Empty Graph");

	}
}