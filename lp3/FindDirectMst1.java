package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.lp3;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.lp3.DMSTGraph.DMSTEdge;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.lp3.DMSTGraph.DMSTVertex;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.ConnectedComponentsOfGraph;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.DFS;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.DFS.DFSVertex;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Graph.Edge;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Graph.Vertex;

/**
 * This class performs the Chu and Liu | Edmonds Algorithm (improved by Tarjan's
 * algorithm) for finding the optimal branching in the given directed graph.
 *
 * @author Ashwin, Arun, Deepak, Haritha
 *
 */
public class FindDirectMst1 {

	/**
	 * Class structure used for storing the image of the edges between SCCs.
	 */
	class ConnectedPair {
		int from;
		int to;

		public ConnectedPair(int from, int to) {
			super();
			this.from = from;
			this.to = to;
		}

		@Override
		public boolean equals(Object obj) {
			ConnectedPair con = (ConnectedPair) obj;
			if (this.from == con.from && this.to == con.to)
				return true;
			else
				return false;
		}

		@Override
		public int hashCode() {
			int result = 17;
			result = 31 * result + from;
			result = 31 * result + to;
			return result;
		}

		@Override
		public String toString() {
			return "(" + from + ", " + to + ")";
		}

	}

	/**
	 * Recursive method that computes the minimum spanning tree for the input
	 * directed graph using Chu and Liu | Edmonds Algorithm (improved by
	 * Tarjan's algorithm).
	 *
	 * @param g
	 * @param start
	 * @param graphSize
	 * @return
	 */
	public void minMst(DMSTGraph g, DMSTVertex start, int graphSize) {
		transformWeights(g, start);

		// If MST is not found
		if (!doDfs(g, start, graphSize)) {
			ConnectedComponentsOfGraph connectedComponentsOfGraph = new ConnectedComponentsOfGraph();
			connectedComponentsOfGraph.stronglyConnectedComponents(g);
			LinkedList<DMSTVertex>[] stronglyConnectedComponents = new LinkedList[connectedComponentsOfGraph.numberOfSCCs];
			HashMap<ConnectedPair, DMSTEdge> minEdge = new HashMap<>();

			getSCCAndFindMinimumEdges(connectedComponentsOfGraph, stronglyConnectedComponents, minEdge);
			HashMap<Integer, Integer> vertexMappings = new HashMap<>();
			HashMap<Integer, Integer> sccLocation = addSCCVerticesAndDisableOldVertices(g, stronglyConnectedComponents,
					minEdge, vertexMappings);
			DMSTVertex c1 = g.getDMSTVertexWithName(
					sccLocation.get(connectedComponentsOfGraph.dfsGraph.getVertex(start).getCno()));
			minMst(g, c1, sccLocation.size());
			g.disableAllVertices();
			expandSCCAndFindItsMST(g, minEdge, connectedComponentsOfGraph, sccLocation, stronglyConnectedComponents,
					vertexMappings);
		}
	}

	/**
	 * Groups the SCC vertices and also finds the minimum edges between the
	 * components.
	 *
	 * @param connectedComponentsOfGraph
	 * @param stronglyConnectedComponents
	 * @param minEdge
	 */
	private void getSCCAndFindMinimumEdges(ConnectedComponentsOfGraph connectedComponentsOfGraph,
			LinkedList<DMSTVertex>[] stronglyConnectedComponents, HashMap<ConnectedPair, DMSTEdge> minEdge) {
		ConnectedPair con;
		Edge prevMin;
		int index;
		Iterator<Edge> iterator;
		for (DFSVertex dv : connectedComponentsOfGraph.dfsFinListReverse) {
			index = dv.getCno() - 1;
			if (stronglyConnectedComponents[index] == null)
				stronglyConnectedComponents[index] = new LinkedList<>();
			stronglyConnectedComponents[index].add((DMSTVertex) dv.getElement());

			iterator = ((DMSTVertex) dv.getElement()).allIterator();
			while (iterator.hasNext()) {
				Edge e = iterator.next();
				con = new ConnectedPair(dv.getCno(), connectedComponentsOfGraph.dfsGraph.getVertex(e.to).getCno());
				if (con.from != con.to) {
					if ((prevMin = minEdge.get(con)) == null)
						minEdge.put(con, (DMSTEdge) e);
					else if (prevMin.weight > e.weight)
						minEdge.put(con, (DMSTEdge) e);
				}

				// updating minEdge
				DMSTVertex toVertex = (DMSTVertex) e.to;
				if (toVertex.minEdge > e.weight)
					toVertex.minEdge = e.weight;
			}
		}
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
		DMSTEdge dmstEdge;
		for (Vertex dmstVertex : g) {
			for (Edge edge : (DMSTVertex) dmstVertex) {
				dmstEdge = (DMSTEdge) edge;
				dmstEdge.weight = dmstEdge.weight - ((DMSTVertex) dmstEdge.to).minEdge;
				if (dmstEdge.weight != 0)
					dmstEdge.disable();
			}
		}
	}

