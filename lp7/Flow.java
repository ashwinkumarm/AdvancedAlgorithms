package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.lp7;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.lp7.ResidualGraph.ResidueEdge;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.lp7.ResidualGraph.ResidueVertex;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.BFS;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Graph;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Graph.Edge;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Graph.Vertex;

/**
 *
 * @author Ashwin, Arun, Deepak, Haritha
 *
 */
public class Flow {
	ResidualGraph gf;
	ResidueVertex s, t;
	HashMap<Edge, Integer> capacity;

	public Flow(Graph g, Vertex s, Vertex t, HashMap<Edge, Integer> capacity) {
		gf = new ResidualGraph(g, capacity);
		this.s = gf.getResidueVertexWithName(s.getName());
		this.t = gf.getResidueVertexWithName(t.getName());
		this.capacity = capacity;
	}

	// Return max flow found by Dinitz's algorithm
	public int dinitzMaxFlow() {
		DinitzMaxFlow dinitzMaxFlow = new DinitzMaxFlow(gf, s, t, capacity);
		return dinitzMaxFlow.dinitzMaxFlow();
	}

	// Return max flow found by relabelToFront algorithm
	public int relabelToFront() {
		PreflowRelabelToFront relabelToFront = new PreflowRelabelToFront(gf, s, t, capacity);
		relabelToFront.relabelToFront();
		return t.excess;
	}

	// flow going through edge e
	public int flow(Edge e) {
		return ((ResidueEdge) gf.getEdgeFromGraph(gf.getVertex(e.from), gf.getVertex(e.to))).flow;
	}

	// capacity of edge e
	public int capacity(Edge e) {
		return capacity.get(e);
	}

	/*
	 * After maxflow has been computed, this method can be called to get the
	 * "S"-side of the min-cut found by the algorithm.
	 *
	 * Note-This method must be called only after finding the max flow using
	 * either Dinitz/Preflow-push algorithms.
	 */
	public Set<Vertex> minCutS() {
		// a min-cut { S, T }, where S = { u | u.distance not equals infinity in
		// the final BFS }, and T = V-S
		BFS bfs = new BFS(gf, s);
		bfs.bfs();
		Set<Vertex> minCutS = new HashSet<>();
		for (Vertex v : gf)
			if (bfs.getVertex(v).getDistance() != BFS.INFINITY)
				minCutS.add(v);
		return minCutS;
	}

	/*
	 * After maxflow has been computed, this method can be called to get the
	 * "T"-side of the min-cut found by the algorithm
	 */
	public Set<Vertex> minCutT() {
		BFS bfs = new BFS(gf, s);
		bfs.bfs();
		Set<Vertex> minCutT = new HashSet<>();
		for (Vertex v : gf)
			if (bfs.getVertex(v).getDistance() == BFS.INFINITY)
				minCutT.add(v);
		return minCutT;
	}
}