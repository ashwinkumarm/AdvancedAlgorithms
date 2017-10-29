package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.lp3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.lp3.DMSTGraph.DMSTEdge;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.lp3.DMSTGraph.DMSTVertex;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.DFS;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Graph;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Graph.Edge;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Graph.Vertex;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Timer;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.DFS.DFSVertex;

public class LP3 {
	static int VERBOSE = 0;

	public static void main(String[] args) throws FileNotFoundException {
		Scanner in;
		if (args.length > 0) {
			File inputFile = new File(args[0]);
			in = new Scanner(inputFile);
		} else {
			in = new Scanner(System.in);
		}
		if (args.length > 1) {
			VERBOSE = Integer.parseInt(args[1]);
		}

		int start = in.nextInt(); // root node of the MST
		Graph g = Graph.readDirectedGraph(in);
		Vertex startVertex = g.getVertex(start);
		List<Edge> dmst = new ArrayList<>();

		Timer timer = new Timer();
		int wmst = directedMST(g, startVertex, dmst);
		isValidMst(g, startVertex, dmst); 
		
		timer.end();

		System.out.println(wmst);
		if (VERBOSE > 0) {
			System.out.println("_________________________");
			for (Edge e : dmst) {
				System.out.print(e);
			}
			System.out.println();
			System.out.println("_________________________");
		}
		System.out.println(timer);
	}

	/**
	 * TO DO: List dmst is an empty list. When your algorithm finishes, it
	 * should have the edges of the directed MST of g rooted at the start
	 * vertex. Edges must be ordered based on the vertex into which it goes,
	 * e.g., {(7,1),(7,2),null,(2,4),(3,5),(5,6),(3,7)}. In this example, 3 is
	 * the start vertex and has no incoming edges. So, the list has a null
	 * corresponding to Vertex 3. The function should return the total weight of
	 * the MST it found.
	 */
	public static int directedMST(Graph g, Vertex start, List<Edge> dmst) {
		DMSTGraph dmstGraph = new DMSTGraph(g);
		FindDirectedMst findMst = new FindDirectedMst();
		int originalSize = dmstGraph.size();
		findMst.minMst(dmstGraph, dmstGraph.getVertex(start), 0);
		int wmst = 0;
		DMSTEdge dmstEdge;
		Edge treeEdge;
		DMSTVertex[] dmstVertexArray = dmstGraph.getDMSTVertexArray();
		for (int i = 0; i < originalSize; i++) {
			dmstEdge = dmstVertexArray[i].incomingEdge;
			if (dmstEdge != null) {
				treeEdge = FindDirectedMst.getEdgeFromGraph(g.getVertex(dmstEdge.from.getName() + 1),
						g.getVertex(dmstEdge.to.getName() + 1));
				wmst += treeEdge.weight;
				dmst.add(treeEdge);
			} else
				dmst.add(null);
		}
		System.out.println(dmst);
		return wmst;
	}
	
	public static boolean isValidMst(Graph g, Vertex start, List<Edge> dmst){
		DMSTGraph dmstGraph = new DMSTGraph(g);
		for(DMSTVertex v : dmstGraph.getDMSTVertexArray()){
			for(DMSTEdge e: v.DMSTadj){
				if(!dmst.contains(e)){
					e.disabled();
				}
			}
		}
		//Step 1
		List<DFSVertex> components = new LinkedList<DFSVertex>();
		DFS dfs = new DFS(g);
		dfs.dfsVisit(start, components);
		if (!(components.size() == g.size())){
			return false;
		}
		//Step 2
		int mstWeight = 0;
		for(Edge e: dmst){
			mstWeight += e.weight;
		}
		int minIncomingSum = 0;
		for(DMSTVertex v : dmstGraph.getDMSTVertexArray()){
			minIncomingSum += v.minEdge;
		}
		if(mstWeight != minIncomingSum){
			return false;
		}
		
		//Step 3
		//Step 4
		return true;

		
	}
}
