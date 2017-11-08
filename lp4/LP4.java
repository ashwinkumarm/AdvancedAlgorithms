package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.lp4;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp3_q1_TopologicalOrdering.TopoGraph;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp3_q4_IsADAG.DFSCheckDAG;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp8_q1to6_ShortestPathAlgos.BellmanFordTake1;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp8_q1to6_ShortestPathAlgos.ShortestPath;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Graph;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.DFS.DFSVertex;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Graph.Edge;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Graph.Vertex;

public class LP4 {

	Graph g;
	Vertex s;
	TopoGraph tg;
	ShortestPath sp;
	int maxRewards = 0;
	List<List<Vertex>> shortestPaths = new LinkedList<>();

	// common constructor for all parts of LP4: g is graph, s is source vertex
	public LP4(Graph g, Vertex s) {
		this.g = g;
		this.s = s;
		initializeTopoGraph(g);
	}

	public void initializeTopoGraph(Graph g) {
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

	public void findAllTopologicalOrders(LinkedList<Vertex> topologicalOrder,
			LinkedList<LinkedList<Vertex>> allTopOrder) {
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
				System.out.print(v + " ");
			}
			allTopOrder.add(topologicalOrder);
			System.out.println();
		}
	}

	/**
	 * (Part c) - Method to return the number of shortest paths from s to t.
	 * Returns -1 if the graph has a negative or zero cycle
	 *
	 * @param t
	 * @return
	 */
	public long countShortestPaths(Vertex t) {
		Graph h = new Graph(g.size());
		if (checkIfGraphHasNonPositiveCycles(h))
			return -1;
		return countShortestPaths(h.getVertexFromName(s.getName()), h.getVertexFromName(t.getName()), new HashMap<>());
	}

	/**
	 * (Part d) - Method to print all shortest paths from s to t, one per line,
	 * and return number of shortest paths from s to t. Return -1 if the graph
	 * has a negative or zero cycle.
	 *
	 * @param t
	 * @return
	 */
	public long enumerateShortestPaths(Vertex t) {
		Graph h = new Graph(g.size());
		if (checkIfGraphHasNonPositiveCycles(h))
			return -1;
		return printAllThePaths(h.getVertexFromName(s.getName()), h.getVertexFromName(t.getName()), new LinkedList<>(),
				0);
	}

	/**
	 * Helper method which creates the tight graph H and also checks if the
	 * input graph G has non positive cycles.
	 *
	 * @param h
	 * @return
	 */
	private boolean checkIfGraphHasNonPositiveCycles(Graph h) {
		ShortestPath sp = new ShortestPath(g, s);
		h.setDirected(true);
		if (!sp.bellmanFord() || !createTightGraphAndCheckForCycles(sp, h)) {
			System.out.println("Non-positive cycle in graph. Unable to solve problem.");
			return true;
		}
		return false;
	}

	/**
	 * Recursive method to count the number of shortest path in the topological
	 * order.
	 *
	 * @param s
	 * @param t
	 * @param map
	 * @return
	 */
	private long countShortestPaths(Vertex s, Vertex t, HashMap<Vertex, Long> map) {
		Long count = 0L, Np;
		if (t == s)
			count = 1L;
		else {
			Vertex p;
			for (Edge e : t.revAdj) { 
				p = e.otherEnd(t);
				count += ((Np = map.get(p)) != null) ? Np : countShortestPaths(s, p, map);
			}
		}
		map.put(t, count); // memoization
		return count;
	}

	/**
	 * This method creates a new graph h which contains only the tight edges
	 * [(u,v) - such that v.d = u.d + (u,v).weight] from the input graph g. It
	 * also finds the topological order of h and checks if this graph is
	 * acyclic.
	 *
	 * @param sp
	 * @param h
	 * @return
	 */
	private boolean createTightGraphAndCheckForCycles(ShortestPath sp, Graph h) {
		for (Vertex u : g) {
			for (Edge e : u) {
				Vertex v = e.otherEnd(u);
				if (sp.getVertex(v).distance == sp.getVertex(u).distance + e.weight)
					h.addEdge(h.getVertexFromName(u.getName()), h.getVertexFromName(v.getName()), e.weight);
			}
		}
		return DFSCheckDAG.isDAG(h);
	}

	/**
	 * Recursive method to print all the paths between s and t in the Graph H
	 * (tight graph).
	 *
	 * @param u
	 * @param t
	 * @param path
	 * @param count
	 * @return
	 */
	private int printAllThePaths(Vertex u, Vertex t, LinkedList<Vertex> path, int count) {
		path.add(u);
		if (u == t) {
			shortestPaths.add(path);
			for (Vertex v : path)
				System.out.print(v + " ");
			System.out.println();
			count++;
		} else
			for (Edge e : u) {
				Vertex v = e.otherEnd(u);
				count = printAllThePaths(v, t, path, count);
			}
		path.removeLast();
		return count;
	}

	/**
	 * Part e - Method to return weight of shortest path from s to t using at
	 * most k edges by using Bellamn-Ford Take 1 algorithm.
	 *
	 * @param t
	 * @param k
	 * @return
	 */
	public int constrainedShortestPath(Vertex t, int k) {
		BellmanFordTake1 bf = new BellmanFordTake1(g);
		bf.findShortestPathUsingAtmostKEdges(g, k, s);
		return bf.getVertex(t).distance;
	}

	// Part f. Reward collection problem
	// Reward for vertices is passed as a parameter in a hash map
	// tour is empty list passed as a parameter, for output tour
	// Return total reward for tour
	public int reward(HashMap<Vertex, Integer> vertexRewardMap, List<Vertex> tour) {
		
		PriorityQueue<RewardPath> q = new PriorityQueue<>(Collections.reverseOrder());
		for (Vertex u : g) {
			enumerateShortestPaths(u);
			for (List<Vertex> lst : shortestPaths) {
				RewardPath rp = new RewardPath();
				rp.path.addAll(lst);
				for (Vertex v : lst) {
					rp.totalRewards += vertexRewardMap.get(v);
				}
				q.add(rp);
			}
			shortestPaths.clear();
		}
		
		while(!q.isEmpty()){
			RewardPath rp = q.poll();
			resetVisitedStatus(rp.path);
			Vertex lastVertexInPath = rp.path.get(rp.path.size());
			if(findPathToSrc(rp.path, lastVertexInPath)){
				tour.addAll(rp.path);
				return rp.totalRewards;
			}
			resetVisitedStatus(rp.path);
		}
		
		return 0;
	}

	public void resetVisitedStatus(List<Vertex> path){
		for (Vertex u : path) {
			u.seen = !u.seen;
		}
	}
	
	public boolean findPathToSrc(List<Vertex> path, Vertex u) {
	
		if(u != s){
			for (Edge e : u) {
				Vertex v = e.otherEnd(u);
				if (!v.seen ) {
					path.add(v);
					findPathToSrc(path, v);
				}
			}
			return false;
		}
		else{
			return true;
		}
		
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
