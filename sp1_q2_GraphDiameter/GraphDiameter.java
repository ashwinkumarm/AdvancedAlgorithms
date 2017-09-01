package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp1_q2_GraphDiameter;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Class GraphDiameter
 * 
 * <P> This class forms the graph and finds the longest path by 
 * running bfs twice.
 * 
 * @author Ashwin, Arun, Deepak, Haritha
 *
 */
public class GraphDiameter {


	/**
	 * Finds the longest path by running bfs.
	 * 
	 * @param g graph
	 * */
	static LinkedList<Graph.Vertex> diameter(Graph g) {
		
		LinkedList<Graph.Vertex> initialPath = BreadthFirstSearch.bfs(g, g.v[0]); // runs  bfs for the first node in the vertices list
		LinkedList<Graph.Vertex> finalPath = BreadthFirstSearch.bfs(g, initialPath.get(0)); // runs bfs for the farthest node from the first vertice
		return finalPath;
	}

	/**
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
		Graph graph = Graph.readGraph(in); // passes the scanner object to graph class to construct the graph
		
		// if graph is not empty we call the diameter method
		if(graph.v.length > 0 && graph.n > 0){
		LinkedList<Graph.Vertex> longestPath = diameter(graph);
		System.out.println(longestPath);
		}else{
			System.out.println("Empty Graph");
		}
	}

}
