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

public class Flow {
	ResidualGraph gf;
	Vertex s, t;
	BFS bfs;

	public Flow(Graph g, Vertex s, Vertex t, HashMap<Edge, Integer> capacity) {
		gf = new ResidualGraph(g, capacity);
		this.s = s;
		this.t = t;
		bfs = new BFS(gf, s);
	}

	// Return max flow found by Dinitz's algorithm
	public int dinitzMaxFlow() {
		// for e in E do f( e ) = 0
		while (true) {
			bfs.bfs();
			if (bfs.getVertex(t).getDistance() == Integer.MAX_VALUE)
				break;
			findMinimumResidualCapacityAndIncreaseFlow();
		}
		// return maxflow
		return 0;
	}

	private void findMinimumResidualCapacityAndIncreaseFlow() {
		int minResidualCapacity = Integer.MAX_VALUE;
		Vertex u, v = t;
		ResidueEdge e;
		List<ResidueEdge> path = new LinkedList<>();
		while (v != null) {
			u = bfs.getParent(v);
			e = (ResidueEdge) getEdgeFromGraph(gf.getVertex(u), gf.getVertex(v));
			if (e.residualCapacity < minResidualCapacity)
				minResidualCapacity = e.residualCapacity;
			v = u;
			path.add(e);
		}
		for (ResidueEdge edge : path) {
			if (!edge.isResidualEdge) {
				edge.flow += minResidualCapacity;
				edge.residualCapacity -= minResidualCapacity;
			} else {
				// f( e^R ) = f( e^R ) - cmin
			}
		}
	}

	/**
	 * This method gets the original edge from the graph.
	 *
	 * @param parent
	 * @param vertex
	 * @param g
	 * @return
	 */
	private Edge getEdgeFromGraph(ResidueVertex parent, ResidueVertex vertex) {
		for (Edge e : parent)
			if (e.to.getName() == vertex.getName())
				return e;
		return null;
	}

	// Return max flow found by relabelToFront algorithm
	public int relabelToFront() {
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