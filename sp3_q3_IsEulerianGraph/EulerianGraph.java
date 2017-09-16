package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp3_q3_IsEulerianGraph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp3_q2_ConnectedComponent.ConnectedComponentsOfGraph;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Graph;

public class EulerianGraph {
	
	public static boolean testEulerian(Graph g) {

		if(ConnectedComponentsOfGraph.stronglyConnectedComponents(g) == 0){
			return false;
		}

		for (Graph.Vertex v : g) {
			int indegree = v.revAdj.size();
			int outdegree = v.adj.size();
			
			if(indegree != outdegree){
				return false;
			}
		}	
		return true;  
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
		Graph graph = Graph.readDirectedGraph(in);

		// if graph is not empty we call the topological sort method
		if (graph.n > 0) {
		
			if(testEulerian(graph)){
				System.out.println("The given graph is an Eulerian graph");
			} else {
				System.out.println("The given graph is not an Eulerian graph");
			}
			
		} else
			System.out.println("Empty Graph");

	}

}
