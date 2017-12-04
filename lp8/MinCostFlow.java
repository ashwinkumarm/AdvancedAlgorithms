package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.lp8;

import java.util.HashMap;
import java.util.LinkedList;

import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.lp7.Flow;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp8_q1to6_ShortestPathAlgos.ShortestPath;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp8_q1to6_ShortestPathAlgos.ShortestPathVertex;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Graph;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Graph.Edge;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Graph.Vertex;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.ResidualGraph.ResidueEdge;

public class MinCostFlow {
	Graph g;
	Vertex s, t;
	HashMap<Edge, Integer> capacity, cost;
	Flow f;

	public MinCostFlow(Graph g, Vertex s, Vertex t, HashMap<Edge, Integer> capacity, HashMap<Edge, Integer> cost) {
		this.g = g;
		this.s = s;
		this.t = t;
		this.capacity = capacity;
		this.cost = cost;
	}

	// Return cost of d units of flow found by cycle cancellation algorithm
	int cycleCancellingMinCostFlow(int d) {
		f = new Flow(g, s, t, capacity);
		int value = f.dinitzMaxFlow();
		System.out.println(value);
		LinkedList<ResidueEdge> cycle = new LinkedList<>();
		int minFlowOfCycle;
		ShortestPath sp;
		while ((sp = checkIfNegativeCycleExists()) != null) {
			minFlowOfCycle = getNegativeCycleAndMinFlow(sp, cycle);
			for (ResidueEdge e : cycle)
				if (e.isEr)
					e.flow -= minFlowOfCycle;
				else
					e.flow += minFlowOfCycle;
			cycle = new LinkedList<>();
		}
		return getMinCost();
	}

	private ShortestPath checkIfNegativeCycleExists() {
		for (Vertex v : f.gf) {
			ShortestPath sp = new ShortestPath(f.gf, f.gf.getVertex(v));
			if (!sp.bellmanFord())
				return sp;
		}
		return null;
	}

	public int getNegativeCycleAndMinFlow(ShortestPath sp, LinkedList<ResidueEdge> cycle) {
		ShortestPathVertex cur = sp.u;
		ResidueEdge e = (ResidueEdge) f.gf.getEdgeFromGraph(cur.parent, cur.vertex);
		int flowTobeSent = checkErAndGetResidualCapacity(e, cur.parent), flow;
		cycle.add(e);
		cur = sp.getVertex(cur.parent);
		while (cur.vertex != sp.u.vertex) {
			e = (ResidueEdge) f.gf.getEdgeFromGraph(cur.parent, cur.vertex);
			if ((flow = checkErAndGetResidualCapacity(e, cur.parent)) < flowTobeSent)
				flowTobeSent = flow;
			cycle.add(e);
			cur = sp.getVertex(cur.parent);
		}
		return flowTobeSent;
	}

	public int checkErAndGetResidualCapacity(ResidueEdge e, Vertex u) {
		if (e.from == u)
			return capacity.get(e) - e.flow;
		else {
			e.isEr = true;
			return e.flow;
		}
	}

	// Return cost of d units of flow found by successive shortest paths
	int successiveSPMinCostFlow(int d) {
		return 0;
	}

	// Return cost of d units of flow found by cost scaling algorithm
	int costScalingMinCostFlow() {
		// Find max-flow first and then a min-cost max-flow
		Flow f = new Flow(g, s, t, capacity);
		int value = f.relabelToFront();
		System.out.println(value);
		CostScaling cs = new CostScaling(f.gf, s, t, capacity);
		cs.minCostCirculation();
		return getMinCost();
	}

	private int getMinCost() {
		int cost = 0;
		for (Vertex u : g)
			for (Edge e : u) {
				cost += f.gf.getEdgeFromGraph(f.gf.getVertex(e.from), f.gf.getVertex(e.to)).flow * e.cost(u);
			}
		return cost;
	}

	// flow going through edge e
	public int flow(Edge e) {
		return 0;
	}

	// capacity of edge e
	public int capacity(Edge e) {
		return 0;
	}

	// cost of edge e
	public int cost(Edge e) {
		return 0;
	}
}