	/**
	 * Add new vertices for SCCs containing multiple vertices.
	 *
	 * @param g
	 * @param stronglyConnectedComponents
	 * @param minEdge
	 * @return
	 */
	private HashMap<Integer, Integer> addSCCVerticesAndDisableOldVertices(DMSTGraph g,
			LinkedList<DMSTVertex>[] stronglyConnectedComponents, HashMap<ConnectedPair, DMSTEdge> minEdge,
			HashMap<Integer, Integer> vertexMappings) {
		HashMap<Integer, Integer> sccMappings = new HashMap<>();
		LinkedList<DMSTVertex> scc;
		for (int i = 0; i < stronglyConnectedComponents.length; i++) {
			scc = stronglyConnectedComponents[i];
			if (scc.size() > 1) {
				g.getDMSTVertexArray()[g.n] = new DMSTVertex(new Vertex(g.n));
				sccMappings.put(i + 1, g.n);
				vertexMappings.put(g.n, i + 1);
				g.n++;
				disableSCCVertices(scc);
			} else {
				sccMappings.put(i + 1, scc.getFirst().getName());
				vertexMappings.put(scc.getFirst().getName(), i + 1);
			}
		}

		DMSTVertex from, to;
		DMSTEdge edge;
		for (Entry<ConnectedPair, DMSTEdge> entry : minEdge.entrySet()) {
			from = g.getDMSTVertexWithName(sccMappings.get(entry.getKey().from));
			to = g.getDMSTVertexWithName(sccMappings.get(entry.getKey().to));
			if ((edge = (DMSTEdge) getEdgeFromGraph(from, to)) == null)
				g.addEdge(from, to, entry.getValue().weight, g.edgeSize());
			else
				edge.enable();
		}
		return sccMappings;
	}

	/**
	 * This method gets the original edge from the graph.
	 *
	 * @param parent
	 * @param vertex
	 * @param g
	 * @return
	 */
	static Edge getEdgeFromGraph(Vertex parent, Vertex vertex) {
		for (Edge e : parent)
			if (e.to.getName() == vertex.getName())
				return e;
		return null;
	}

	/**
	 * Expands each SCC and runs a BFS in each component.
	 *
	 * @param g
	 * @param minEdge
	 * @param connectedComponentsOfGraph
	 * @param sccLocation
	 * @param stronglyConnectedComponents
	 * @param vertexMappings
	 */
	private void expandSCCAndFindItsMST(DMSTGraph g, HashMap<ConnectedPair, DMSTEdge> minEdge,
			ConnectedComponentsOfGraph connectedComponentsOfGraph, HashMap<Integer, Integer> sccLocation,
			LinkedList<DMSTVertex>[] stronglyConnectedComponents, HashMap<Integer, Integer> vertexMappings) {
		DMSTEdge dEdge;
		for (int vertexNo : sccLocation.values()) {
			if ((dEdge = g.getDMSTVertexWithName(vertexNo).incomingEdge) != null) {
				DMSTEdge mstIncomingEdge = minEdge.get(new ConnectedPair(vertexMappings.get(dEdge.from.getName()),
						vertexMappings.get(dEdge.to.getName())));
				DMSTVertex rootVertex = (DMSTVertex) mstIncomingEdge.to;
				rootVertex.incomingEdge = mstIncomingEdge;
				int ck = connectedComponentsOfGraph.dfsGraph.getVertex(rootVertex).getCno();
				if (stronglyConnectedComponents[ck - 1].size() > 1) {
					enableSCCVertices(stronglyConnectedComponents[ck - 1]);
					doDfs(g, rootVertex, stronglyConnectedComponents[ck - 1].size());
					disableSCCVertices(stronglyConnectedComponents[ck - 1]);
				}
			}
		}
	}

	/**
	 * This method enables the vertices of the given SCC.
	 *
	 * @param stronglyConnectedComponent
	 */
	private void enableSCCVertices(LinkedList<DMSTVertex> stronglyConnectedComponent) {
		for (DMSTVertex v : stronglyConnectedComponent)
			v.enable();
	}

	/**
	 * This method disables the vertices of the given SCC.
	 *
	 * @param stronglyConnectedComponent
	 */
	private void disableSCCVertices(LinkedList<DMSTVertex> stronglyConnectedComponent) {
		for (DMSTVertex v : stronglyConnectedComponent)
			v.disable();
	}

	/**
	 * This method calls the standard DFS on the given graph.
	 *
	 * @param g
	 * @param src
	 * @param ck
	 * @param connectedComponentsOfGraph
	 */
	private boolean doDfs(DMSTGraph g, Vertex src, int graphSize) {
		List<DFSVertex> components = new LinkedList<DFSVertex>();
		DFS dfsObject = new DFS(g);
		dfsObject.dfsVisit(src, components);
		if (components.size() == graphSize) {
			Vertex parent = null;
			for (DFSVertex dv : components) {
				if ((parent = dv.getParent()) != null) {
					DMSTVertex to = g.getDMSTVertex(dv.getElement().getName() + 1);
					to.incomingEdge = (DMSTEdge) getEdgeFromGraph(g.getDMSTVertex(parent.getName() + 1), to);
				}
			}
			return true;
		}
		return false;
	}
}