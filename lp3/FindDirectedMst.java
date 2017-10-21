package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.lp3;

import java.util.HashSet;
import java.util.Iterator;

import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.lp3.DMSTGraph.DMSTEdge;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.lp3.DMSTGraph.DMSTVertex;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.ConnectedComponentsOfGraph;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.DFS.DFSVertex;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Graph;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Graph.Edge;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Graph.Vertex;

/**
 * This class performs the Chu and Liu | Edmonds Algorithm (improved by Tarjan's
 * algorithm) for finding the optimat branching in the given directed graph.
 *
 * @author Ashwin, Arun, Deepak, Haritha
 *
 */
public class FindDirectedMst {

	HashSet<Integer> cycle = new HashSet<Integer>();

	public Graph minMst(DMSTGraph g, Vertex start) {
		transformWeights(g, start);
		ConnectedComponentsOfGraph componentsOfGraph = new ConnectedComponentsOfGraph();
		componentsOfGraph.stronglyConnectedComponents(g);
		HashSet<Graph.Vertex>[] stronglyConnectedComponents = new HashSet[componentsOfGraph.numberOfSCCs];
		int index;
		for (DFSVertex dv : componentsOfGraph.dfsFinListReverse) {
			index = dv.getCno() - 1;
			if (stronglyConnectedComponents[index] == null)
				stronglyConnectedComponents[index] = new HashSet<>();
			stronglyConnectedComponents[index].add(dv.getElement());
		}
		if (componentsOfGraph.numberOfComponents == 1) {
			// TODO: This is the MST. Have to order the edges.
		} else {
			DMSTVertex shrinkedVetrex;
			int n = 1;
			for (HashSet<Graph.Vertex> scc : stronglyConnectedComponents)
				System.out.println("SCC: " + scc);
			for (HashSet<Graph.Vertex> scc : stronglyConnectedComponents) {
				for (Graph.Vertex dmstVertex : scc)
					((DMSTVertex) dmstVertex).disable();
				shrinkedVetrex = new DMSTVertex(new Vertex(n++));
				// findMinimumEdgeBetweenSCCs();
				// disable the other edges between the sccs and keep only the
				// minimum edge
			}
		}
		return g;
	}

	public String detectCycle(StringBuilder sb, DMSTVertex v) {
		while (v != null) {
			Iterator<Edge> it = v.revIterator();
			while (it.hasNext()) {
				Edge e = it.next();
				if (!cycle.contains(e.from.getName())) {
					cycle.add(e.from.getName());
					sb.append(e.from.getName());
					v = (DMSTVertex) e.from;
				} else {
					String s = sb.toString();
					int i = s.indexOf(Integer.toString(e.from.getName()));
					sb = new StringBuilder(s.substring(i, s.length()));
					return s.substring(i, s.length());
				}
			}
		}
		return null;
	}

	/**
	 * This method transforms the weights of all edges such that every vertex
	 * except the root has atleast one incoming 0-weight edge.
	 *
	 * @param g
	 * @param start
	 * @return
	 */
	public void transformWeights(DMSTGraph g, Vertex start) {
		DMSTVertex dmstVertex;
		DMSTEdge dmstEdge;
		for (Vertex vertex : g) {
			dmstVertex = (DMSTVertex) vertex;
			if (dmstVertex.getName() != start.getName()) {
				for (Edge edge : dmstVertex) {
					dmstEdge = (DMSTEdge) edge;
					dmstEdge.weight = dmstEdge.weight - ((DMSTVertex) dmstEdge.otherEnd(dmstVertex)).minEdge;
					if (dmstEdge.weight != 0)
						dmstEdge.disabled();
				}
			}
		}
	}
}
