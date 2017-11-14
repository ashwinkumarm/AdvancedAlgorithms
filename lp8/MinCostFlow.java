package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.lp8;

import java.util.HashMap;

import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Graph;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Graph.Edge;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Graph.Vertex;

public class MinCostFlow {
	public MinCostFlow(Graph g, Vertex s, Vertex t, HashMap<Edge, Integer> capacity, HashMap<Edge, Integer> cost) {
	}

	// Return cost of d units of flow found by cycle cancellation algorithm
	int cycleCancellingMinCostFlow(int d) {
		return 0;
	}

	// Return cost of d units of flow found by successive shortest paths
	int successiveSPMinCostFlow(int d) {
		return 0;
	}

	// Return cost of d units of flow found by cost scaling algorithm
	int costScalingMinCostFlow(int d) {
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

	// cost of edge e
	public int cost(Edge e) {
		return 0;
	}
}