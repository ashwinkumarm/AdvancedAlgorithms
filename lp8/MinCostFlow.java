package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.lp8;

import java.util.HashMap;
import java.util.LinkedList;

import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.lp7.Flow;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp8_q1to6_ShortestPathAlgos.ShortestPath;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp8_q1to6_ShortestPathAlgos.ShortestPathVertex;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Graph;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Graph.Edge;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Graph.Vertex;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.ResidualGraph;
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
		ShortestPath sp = new ShortestPath(f.gf, f.gf.getVertex(s));
		LinkedList<Edge> cycle = new LinkedList<>();
		int minFlowOfCycle;
		while (!sp.bellmanFord()) {
			getNegativeCycle(sp, cycle);
			// TODO:
			minFlowOfCycle = getMinimumWeightEdgeFromCycle(cycle);
			for (Edge e : cycle) {

			}
			cycle = new LinkedList<>();
		}
		return 0;
	}

	private int getMinimumWeightEdgeFromCycle(LinkedList<Edge> cycle) {
		int flowTobeSent = Integer.MAX_VALUE;

		return flowTobeSent;
	}

	public int getNegativeCycle(ShortestPath sp, LinkedList<Edge> cycle) {
		ShortestPathVertex cur = sp.u;
		int flowTobeSent = Integer.MAX_VALUE, flow;
		while (cur.parent != sp.u.vertex) {
			ResidueEdge e = (ResidueEdge) f.gf.getEdgeFromGraph(cur.parent, cur.vertex);
			if ((flow = ((ResidueEdge) e).getResidualCapacity(null)) < flowTobeSent)
				flowTobeSent = flow;
			cycle.add(e);
			cur = sp.getVertex(cur.parent);
		}
		cycle.add(g.getEdgeFromGraph(cur.parent, cur.vertex));
		return flowTobeSent;
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
		return getMinCost(f.gf);
	}

	private int getMinCost(ResidualGraph gf) {
		int cost = 0;
		ResidueEdge re;
		for (Vertex u : gf)
			for (Edge e : u) {
				re = (ResidueEdge) e;
				cost += re.flow * re.getCost(u);
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