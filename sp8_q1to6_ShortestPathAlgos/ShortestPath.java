package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp8_q1to6_ShortestPathAlgos;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp3_q1_TopologicalOrdering.TopologicalOrder;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp6_q4and6_PrimAlgo2.IndexedHeap;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Graph;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.GraphAlgorithm;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Graph.Edge;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Graph.Vertex;

public class ShortestPath extends GraphAlgorithm<ShortestPathVertex>{

	public static final int INFINITY = Integer.MAX_VALUE;
	Graph.Vertex src;
	Boolean isPull = false;


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
		
	}

	public List<Edge> findOddCycle() {

	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
