package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp1_q2_GraphDiameter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp1_q2_GraphDiameter.Graph.Vertex;

/**
 * Class GraphDiameter
 *
 * <P>
 * This class forms the graph and finds the longest path by running bfs twice.
 *
 * @author Ashwin, Arun, Deepak, Haritha
 *
 */
public class GraphDiameter {

	/**
	 * Finds the longest path by running bfs.
	 *
	 * @param g
	 *
	 */
	static LinkedList<Graph.Vertex> diameter(Graph g) {
		CC cc = new CC(g);
		// runs bfs for the first node in the vertex list
		Vertex lastVertex1 = BreadthFirstSearch.doBFSAndReturnFarthestNode(cc, g, g.v[0]);
		
		cc = new CC(g);
		// runs bfs for the farthest node from the first vertex
		Vertex lastVertex2 = BreadthFirstSearch.doBFSAndReturnFarthestNode(cc, g, lastVertex1);
		return getDiameterPath(cc, lastVertex2);
	}

	/**
	 * This method finds the path from the given node to its farthest node.
	 *
	 * @param cc
	 * @param g
	 * @param initVertex
	 * @return
	 */
	static LinkedList<Vertex> getDiameterPath(CC cc, Vertex startVertex) {
		LinkedList<Vertex> path = new LinkedList<>();
		path.add(startVertex);

		// we iterate till the first node(parent is null) and add the vertices
		// to the path list
		while (cc.getCCVertex(startVertex).parent != null) {
			startVertex = cc.getCCVertex(startVertex).parent;
			path.add(startVertex);
		}
		return path;
	}

	/**
	 * Main method where the execution starts.
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
		// passing the scanner object to graph class to construct the graph
		Graph graph = Graph.readGraph(in);

		// if graph is not empty we call the diameter method
		if (graph.v.length > 0 && graph.n > 0){
			System.out.print("The longest path in the given graph is ");
			System.out.print(diameter(graph));
		}
		else
			System.out.println("Empty Graph");
	}

}
