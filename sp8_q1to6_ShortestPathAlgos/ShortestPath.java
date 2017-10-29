package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp8_q1to6_ShortestPathAlgos;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.TreeMap;

import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp3_q1_TopologicalOrdering.TopologicalOrder;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp3_q4_IsADAG.DFSCheckDAG;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp6_q4and6_PrimAlgo2.IndexedHeap;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Graph;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.GraphAlgorithm;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Graph.Edge;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Graph.Vertex;

public class ShortestPath extends GraphAlgorithm<ShortestPathVertex>{

	public static final int INFINITY = Integer.MAX_VALUE;
	Graph.Vertex src;
	Boolean isPull = false;
	Boolean isDirectedGraph = false;
	/**
	 * Comparator based on priorities of the Vertices to create heap of Vertices.
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
	}


	//refactor
	void reinitialize(Graph.Vertex newSource) {
		src = newSource;
		int index = 0;
		for (Graph.Vertex u : g) {
			ShortestPathVertex su = getVertex(u);
			su.reinitializeVertex(INFINITY, index++);
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
				Graph.Vertex v = e.otherEnd(u);
				if(!v.seen){
					relax(e);
					q.add(v);
				}
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
		reinitialize(src);
		VertexComparator comp = new VertexComparator();
		ShortestPathVertex[] vertexArray = new ShortestPathVertex[node.length];
		System.arraycopy(node, 0, vertexArray, 0, node.length);
		IndexedHeap<ShortestPathVertex> vertexQueue = new IndexedHeap<>(vertexArray, comp, node.length);
		ShortestPathVertex v;
		while (!vertexQueue.isEmpty()) {
			v = vertexQueue.remove();
			v.seen = true;
			for (Graph.Edge e : v.vertex) {
				boolean changed = relax(e);
				if(changed) {
					vertexQueue.decreaseKey(v);
				}
			}
		}
	}

	public boolean bellmanFord() {
		reinitialize(src);
		Queue<ShortestPathVertex> q = new LinkedList<>();
		q.add(getVertex(src));
		int V = g.size();
		while(q.isEmpty()){
			ShortestPathVertex u = q.remove();
			u.seen = false;
			u.count = u.count + 1;
			if(u.count >= V) {
				return false;
			}
			for(Edge e : u.vertex){
				relax(e);
				Vertex v = e.otherEnd(u.vertex);
				if (!v.seen){
					q.add(getVertex(v));
					getVertex(v).seen = true;
				}
			}
		}
		return true;
	}

	public boolean fastestShortestPaths() {
		Boolean hasEqualWeightEdges = true;
		Boolean hasNegativeEdges = false;
		int weight = src.adj.get(0).weight;
		if(weight < 0){
			hasNegativeEdges = true;
		}
		for (Vertex u : g) {
			for(Edge e : u){
				if(weight != e.weight){
					hasEqualWeightEdges = false;
					if(e.weight < 0){
						hasNegativeEdges = true;
					}
				} 
			}
		}
		if(hasEqualWeightEdges && !hasNegativeEdges){
			bfs();
		}
		else if(!hasNegativeEdges){
			dijkstra();
		}
		else if(DFSCheckDAG.isDAG(g)){
			dagShortestPaths();
		}
		else {
			return bellmanFord();
		}
		return true;
	}
	
	public List<Edge> findOddCycle() {
		reinitialize(src);
		bfs();
		List<Edge> edgesOfCycle = new LinkedList<>();
		for (Vertex u : g) {
			for (Edge e : u) {
				Graph.Vertex v = e.otherEnd(u);
				if(getVertex(u).distance == getVertex(v).distance){
					edgesOfCycle.add(e);
					Graph.Vertex p_u; 
					Graph.Vertex p_v;
					do{
						p_u = getVertex(u).parent;
						p_v = getVertex(v).parent;
						Edge e1 = null,e2 = null;
						for (Edge edge : p_u) {
							if(edge.otherEnd(p_u)==u){
								e1 = edge;
								break;
							}
						}
						for (Edge edge : p_v) {
							if(edge.otherEnd(p_v)==v){
								e2 = edge;
								break;
							}
						}
						edgesOfCycle.add(0, e1);
						edgesOfCycle.add(e2);
						
						u = p_u;
						v = p_v;	
					} while(p_u != p_v);
					
					return edgesOfCycle;
				}
			}
		}
		return null;
	}
	
	public TreeMap<Vertex,List<Edge>> printShortestPath(){
		TreeMap<Vertex,List<Edge>> path = new TreeMap<>(); 
		for (Vertex u : g) {
			if(getVertex(u).parent != null){
				Graph.Vertex p;
				List<Edge> lst = new LinkedList<>();
				do{
					p = getVertex(u).parent;
					for (Edge e : p) {
						if(e.otherEnd(p) == u){
							lst.add(e);
							path.put(u, lst);
							break;
						}
					}
					u = p;
				}while(p != src);
			}
		}
		return path;
	}

	public static void main(String[] args) throws FileNotFoundException {
		
		Scanner in;
		boolean isDirectedGraph = true;
		Graph g;
		Graph.Vertex src;
		ShortestPath sp;
		
		if (args.length > 0) {
			File inputFile = new File(args[0]);
			in = new Scanner(inputFile);
		} else {
			in = new Scanner(System.in);
		}
		
		
		if(isDirectedGraph){
			g = Graph.readDirectedGraph(in);
			src = g.getVertex(1);
		}
		else {
			g = Graph.readGraph(in);
			src = g.getVertex(1);
		}
		
		System.out.println("Enter 1 to find shortest path or "
							+ "\n 2 to find odd length cycle in a undirected graph using BFS");
		int a = in.nextInt();
		switch (a) {
		case 1:
			sp = new ShortestPath(g, src);
			if(!sp.fastestShortestPaths()){
				System.out.println("The Graph has negative cycles");
			}
			else{
				System.out.println("");
			}
			
			break;
		case 2:
			if(g.isDirected()){
				System.out.println("Odd Length cycle cannot be found in a directed graph using BFS");
			}
			else {
				sp = new ShortestPath(g, src);	
				List<Edge> edge = sp.findOddCycle();
				if(edge == null){
					System.out.println("The Graph is not bipartite");
				}
				else {
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
