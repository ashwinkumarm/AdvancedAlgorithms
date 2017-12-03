package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.lp8;

import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Queue;

import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Graph.Edge;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Graph.Vertex;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.ResidualGraph;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.ResidualGraph.ResidueEdge;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.ResidualGraph.ResidueVertex;

public class CostScaling {
	int epsilon;
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

	public CostScaling(ResidualGraph gf, Vertex s, Vertex t, HashMap<Edge, Integer> capacity) {
		super();
		this.gf = gf;
		this.s = gf.getResidueVertexWithName(s.getName());
		this.t = gf.getResidueVertexWithName(t.getName());
		this.capacity = capacity;
	}

	void minCostCirculation() {
		epsilon = gf.maximumWeight;
		// p( u ) = 0, for u in V;
		while (epsilon > 0)
			refine();
	}

	// push flow along ( u, v ) in Gf
	void push(ResidueEdge e, ResidueVertex u, ResidueVertex v) {
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

	int RC(ResidueEdge e, Vertex u) {
		return e.getCost(u) + ((ResidueVertex) e.from).p - ((ResidueVertex) e.to).p;
	}

	void discharge(ResidueVertex u) { // drain the excess at u
		ResidueEdge re;
		ResidueVertex v;
		int maxp = Integer.MIN_VALUE;
		while (u.excess > 0) {
			// for ( u, v ) out of u in Gf with RC( u, v ) < 0
			for (Edge e : u) {
				v = (ResidueVertex) e.otherEnd(u);
				if (v.p - e.weight - epsilon > maxp)
					maxp = v.p - e.weight - epsilon;
				re = (ResidueEdge) e;
				if (RC(re, u) < 0)
					push(re, u, v);
			}
			if (u.excess > 0)
				u.p = maxp;
		}
	}

	void refine() { // cut epsilon by half
		epsilon = epsilon / 2;
		ResidueEdge re;
		for (Vertex v : gf)
			for (Edge e : v) {
				re = (ResidueEdge) e;
				// for (( u, v ) in Gf with RC( u, v ) < 0 )
				if (RC(re, v) < 0)
					re.flow = capacity.get(e);
			}
		while (!q.isEmpty())
			discharge(q.poll());
	}
}
