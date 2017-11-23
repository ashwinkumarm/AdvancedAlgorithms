package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.lp7;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.lp7.ResidualGraph.ResidueEdge;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.lp7.ResidualGraph.ResidueVertex;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Graph.Edge;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Graph.Vertex;

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
		/*-for (Vertex u : gf) {
			ResidueVertex ru = (ResidueVertex) u;
			ru.height = 0;
			ru.excess = 0;
		}
		for (Vertex u : gf)
			for (Edge e : u)
				((ResidueEdge) e).flow = 0;*/
		s.height = gf.size();
		for (Edge e : s) {
			ResidueVertex ru = (ResidueVertex) e.otherEnd(s);
			((ResidueEdge) e).flow = capacity.get(e);
			s.excess = s.excess - capacity.get(e);
			ru.excess = ru.excess + capacity.get(e);
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
		v.excess = v.excess + delta;
	}

	/**
	 * Increases the height of u, to allow u to get rid of its excess
	 * Precondition: u.excess > 0, and for all ( u, v ) in G f , u.height <=
	 * v.height
	 *
	 * @param u
	 */
	private void relabel(ResidueVertex u) {
		u.height = 1 + getAdjacentVertexWithMinimumHeight(u);
	}

	/**
	 * Finds the adjacent vertex with minimum height.
	 *
	 * @param u
	 * @return
	 */
	private int getAdjacentVertexWithMinimumHeight(ResidueVertex u) {
		int minHeight = Integer.MAX_VALUE, vHeight;
		for (Edge e : u) {
			if ((vHeight = ((ResidueVertex) e.otherEnd(u)).height) < minHeight)
				minHeight = vHeight;
		}
		return minHeight;
	}

	/**
	 * Pushes all excess flow out of u, raising its height, as needed
	 *
	 * @param u
	 */
	private void discharge(ResidueVertex u) {
		ResidueEdge re;
		while (u.excess > 0) {
			for (Edge e : u) {
				re = (ResidueEdge) e;
				ResidueVertex v = (ResidueVertex) e.otherEnd(u);
				if (re.inGf(u) && u.height == 1 + v.height) {
					push(u, v, re);
					if (u.excess == 0)
						return;
				}
			}
			relabel(u);
		}
	}

	/**
	 * Perform Preflow-push relabel to front algorithm.
	 */
	public void relabelToFront() {
		initialize();
		// Create an list L of nodes in V - { s, t }, in any order
		LinkedList<ResidueVertex> L = new LinkedList<>();
		for (Vertex u : gf)
			if (u != s && u != t)
				L.add((ResidueVertex) u);
		boolean done = false;
		ResidueVertex ru = null;
		while (!done) {
			Iterator<ResidueVertex> it = L.iterator();
			done = true;
			while (it.hasNext()) {
				ru = it.next();
				if (ru.excess == 0)
					continue;
				int oldHeight = ru.height;
				discharge(ru);
				if (ru.height != oldHeight) {
					done = false;
					break;
				}
			}
			if (!done) {
				// Move u to beginning of L
				it.remove();
				L.addFirst(ru);
			}
		}
	}
}
