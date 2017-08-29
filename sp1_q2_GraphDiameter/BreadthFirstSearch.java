package cs6301.g12;

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Queue;

import cs6301.g12.Graph.Edge;
import cs6301.g12.Graph.Vertex;

/**
 * Class that implements Breadth first Search algorithm.
 *  
 * @author Ashwin, Arun, Deepak, Haritha
 *
 */
public class BreadthFirstSearch {
	
	/**
	 * Breadth first algorithm is being used to find the diameter for the 
	 * given graph.
	 * 
	 * @param g graph
	 * @param v current vertex 
	 * @return path longest path from the currentVertex
	 */
	static LinkedList<Graph.Vertex> bfs(Graph g, Vertex v){
		Queue<Graph.Vertex> q = new LinkedList<>();
		v.parent = null; 
		q.add(v);
		// boolean array to make sure we dont form a infinite loop by running same vertices again and again.
		boolean[] visited = new boolean[g.size()+1]; 
		visited[v.name+1] = true;
		Vertex currentVertex =  null;
		while(!q.isEmpty()){
			currentVertex = q.poll();
			ListIterator<Edge> adjEdges = currentVertex.adj.listIterator(); // gets all the adjacent vertices
			while(adjEdges.hasNext()){
				Vertex adjVertex = null;
				Edge e = adjEdges.next();
				
				// for all adjacent edges, chooses the current adjacent vertices. (i.e) for current vertice 5, let adj edges 
				// be (5,2) this implies the adjacent vertice must be 2.
				if(e.from == currentVertex){
					adjVertex = e.to;
				}else{
					adjVertex = e.from;
				} 
				if(!visited[adjVertex.name + 1]){
					q.add(adjVertex);
					visited[adjVertex.name + 1] = true; // if the vertice is visited mark them as true.
					adjVertex.parent = currentVertex; // to get the path, we assign next verice parent as the current vertice
				}
			}
		}
		
		LinkedList<Graph.Vertex> path = new LinkedList<>();
		Vertex vertexPath = g.getVertex(currentVertex.name+1);
		path.add(vertexPath);
		
		// we iterate till the first node(parent is null) and add the vertices to the path list
		while(vertexPath.parent != null){
			vertexPath = vertexPath.parent;
			path.add(vertexPath);
		}
		
		return path;
	}

}
