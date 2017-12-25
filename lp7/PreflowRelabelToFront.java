package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.lp7;

import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Queue;

import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Graph.Edge;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.ResidualGraph;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.ResidualGraph.ResidueEdge;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.ResidualGraph.ResidueVertex;

/**
 * This class implements Preflow-push relabel to front algorithm to find the
 * maximum flow in the given graph.
 *
 * @author Ashwin, Arun, Deepak, Haritha
 *
 */
public class PreflowRelabelToFront {

	ResidualGraph gf;
	ResidueVertex s, t;
	HashMap<Edge, Integer> capacity;
	Comparator<ResidueVertex> c = new PriorityComp();
	// Queue to hold vertices with excess > 0
	Queue<ResidueVertex> q = new PriorityQueue<>(c);
	int maxPriority = 1;

	static class PriorityComp implements Comparator<ResidueVertex> {

		@Override
		public int compare(ResidueVertex v1, ResidueVertex v2) {
			if (v1.getPriority() > v2.getPriority())
				return 1;
			else if (v1.getPriority() < v2.getPriority())
				return -1;
			else
				return 0;
		}
	}

	/**
	 * @param gf
	 * @param s
	 * @param t
	 * @param capacity
	 */
	public PreflowRelabelToFront(ResidualGraph gf, ResidueVertex s, ResidueVertex t, HashMap<Edge, Integer> capacity) {
		super();
		this.gf = gf;
		this.s = s;
		this.t = t;
		this.capacity = capacity;
	}

	/**
	 * Initializes the height to inital values and pushes the initial flow from
	 * the source.
	 */
	private void initialize() {
		s.height = gf.size();
		for (Edge e : s) {
			ResidueVertex ru = (ResidueVertex) e.otherEnd(s);
			((ResidueEdge) e).flow = capacity.get(e);
			s.excess = s.excess - capacity.get(e);
			ru.excess = ru.excess + capacity.get(e);
			if (e.to != s && e.to != t)
				q.add((ResidueVertex) e.to);
		}
	}

	/**
	 * Pushes flow out of u using edge ( u, v ) in Gf , where e = ( u, v ) or (
	 * v, u )
	 *
	 * @param u
	 * @param v
	 * @param e
	 */
	private void push(ResidueVertex u, ResidueVertex v, ResidueEdge e) {
		int delta = Math.min(u.excess, e.getResidualCapacity(u));
		if (e.from == u)
			e.flow += delta;
		else
			e.flow -= delta;
		u.excess = u.excess - delta;
		if (v.excess == 0 && v != s && v != t)
			q.add(v);
		v.excess = v.excess + delta;
	}

	/**
	 * Increases the height of u, to allow u to get rid of its excess
	 * Precondition: u.excess > 0, and for all ( u, v ) in G f , u.height <=
	 * v.height
	 *
	 * @param u
	 * @param minHeight
	 */
	private void relabel(ResidueVertex u, int minHeight) {
		u.height = 1 + minHeight;
	}

	/**
	 * Pushes all excess flow out of u, raising its height, as needed
	 *
	 * @param u
	 */
	private void discharge(ResidueVertex u) {
		ResidueEdge re;
		int minHeight;
		while (u.excess > 0) {
			minHeight = Integer.MAX_VALUE;
			for (Edge e : u) {
				ResidueVertex v = (ResidueVertex) e.otherEnd(u);
				if (v.height < minHeight)
					minHeight = v.height;
				re = (ResidueEdge) e;
				if (u.height == 1 + v.height) {
					push(u, v, re);
					if (u.excess == 0)
						return;
				}
			}
			relabel(u, minHeight);
		}
	}

	/**
	 * Perform Preflow-push relabel to front algorithm.
	 */
	public void relabelToFront() {
		initialize();
		ResidueVertex ru = null;
		int oldHeight;
		while (!q.isEmpty()) {
			ru = q.poll();
			oldHeight = ru.height;
			discharge(ru);
			if (ru.height != oldHeight)
				ru.setPriority(maxPriority++);
		}
	}
}
