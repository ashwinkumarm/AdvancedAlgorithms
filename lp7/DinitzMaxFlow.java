package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.lp7;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.lp7.ResidualGraph.ResidueEdge;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.lp7.ResidualGraph.ResidueVertex;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.BFS;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Graph.Edge;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Graph.Vertex;

public class DinitzMaxFlow {
	ResidualGraph gf;
	ResidueVertex s, t;
	HashMap<Edge, Integer> capacity;
	BFS bfs;

	/**
	 * @param gf
	 * @param s
	 * @param t
	 * @param capacity
	 */
	public DinitzMaxFlow(ResidualGraph gf, ResidueVertex s, ResidueVertex t, HashMap<Edge, Integer> capacity) {
		this.gf = gf;
		this.s = s;
		this.t = t;
		this.capacity = capacity;
		bfs = new BFS(gf, s);
	}

	/**
	 * @param u
	 * @param minFlow
	 * @return
	 */
	private int findAugmentingPaths(ResidueVertex u, int minFlow) {
		if (u == t)
			return minFlow;
		ResidueEdge re;
		for (Edge e : u) {
			re = (ResidueEdge) e;
			ResidueVertex v = (ResidueVertex) e.otherEnd(u);
			if (bfs.getVertex(v).getDistance() == bfs.getVertex(u).getDistance() + 1 && re.inGf(u)) {
				int flow = Math.min(minFlow, re.getResidualCapacity(u));
				int finalFlow = findAugmentingPaths(v, flow);
				if (finalFlow > 0) {
					if (re.from == u)
						re.flow += finalFlow;
					else
						re.flow -= finalFlow;
					return finalFlow;
				}
			}
		}
		return 0;
	}

	/**
	 * Edmond-Karp Algorithm
	 *
	 * @return
	 */
	public int findMinimumResidualCapacityAndIncreaseFlow() {
		int minResidualCapacity = Integer.MAX_VALUE, cf;
		Vertex u, v = t;
		ResidueEdge e;
		List<ResidueEdge> path = new LinkedList<>();
		while (v != null) {
			u = bfs.getParent(v);
			e = (ResidueEdge) gf.getEdgeFromGraph(gf.getVertex(u), gf.getVertex(v));
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
	 * Dinitz max flow algorithm.
	 *
	 * @return
	 */
	public int dinitzMaxFlow() {
		int maxFlow = 0, flow;
		while (true) {
			bfs.bfs();
			if (bfs.getVertex(t).getDistance() == Integer.MAX_VALUE)
				break;
			// maxFlow += findMinimumResidualCapacityAndIncreaseFlow();
			while ((flow = findAugmentingPaths(s, Integer.MAX_VALUE)) != 0)
				// TODO: Use iterator
				maxFlow += flow;
			bfs.reinitialize(s);
		}
		return maxFlow;
	}
}
