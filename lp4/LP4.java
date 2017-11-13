package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.lp4;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp3_q1_TopologicalOrdering.TopoGraph;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp8_q1to6_ShortestPathAlgos.BellmanFordTake1;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp8_q1to6_ShortestPathAlgos.ShortestPath;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Graph;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Graph.Edge;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Graph.Vertex;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.TopologicalOrder;

public class LP4 {

	Graph g;
	Vertex s;
	TopoGraph tg;
	LinkedList<Vertex> topologicalOrder;
	ShortestPath sp;
	int maxRewards = 0;

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

	/**
	 * (Part a) Finds the number of topological orders for the given graph
	 *
	 * @return
	 */
	public long countTopologicalOrders() {
		return countTopologicalOrders(g.getVertexArray(), g.size(), g.size());
	}

	/**
	 * Helper function for counting the number of topological orders for the
	 * given graph
	 *
	 * @param vertexArray
	 * @param c
	 * @param k
	 * @return
	 */
	public long countTopologicalOrders(Vertex[] vertexArray, int c, int k) {
		Long count = 0L;
		if (c == 0)
			count++;
		else {
			int d = k - c;
			for (int i = d; i < k; i++) {
				Vertex v = vertexArray[i];
				if (tg.getVertex(v).inDegree == 0) {
					for (Edge e : v)
						tg.reduceInDegree(e.to);
					Vertex tmp = vertexArray[d];
					vertexArray[d] = vertexArray[i];
					vertexArray[i] = tmp;
					count += countTopologicalOrders(vertexArray, c - 1, k);
					vertexArray[i] = vertexArray[d];
					vertexArray[d] = tmp;
					for (Edge e : v)
						tg.increaseInDegree(e.to);
				}
			}
		}
		return count;
	}

	/**
	 * (Part b) Enumerates and prints all the topological orders for the given
	 * graph and also returns the number of topological orders
	 *
	 * @return
	 */
	public long enumerateTopologicalOrders() {
		topologicalOrder = new LinkedList<>();
		return findAllTopologicalOrders(g.getVertexArray(), g.size(), g.size());
	}

	/**
	 * Helper function for enumerating and printing all the topological orders
	 * for the given graph and also the number of topological orders
	 *
	 * @param topologicalOrder
	 * @param vertexArray
	 * @param c
	 * @param k
	 * @return
	 */
	public long findAllTopologicalOrders(Vertex[] vertexArray, int c, int k) {
		Long count = 0L;
		if (c == 0) {
			for (Vertex v : topologicalOrder) {
				System.out.print(v + " ");
			}
			System.out.println();
			count++;
		} else {
			int d = k - c;
			for (int i = d; i < k; i++) {
				Vertex v = vertexArray[i];
				if (tg.getVertex(v).inDegree == 0) {
					for (Edge e : v) {
						tg.reduceInDegree(e.to);
					}
					topologicalOrder.addLast(v);
					Vertex tmp = vertexArray[d];
					vertexArray[d] = vertexArray[i];
					vertexArray[i] = tmp;
					count += findAllTopologicalOrders(vertexArray, c - 1, k);
					vertexArray[i] = vertexArray[d];
					vertexArray[d] = tmp;
					topologicalOrder.removeLast();
					for (Edge e : v) {
						tg.increaseInDegree(e.to);
					}
				}
			}
		}
		return count;
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
		sp.getVertex(s).spCount = 1;
		for (Vertex p : topologicalOrder) {
			if (p == t)
				break;
			for (Edge e : p) {
				Vertex v = e.otherEnd(p);
				sp.getVertex(v).spCount += sp.getVertex(p).spCount;
			}
		}
		return sp.getVertex(t).spCount;
	}

