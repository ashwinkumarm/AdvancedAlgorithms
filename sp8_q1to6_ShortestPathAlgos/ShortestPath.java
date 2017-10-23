package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp8_q1to6_ShortestPathAlgos;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Queue;

import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp3_q1_TopologicalOrdering.TopologicalOrder;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Graph;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.GraphAlgorithm;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Graph.Edge;

public class ShortestPath extends GraphAlgorithm<ShortestPath.ShortestPathVertex>{

	public static final int INFINITY = Integer.MAX_VALUE;
	Graph.Vertex src;
	Boolean isPull = false;
	// Class to store information about a vertex in this algorithm
	static class ShortestPathVertex {
		boolean seen;
		Graph.Vertex parent;
		int distance; // distance of vertex from source

		ShortestPathVertex(Graph.Vertex u) {
			seen = false;
			parent = null;
			distance = INFINITY;
		}
	}

	public ShortestPath(Graph g, Graph.Vertex src) {
		super(g);
		this.src = src;
		node = new ShortestPathVertex[g.size()];
		// Create array for storing vertex properties
		for (Graph.Vertex u : g) {
			node[u.getName()] = new ShortestPathVertex(u);
		}
		// Set source to be at distance 0
		getVertex(src).distance = 0;
	}

	// reinitialize allows running BFS many times, with different sources
	void reinitialize(Graph.Vertex newSource) {
		src = newSource;
		for (Graph.Vertex u : g) {
			ShortestPathVertex su = getVertex(u);
			su.seen = false;
			su.parent = null;
			su.distance = INFINITY;
		}
		getVertex(src).distance = 0;
	}
	
	boolean relax (Graph.Edge e ){
		Graph.Vertex u = e.from; 
		Graph.Vertex v = e.to;
		if(distance(v) > distance(u) + 1){
			getVertex(v).distance = distance(u) + 1; 
			getVertex(v).parent = u;
			return true;
		}
		return false;
	}
	
	boolean seen(Graph.Vertex u) {
		return getVertex(u).seen;
	}

	Graph.Vertex getParent(Graph.Vertex u) {
		return getVertex(u).parent;
	}

	int distance(Graph.Vertex u) {
		return getVertex(u).distance;
	}

	
	public void bfs() { 
		Queue<Graph.Vertex> q = new LinkedList<>();
		q.add(src);
		while (!q.isEmpty()) {
			Graph.Vertex u = q.remove();
			for (Graph.Edge e : u) {
				relax(e);
			}
		}
	}
	
	public void dagShortestPaths() { 
		List<Graph.Vertex> topoOrder = TopologicalOrder.toplogicalOrder2(g);
		reinitialize(src);
		for (Graph.Vertex u : topoOrder) {
			List<Edge> edgeList = isPull? u.revAdj:u.adj;
			for (Edge e : edgeList) {
				relax(e);
			}
		}
	}
	
	public void dijkstra() { 
		
	}
	
	public boolean bellmanFord() { 
		
	}
	
	public boolean fastestShortestPaths() { 
		
	}
	
	public List<Edge> findOddCycle() { 
		
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
