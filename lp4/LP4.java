package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.lp4;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp3_q1_TopologicalOrdering.TopoGraph;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Graph;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Graph.Edge;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Graph.Vertex;

public class LP4 {

	Graph g;
	Vertex s;
	TopoGraph tg;

	// common constructor for all parts of LP4: g is graph, s is source vertex
	public LP4(Graph g, Vertex s) {
		this.g = g;
		this.s = s;
		initializeTopoGraph(g);
	}
	
	public void initializeTopoGraph(Graph g){
		tg = new TopoGraph(g);
		for (Graph.Vertex u : g) {
			tg.getVertex(u).inDegree = u.revAdj.size(); 
		}
	}

	// Part a. Return number of topological orders of g
	public long countTopologicalOrders() {
		// To do
		return 0;
	}

	// Part b. Print all topological orders of g, one per line, and
	// return number of topological orders of g
	public long enumerateTopologicalOrders() {
		LinkedList<Vertex> topologicalOrder = new LinkedList<>();
		LinkedList<LinkedList<Vertex>> allTopOrder = new LinkedList<>();
		findAllTopologicalOrders(topologicalOrder, allTopOrder);
		return allTopOrder.size();
	}
	
	public void findAllTopologicalOrders(LinkedList<Vertex> topologicalOrder, LinkedList<LinkedList<Vertex>> allTopOrder) {
		boolean flag = false;
		for (Vertex v : g) {
			if (tg.getVertex(v).inDegree == 0 && !tg.getVertex(v).seen) {
				for (Edge e : v) {
					tg.reduceInDegree(e.to);
				}

				topologicalOrder.addLast(v);
				tg.getVertex(v).seen = true;
				findAllTopologicalOrders(topologicalOrder, allTopOrder);
				tg.getVertex(v).seen = false;
				topologicalOrder.removeLast();
				for (Edge e : v) {
					tg.increaseInDegree(e.to);
				}
				flag = true;
			}
		}

		if (!flag) {
			for (Vertex v : topologicalOrder) {
				System.out.print(v);
			}
			allTopOrder.add(topologicalOrder);
			System.out.println();
		}
	}

	// Part c. Return the number of shortest paths from s to t
	// Return -1 if the graph has a negative or zero cycle
	public long countShortestPaths(Vertex t) {
		// To do
		return 0;
	}

	// Part d. Print all shortest paths from s to t, one per line, and
	// return number of shortest paths from s to t.
	// Return -1 if the graph has a negative or zero cycle.
	public long enumerateShortestPaths(Vertex t) {
		// To do
		return 0;
	}

	// Part e. Return weight of shortest path from s to t using at most k edges
	public int constrainedShortestPath(Vertex t, int k) {
		// To do
		return 0;
	}

	// Part f. Reward collection problem
	// Reward for vertices is passed as a parameter in a hash map
	// tour is empty list passed as a parameter, for output tour
	// Return total reward for tour
	public int reward(HashMap<Vertex, Integer> vertexRewardMap, List<Vertex> tour) {
		// To do
		return 0;
	}

	// Do not modify this function
	static void printGraph(Graph g, HashMap<Vertex, Integer> map, Vertex s, Vertex t, int limit) {
		System.out.println("Input graph:");
		for (Vertex u : g) {
			if (map != null) {
				System.out.print(u + "($" + map.get(u) + ")\t: ");
			} else {
				System.out.print(u + "\t: ");
			}
			for (Edge e : u) {
				System.out.print(e + "[" + e.weight + "] ");
			}
			System.out.println();
		}
		if (s != null) {
			System.out.println("Source: " + s);
		}
		if (t != null) {
			System.out.println("Target: " + t);
		}
		if (limit > 0) {
			System.out.println("Limit: " + limit + " edges");
		}
		System.out.println("___________________________________");
	}
}