	/**
	 * (Part c) - Method to return the number of shortest paths from s to t.
	 * Returns -1 if the graph has a negative or zero cycle
	 *
	 * @param t
	 * @return
	 */
	public long countShortestPathsAlternate(Vertex t) {
		Graph h = new Graph(g.size());
		if (checkIfGraphHasNonPositiveCycles(h))
			return -1;
		return countShortestPaths(h.getVertexFromName(s.getName()), h.getVertexFromName(t.getName()));
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
	 * (Part e) - Method to return weight of shortest path from s to t using at
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

	/**
	 * (Part f) - Reward collection problem. Reward for vertices is passed as a
	 * parameter in a hash map tour is empty list passed as a parameter, for
	 * output tour. Return total reward for tour
	 *
	 * @param vertexRewardMap
	 * @param tour
	 * @return
	 */
	public int reward(HashMap<Vertex, Integer> vertexRewardMap, List<Vertex> tour) {
		Graph h = new Graph(g.size());
		h.setDirected(true);
		sp = new ShortestPath(g, s);
		sp.dijkstra();
		createTightGraph(h);
		findRewards(h.getVertexFromName(s.getName()), new LinkedList<Vertex>(), 0, vertexRewardMap, tour);
		return maxRewards;
	}

	/**
	 * Helper method which creates the tight graph H and also checks if the
	 * input graph G has non positive cycles.
	 *
	 * @param h
	 * @return
	 */
	private boolean checkIfGraphHasNonPositiveCycles(Graph h) {
		sp = new ShortestPath(g, s);
		h.setDirected(true);
		if (!sp.bellmanFord() || !createTightGraphAndCheckForCycles(h)) {
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
	private long countShortestPaths(Vertex s, Vertex t) {
		Long count = 0L, Np;
		if (t == s)
			count = 1L;
		else {
			Vertex p;
			for (Edge e : t.revAdj) {
				p = e.otherEnd(t);
				count += ((Np = sp.getVertex(p).spCount) != -1) ? Np : countShortestPaths(s, p);
			}
		}
		sp.getVertex(t).spCount = count;
		return count;
	}

	/**
	 * This method creates a new graph h which contains only the tight edges
	 * [(u,v) - such that v.d = u.d + (u,v).weight] from the input graph g. It
	 * also checks if this graph is acyclic.
	 *
	 * @param h
	 * @return
	 */
	private boolean createTightGraphAndCheckForCycles(Graph h) {
		createTightGraph(h);
		topologicalOrder = (LinkedList<Vertex>) TopologicalOrder.toplogicalOrder1(h);
		return topologicalOrder != null;
	}

	/**
	 * This method creates the tight graph.
	 *
	 * @param h
	 */
	private void createTightGraph(Graph h) {
		for (Vertex u : g) {
			for (Edge e : u) {
				Vertex v = e.otherEnd(u);
				if (sp.getVertex(v).distance == sp.getVertex(u).distance + e.weight)
					h.addEdge(h.getVertexFromName(u.getName()), h.getVertexFromName(v.getName()), e.weight);
			}
		}
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
		path.add(g.getVertexFromName(u.getName()));
		if (u == t) {
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
	 * Recursive method to enumerate all the shortest paths and find the path
	 * with maximum rewards.
	 *
	 * @param u
	 * @param forwardPath
	 * @param totalRewards
	 * @param vertexRewardMap
	 * @param tour
	 */
	private void findRewards(Vertex u, LinkedList<Vertex> forwardPath, int totalRewards,
			HashMap<Vertex, Integer> vertexRewardMap, List<Vertex> tour) {
		forwardPath.add(u);
		totalRewards += vertexRewardMap.get(u);
		for (Edge e : u) {
			Vertex v = e.otherEnd(u);
			g.getVertexFromName(v.getName()).seen = true;
			findRewards(v, forwardPath, totalRewards, vertexRewardMap, tour);
			g.getVertexFromName(v.getName()).seen = false;
		}
		LinkedList<Vertex> traversedVertices = new LinkedList<>();
		LinkedList<Vertex> reversePath = new LinkedList<>();
		if (totalRewards > maxRewards
				&& findPathToSrc(traversedVertices, reversePath, g.getVertexFromName(u.getName()))) {
			maxRewards = totalRewards;
			tour.clear();
			tour.addAll(forwardPath);
			tour.addAll(reversePath);
		}
		resetSeenStatus(traversedVertices);
		forwardPath.removeLast();
	}

	/**
	 * Method to reset the seen status of the input graph.
	 *
	 * @param traversedVertices
	 */
	private void resetSeenStatus(LinkedList<Vertex> traversedVertices) {
		for (Vertex v : traversedVertices)
			v.seen = false;
	}

	/**
	 * Method to check (also record if any) if the given vertex has a path to
	 * the source in the input graph.
	 *
	 * @param traversedVertices
	 * @param reversePath
	 * @param u
	 * @return
	 */
	public boolean findPathToSrc(LinkedList<Vertex> traversedVertices, LinkedList<Vertex> reversePath, Vertex u) {
		if (u == s)
			return true;
		for (Edge e : u) {
			Vertex v = e.otherEnd(u);
			if (!v.seen) {
				v.seen = true;
				traversedVertices.add(v);
				if (findPathToSrc(traversedVertices, reversePath, v)) {
					reversePath.addFirst(v);
					return true;
				}
			}
		}
		return false;
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
