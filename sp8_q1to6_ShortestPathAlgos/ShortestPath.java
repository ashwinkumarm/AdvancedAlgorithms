package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp8_q1to6_ShortestPathAlgos;
/**
 * This class finds the shortest path from a given source vertex in a graph
 *
 * @author Ashwin, Arun, Deepak, Haritha
 * @param <T>
 *
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp3_q1_TopologicalOrdering.TopologicalOrder;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp3_q4_IsADAG.DFSCheckDAG;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp6_q4and6_PrimAlgo2.IndexedHeap;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Graph;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.GraphAlgorithm;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Graph.Edge;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Graph.Vertex;

public class ShortestPath extends GraphAlgorithm<ShortestPathVertex> {

	public static final int INFINITY = Integer.MAX_VALUE;
	Graph.Vertex src;
	Boolean isPull = false;

	/**
	 * Comparator based on priorities of the distance of each Vertices to create
	 * heap of Vertices.
	 *
	 * @param <T>
	 */
	static class VertexComparator implements Comparator<ShortestPathVertex> {

		@Override
		public int compare(ShortestPathVertex v1, ShortestPathVertex v2) {
			if (v1.distance > v2.distance)
				return 1;
			else if (v1.distance < v2.distance)
				return -1;
			else
				return 0;
		}
	}

	/**
	 * Constructor to initialize the variable and set the source vertex
	 * 
	 * @param g : graph
	 * @param src : source vertex
	 */
	public ShortestPath(Graph g, Graph.Vertex src) {
		super(g);
		this.src = src;
		node = new ShortestPathVertex[g.size()];
		int index = 0;
		for (Graph.Vertex u : g) {
			node[u.getName()] = new ShortestPathVertex(u, index++, INFINITY);
		}
		// Set source to be at distance 0
		getVertex(src).distance = 0;
		getVertex(src).seen = true;
	}

	/**
	 * Method to reinitialize the graph
	 * 
	 * @param newSource : Graph Vertex : source vertex in the graph
	 */
	void reinitialize(Graph.Vertex newSource) {
		src = newSource;
		int index = 0;
		for (Graph.Vertex u : g) {
			ShortestPathVertex su = getVertex(u);
			su.reinitializeVertex(INFINITY, index++);
		}
		getVertex(src).distance = 0;
		getVertex(src).seen = true;
	}

	/**
	 * Method to update the distance of the vertex if the old distance is greater than the 
	 * current distance
	 * 
	 * @param e : Graph.Edge :  Edge to be relaxed
	 * @return boolean : true if the edge is relaxed else false 
	 */
	boolean relax(Graph.Edge e) {

		Graph.Vertex u = e.from;
		Graph.Vertex v = e.to;
		if (!g.isDirected()) {
			if (seen(v)) {
				Graph.Vertex tmp = u;
				u = v;
				v = tmp;
			}
		}
		if (distance(u) != INFINITY && distance(v) > distance(u) + e.weight) {
			getVertex(v).distance = distance(u) + e.weight;
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

	/**
	 * Method that implements bfs to find the single source shortest path
	 */
	public void bfs() {
		Queue<Graph.Vertex> q = new LinkedList<>();
		q.add(src);
		while (!q.isEmpty()) {
			Graph.Vertex u = q.remove();
			for (Graph.Edge e : u) {
				Graph.Vertex v = e.otherEnd(u);
				if (!seen(v)) {
					relax(e);
					q.add(v);
					getVertex(v).seen = true;
				}
			}
		}
	}
	
	/**
	 * Method that implements DAG for finding the shortest path
	 */
	public void dagShortestPaths() {
		List<Graph.Vertex> topoOrder = TopologicalOrder.toplogicalOrder2(g);
		for (Graph.Vertex u : topoOrder) {
			List<Edge> edgeList = isPull ? u.revAdj : u.adj;
			for (Edge e : edgeList) {
				relax(e);
			}
		}
	}

	/**
	 * Implements Dijksstra's method for finding the shortest path
	 */
	public void dijkstra() {
		VertexComparator comp = new VertexComparator();
		ShortestPathVertex[] vertexArray = new ShortestPathVertex[node.length];
		System.arraycopy(node, 0, vertexArray, 0, node.length);
		IndexedHeap<ShortestPathVertex> vertexQueue = new IndexedHeap<>(vertexArray, comp, node.length);
		ShortestPathVertex u;
		while (!vertexQueue.isEmpty()) {
			u = vertexQueue.remove();
			u.seen = true;
			for (Graph.Edge e : u.vertex) {
				boolean changed = relax(e);
				if (changed) {
					ShortestPathVertex sv = getVertex(e.otherEnd(u.vertex));
					vertexQueue.decreaseKey(sv);
				}
			}
		}
	}

	/**
	 * Implements bellmanFord method for finding the shortest path
	 */
	public boolean bellmanFord() {
		Queue<ShortestPathVertex> q = new LinkedList<>();
		q.add(getVertex(src));
		int V = g.size();
		while (!q.isEmpty()) {
			ShortestPathVertex u = q.remove();
			u.seen = false;
			u.count = u.count + 1;
			if (u.count >= V) {
				return false;
			}
			for (Edge e : u.vertex) {
				if (relax(e)) {
					Vertex v = e.otherEnd(u.vertex);
					if (!v.seen) {
						q.add(getVertex(v));
						getVertex(v).seen = true;
					}
				}
			}
		}
		return true;
	}
	
	/**
	 * This method finds the fastest shortest path algorithm based on the given graph.
	 * If the problem is not solvable because the graph has a negative cycle, return false
	 * 
	 * @return boolean : true if the shortest path can be found else false if the graph has a negative
	 * 					 weight cycle
	 */
	public boolean fastestShortestPaths() {
		Boolean hasEqualWeightEdges = true;
		Boolean hasNegativeEdges = false;
		int weight = src.adj.get(0).weight;
		if (weight < 0) {
			hasNegativeEdges = true;
		}
		outer: for (Vertex u : g) {
			for (Edge e : u) {
				if (!e.seen) {
					if (weight != e.weight) {
						hasEqualWeightEdges = false;
						if (e.weight < 0) {
							hasNegativeEdges = true;
							break outer;
						}
					}
					e.seen = true;
				}
			}
		}
		if (hasEqualWeightEdges && !hasNegativeEdges) {
			bfs();
		} else if (!hasNegativeEdges) {
			dijkstra();
		} else if (g.isDirected() && DFSCheckDAG.isDAG(g)) {
			dagShortestPaths();
		} else {
			return bellmanFord();
		}
		return true;
	}
	
	/**
	 * Method to find the odd length cycle in an undirected graph using BFS
	 * 
	 * @return : List : list of edges in the cycle
	 */
	public List<Edge> findOddCycle() {
		bfs();
		List<Edge> edgesOfCycle = new LinkedList<>();
		for (Vertex u : g) {
			for (Edge e : u) {
				if (!e.seen) {
					Graph.Vertex v = e.otherEnd(u);
					ShortestPathVertex su = getVertex(u);
					ShortestPathVertex sv = getVertex(v);
					if (su.distance == sv.distance) {
						edgesOfCycle.add(e);
						Graph.Vertex p_u;
						Graph.Vertex p_v;
						do {
							p_u = getVertex(u).parent;
							p_v = getVertex(v).parent;
							Edge e1 = null, e2 = null;
							for (Edge edge : p_u) {
								if (edge.otherEnd(p_u) == u) {
									e1 = edge;
									break;
								}
							}
							for (Edge edge : p_v) {
								if (edge.otherEnd(p_v) == v) {
									e2 = edge;
									break;
								}
							}
							edgesOfCycle.add(0, e1);
							edgesOfCycle.add(e2);

							u = p_u;
							v = p_v;
						} while (p_u != p_v);

						return edgesOfCycle;
					}
					e.seen = true;
				}
			}
		}
		return null;
	}

	/**
	 * Method to print all the shortest path
	 */
	public void printShortestPath() {
		HashMap<Vertex, List<Edge>> path = new HashMap<>();
		for (Vertex v : g) {
			if (getVertex(v).parent != null) {
				Graph.Vertex p;
				List<Edge> lst = new LinkedList<>();
				Graph.Vertex u = v;
				do {
					p = getVertex(u).parent;
					for (Edge e : p) {
						if (e.otherEnd(p) == u) {
							lst.add(0, e);
							path.put(v, lst);
							break;
						}
					}
					u = p;
				} while (p != src);
			}
		}
		System.out.println(path);
	}
	
	//Driver Code
	public static void main(String[] args) throws FileNotFoundException {

		Scanner in;
		boolean isDirectedGraph = true; // flag to read directed or undirected graph
		Graph g;
		Graph.Vertex src;
		ShortestPath sp;

		if (args.length > 0) {
			File inputFile = new File(args[0]);
			in = new Scanner(inputFile);
		} else {
			in = new Scanner(System.in);
		}

		if (isDirectedGraph) {
			g = Graph.readDirectedGraph(in);
			src = g.getVertex(1);
		} else {
			g = Graph.readGraph(in);
			src = g.getVertex(1);
		}

		System.out.println("Enter 1 to find shortest path or ");
		System.out.println("2. to find odd length cycle in a undirected graph using BFS");
		int a = in.nextInt();
		switch (a) {
		case 1:
			sp = new ShortestPath(g, src);
			if (!sp.fastestShortestPaths()) {
				System.out.println("The Graph has negative cycles");
			} else {
				sp.printShortestPath();
			}

			break;
		case 2:
			if (g.isDirected()) {
				System.out.println("Odd Length cycle cannot be found in a directed graph using BFS");
			} else {
				sp = new ShortestPath(g, src);
				List<Edge> edge = sp.findOddCycle();
				if (edge == null) {
					System.out.println("The Graph is  bipartite");
				} else {
					System.out.println("The edges of odd length cycle are:");
					System.out.println(edge);
				}
			}
			break;
		default:
			break;
		}

		in.close();

	}

}
