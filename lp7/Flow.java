package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.lp7;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.lp7.ResidualGraph.ResidueEdge;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.lp7.ResidualGraph.ResidueVertex;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.BFS;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Graph;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Graph.Edge;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Graph.Vertex;

/**
 * @author Arun
 *
 */
public class Flow {
	ResidualGraph gf;
	ResidueVertex s, t;
	HashMap<Edge, Integer> capacity;
	BFS bfs;

	public Flow(Graph g, Vertex s, Vertex t, HashMap<Edge, Integer> capacity) {
		gf = new ResidualGraph(g, capacity);
		this.s = gf.getResidueVertexWithName(s.getName());
		this.t = gf.getResidueVertexWithName(t.getName());
		this.capacity = capacity;
		bfs = new BFS(gf, s);
	}

	// Return max flow found by Dinitz's algorithm
	public int dinitzMaxFlow() {
		int maxFlow = 0;
		while (true) {
			bfs.bfs();
			if (bfs.getVertex(t).getDistance() == Integer.MAX_VALUE)
				break;
			maxFlow += findMinimumResidualCapacityAndIncreaseFlow();
			maxFlow += findAugmentingPaths(s, Integer.MAX_VALUE);
			bfs.reinitialize(s);
		}
		return maxFlow;
	}

	private int findAugmentingPaths(ResidueVertex u, int minFlow) {
		if (u == t)
			return minFlow;
		ResidueEdge re;
		for (Edge e : u) {
			re = (ResidueEdge) e;
			ResidueVertex v = (ResidueVertex) e.otherEnd(u);
			if (bfs.getVertex(v).getDistance() == bfs.getVertex(u).getDistance() + 1 && re.inGf(u)) {
				minFlow = Math.min(minFlow, re.getResidualCapacity(u));
				int flow = findAugmentingPaths(v, minFlow);
				if (flow > 0) {
					if (re.from == u)
						re.flow += flow;
					else
						re.flow -= flow;
					return flow;
				}
			}
		}
		return 0;
	}

	/**
	 * Used in Edmond-Karp Algorithm
	 *
	 * @return
	 */
	private int findMinimumResidualCapacityAndIncreaseFlow() {
		int minResidualCapacity = Integer.MAX_VALUE, cf;
		Vertex u, v = t;
		ResidueEdge e;
		List<ResidueEdge> path = new LinkedList<>();
		while (v != null) {
			u = bfs.getParent(v);
			e = (ResidueEdge) getEdgeFromGraph(gf.getVertex(u), gf.getVertex(v));
			if ((cf = e.getResidualCapacity(u)) < minResidualCapacity)
				minResidualCapacity = cf;
			v = u;
			path.add(e);
		}
		u = t;
		for (ResidueEdge edge : path) {
			if (edge.to == u)
				edge.flow += minResidualCapacity;
			else
				edge.flow -= minResidualCapacity;
			u = edge.from;
		}
		return minResidualCapacity;
	}

	/**
	 * This method gets the original edge from the graph.
	 *
	 * @param parent
	 * @param vertex
	 * @param g
	 * @return
	 */
	private Edge getEdgeFromGraph(Vertex parent, Vertex vertex) {
		for (Edge e : parent)
			if (e.to.getName() == vertex.getName())
				return e;
		return null;
	}

	// Return max flow found by relabelToFront algorithm
	public int relabelToFront() {
		PreflowRelabelToFront relabelToFront = new PreflowRelabelToFront(gf, s, t, capacity);
		relabelToFront.relabelToFront();
		return 0;
	}

	// flow going through edge e
	public int flow(Edge e) {
		return 0;
	}

	// capacity of edge e
	public int capacity(Edge e) {
		return 0;
	}

	/*
	 * After maxflow has been computed, this method can be called to get the
	 * "S"-side of the min-cut found by the algorithm
	 */
	public Set<Vertex> minCutS() {
		// a min-cut { S, T }, where S = { u | u.distance not equals infinity in
		// the final BFS }, and T = V-S
		return null;
	}

	/*
	 * After maxflow has been computed, this method can be called to get the
	 * "T"-side of the min-cut found by the algorithm
	 */
	public Set<Vertex> minCutT() {
		return null;
	}
}