package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.lp7;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.lp7.ResidualGraph.ResidueEdge;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.lp7.ResidualGraph.ResidueVertex;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.BFS;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Graph.Edge;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Graph.Vertex;

/**
 * This class implements the Dinitz maximum flow algorithm.
 *
 * @author Ashwin, Arun, Deepak, Haritha
 *
 */
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
	 * Routine to find the paths in the BFS tree with inceremental levels.
	 *
	 * @param u
	 * @param minFlow
	 * @return
	 */
	private int findAugmentingPaths(ResidueVertex u, int minFlow) {
		if (u == t)
			return minFlow;
		ResidueEdge re;
		int flow, finalFlow = 0;

		while (u.iterator.hasNext()) {
			re = (ResidueEdge) u.iterator.next();
			ResidueVertex v = (ResidueVertex) re.otherEnd(u);
			if (bfs.getVertex(v).getDistance() == bfs.getVertex(u).getDistance() + 1) {
				flow = Math.min(minFlow, re.getResidualCapacity(u));
				finalFlow = findAugmentingPaths(v, flow);
				if (finalFlow > 0) {
					if (re.from == u)
						re.flow += finalFlow;
					else
						re.flow -= finalFlow;
					return finalFlow;
				}
			}
		}
		return finalFlow;
	}

	/**
	 * Edmond-Karp Algorithm
	 *
	 * @return
	 */
	public int findMinimumResidualCapacityAndIncreaseFlow() {
		int minResidualCapacity = Flow.INFINITY, cf;
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
	 * @return maxFlow
	 */
	public int dinitzMaxFlow() {
		int maxFlow = 0, flow;
		while (true) {
			bfs.bfs();
			if (bfs.getVertex(t).getDistance() == Flow.INFINITY)
				break;
			resetIteratorOfVertex();
			while ((flow = findAugmentingPaths(s, Flow.INFINITY)) != 0)
				maxFlow += flow;
			bfs.reinitialize(s);
		}
		return maxFlow;
	}

	/**
	 * Adds a new iterator to each vertex to avoid iteration through all the
	 * adjacent edges repeatedly.
	 */
	private void resetIteratorOfVertex() {
		for (Vertex u : gf) {
			ResidueVertex ru = (ResidueVertex) u;
			ru.iterator = ru.iterator();
		}
	}
}
