
// change following line to your group number
package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.lp2;

import java.util.List;

import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.*;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Graph.Edge;

import java.util.LinkedList;

public class Euler extends GraphAlgorithm<Euler.EulerVertex>{
	int VERBOSE;
	List<Graph.Edge> tour;
	Graph.Vertex startVertex;

	static class EulerVertex {

		Graph.Vertex element;
		List<Graph.Edge> subTour;

		EulerVertex(Graph.Vertex u) {
			element = u;
			subTour = new LinkedList<>();
		}

	}
	
	// Constructor
	public Euler(Graph g, Graph.Vertex start) {
		super(g);
		startVertex = start;
		node = new EulerVertex[g.size()];
		for (Graph.Vertex u : g) {
			node[u.getName()] = new EulerVertex(u);
		}
		VERBOSE = 1;
		tour = new LinkedList<>();
	}

	// To do: function to find an Euler tour
	public List<Graph.Edge> findEulerTour() {
		findTours();
		if (VERBOSE > 0) {
			printTours();
		}
		//stitchTours();
		return tour;
	}

	/*
	 * To do: test if the graph is Eulerian. If the graph is not Eulerian, it
	 * prints the message: "Graph is not Eulerian" and one reason why, such as
	 * "inDegree = 5, outDegree = 3 at Vertex 37" or
	 * "Graph is not strongly connected"
	 */
	public boolean isEulerian() {
		
		//check whether the given graph is strongly connected or not
		if(ConnectedComponentsOfGraph.stronglyConnectedComponents(g) == 0){
			System.out.println("Graph is not Eulerian");
			System.out.println("Reason: Graph is not strongly connected");
			return false;
		}

		// check if indegree == outdegree at every vertex 
		for (Graph.Vertex v : g) {
			int indegree = v.revAdj.size();
			int outdegree = v.adj.size();
			
			if(indegree != outdegree){
				System.out.println("Graph is not Eulerian");
				System.out.println("Reason: the indegree("+indegree+") is not equal to outdegree("+outdegree+") at vertex "+ v.toString());
				return false;
			}
		}	
		return true;  
	}

	void findTours(){
		findTours(startVertex, getSubTour(startVertex));
		for (Graph.Vertex u : g) {
			findTours(u,getSubTour(u));
		}
	}
	// Find tours starting at vertices with unexplored edges
	void findTours(Graph.Vertex u, List<Graph.Edge> subTour) {

		for (Graph.Edge e : u.adj) {
			if(!e.seen){
				subTour.add(e);
				e.seen = true;
				Graph.Vertex v = e.otherEnd(u);
				findTours(v, subTour);
			}
		}

	}

	/*
	 * Print tours found by findTours() using following format: Start vertex of
	 * tour: list of edges with no separators Example: lp2-in1.txt, with start
	 * vertex 3, following tours may be found. 3:
	 * (3,1)(1,2)(2,3)(3,4)(4,5)(5,6)(6,3) 4: (4,7)(7,8)(8,4) 5: (5,7)(7,9)(9,5)
	 *
	 * Just use System.out.print(u) and System.out.print(e)
	 */
	void printTours() {
		for (Graph.Vertex u : g) {
			if(!getSubTour(u).isEmpty()){
				System.out.println(u.toString()+": "+ getSubTour(u));
			}
		}
	}

	// Stitch tours into a single tour using the algorithm discussed in class
	void stitchTours() {
	}

	void setVerbose(int v) {
		VERBOSE = v;
	}
	
	List<Graph.Edge> getSubTour(Graph.Vertex u){
		return getVertex(u).subTour;
	}
}
