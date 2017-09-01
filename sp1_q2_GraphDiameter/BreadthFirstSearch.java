package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp1_q2_GraphDiameter;

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Queue;

import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp1_q2_GraphDiameter.Graph.Edge;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp1_q2_GraphDiameter.Graph.Vertex;



/**
 * Class that implements Breadth first Search	 algorithm.
 *  
 * @author Ashwin, Arun, Deepak, Haritha
 *
 */
public class BreadthFirstSearch {
	
	/**
	 * Breadth first algorithm is being used to find the diameter for the 
	 * given graph(Tree).
	 * 
	 * @param g graph
	 * @param v current vertex 
	 * @return path longest path from the currentVertex
	 */
	static LinkedList<Vertex> bfs(Graph g, Vertex initVertex){
		Queue<Vertex> q = new LinkedList<>();
		CC cc = new CC(g);
		cc.getCCVertex(initVertex).parent=null;
		q.add(initVertex);
		
	
		Vertex currentVertex =  null;
		while(!q.isEmpty()){
			currentVertex = q.poll();
			cc.getCCVertex(currentVertex).seen = true;  // if the vertice is visited mark them as true.
			ListIterator<Edge> adjEdges = currentVertex.adj.listIterator(); // gets all the adjacent vertices
			while(adjEdges.hasNext()){
				Vertex adjVertex = adjEdges.next().otherEnd(currentVertex);
				
				// for all adjacent edges, chooses the current adjacent vertices. (i.e) for current vertice 5, let adj edges 
				// be (5,2) this implies the adjacent vertice must be 2.
				
				if(!cc.getCCVertex(adjVertex).seen){
					q.add(adjVertex);
					cc.getCCVertex(adjVertex).parent = currentVertex; // to get the path, we assign next vertice parent as the current vertice
				}
			}
		}
		
		LinkedList<Vertex> path = new LinkedList<>();
		Vertex vertexPath = currentVertex;
		path.add(vertexPath);
		
		// we iterate till the first node(parent is null) and add the vertices to the path list
		while(cc.getCCVertex(vertexPath).parent != null){
			vertexPath = cc.getCCVertex(vertexPath).parent;
			path.add(vertexPath);
		}
		return path;
	}

}
