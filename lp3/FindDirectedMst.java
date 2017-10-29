package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.lp3;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Queue;

import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.lp3.DMSTGraph.DMSTEdge;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.lp3.DMSTGraph.DMSTVertex;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.ConnectedComponentsOfGraph;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.DFS;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.DFS.DFSVertex;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Graph;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Graph.Edge;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Graph.Vertex;

/**
 * This class performs the Chu and Liu | Edmonds Algorithm (improved by Tarjan's
 * algorithm) for finding the optimal branching in the given directed graph.
 *
 * @author Ashwin, Arun, Deepak, Haritha
 *
 */
public class FindDirectedMst {

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
	 * @param g
	 * @param start
	 * @param originalSize2
	 * @return
	 */
	public void minMst(DMSTGraph g, DMSTVertex start, int startSize) {
		transformWeights(g, start);
		for (Vertex v : g) {
			DMSTVertex d = (DMSTVertex) v;
			for (Edge e : d) {
				DMSTEdge de = (DMSTEdge) e;
				System.out.println(e + " - " + de.weight + " - " + de.disabled);
			}
		}
		// Check for MST
		List<DFSVertex> components = new LinkedList<DFSVertex>();
		DFS dfsObject = new DFS(g);
		dfsObject.dfsVisit(start, components);
		if (components.size() == (g.size() - startSize)) {
			Vertex parent = null;
			for (DFSVertex dv : components) {
				if ((parent = dv.getParent()) != null) {
					DMSTVertex to = g.getDMSTVertex(dv.getElement().getName() + 1);
					to.incomingEdge = (DMSTEdge) getEdgeFromGraph(g.getDMSTVertex(parent.getName() + 1), to);
				}
			}
		}
		// If MST is not found
		else {
			ConnectedComponentsOfGraph connectedComponentsOfGraph = new ConnectedComponentsOfGraph();
			connectedComponentsOfGraph.stronglyConnectedComponents(g);
			LinkedList<DMSTVertex>[] stronglyConnectedComponents = new LinkedList[connectedComponentsOfGraph.numberOfSCCs];
			int index;
			for (DFSVertex dv : connectedComponentsOfGraph.dfsFinListReverse) {
				index = dv.getCno() - 1;
				if (stronglyConnectedComponents[index] == null)
					stronglyConnectedComponents[index] = new LinkedList<>();
				stronglyConnectedComponents[index].add((DMSTVertex) dv.getElement());
			}
			disableAllVertices(g);
			int originalSize = g.size();
			addSCCVertices(g, stronglyConnectedComponents);
			int newSize = g.size();
			HashMap<ConnectedPair, DMSTEdge> minEdge = findMinimumEdgeBetweenSCCs(g, originalSize,
					connectedComponentsOfGraph);
			DMSTVertex c1 = g
					.getDMSTVertex(connectedComponentsOfGraph.dfsGraph.getVertex(start).getCno() + originalSize);
			for (Vertex v : g) {
				DMSTVertex d = (DMSTVertex) v;
				for (Edge e : d) {
					DMSTEdge de = (DMSTEdge) e;
					System.out.println(e + " - " + de.weight + " - " + de.disabled);
				}
			}
			System.out.println();
			minMst(g, c1, originalSize);
			enableVertices(g, startSize, originalSize);
			expandSCCAndFindItsMST(g, minEdge, originalSize, newSize, connectedComponentsOfGraph);
		}
	}

	private void enableVertices(DMSTGraph g, int startSize, int originalSize) {
		for (int i = startSize; i < originalSize; i++)
			g.getDMSTVertexArray()[i].enable();
	}

	private void addSCCVertices(DMSTGraph g, LinkedList<DMSTVertex>[] stronglyConnectedComponents) {
		for (LinkedList<DMSTVertex> scc : stronglyConnectedComponents) {
			if (scc.size() > 1) {
				g.getDMSTVertexArray()[g.n] = new DMSTVertex(new Vertex(g.n));
				g.n++;
			}
		}
	}

	/**
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
	 * @param g
	 * @param h
	 * @param stronglyConnectedComponents
	 * @param minEdge
	 * @param newSize
	 * @param originalSize
	 * @param connectedComponentsOfGraph
	 * @param mst
	 */
	private void expandSCCAndFindItsMST(DMSTGraph g, HashMap<ConnectedPair, DMSTEdge> minEdge, int originalSize,
			int newSize, ConnectedComponentsOfGraph connectedComponentsOfGraph) {
		DMSTEdge dEdge;
		for (int i = originalSize; i < newSize; i++) {
			if ((dEdge = g.getDMSTVertexArray()[i].incomingEdge) != null) {
				DMSTEdge mstIncomingEdge = minEdge
						.get(new ConnectedPair(dEdge.from.getName() + 1, dEdge.to.getName() + 1));
				DMSTVertex rootVertex = (DMSTVertex) mstIncomingEdge.to;
				rootVertex.incomingEdge = mstIncomingEdge;
				doBfs(rootVertex, connectedComponentsOfGraph);
			}
		}
	}

	private void doBfs(Vertex src, ConnectedComponentsOfGraph connectedComponentsOfGraph) {
		int ck = connectedComponentsOfGraph.dfsGraph.getVertex(src).getCno();
		Queue<Graph.Vertex> q = new LinkedList<>();
		src.seen = true;
		q.add(src);
		while (!q.isEmpty()) {
			Graph.Vertex u = q.remove();
			for (Graph.Edge e : u) {
				DMSTVertex v = (DMSTVertex) e.otherEnd(u);
				if (connectedComponentsOfGraph.dfsGraph.getVertex(v).getCno() != ck)
					continue;
				if (v.seen == false) {
					v.seen = true;
					v.incomingEdge = (DMSTEdge) e;
					q.add(v);
				}
			}
		}
	}

	/**
	 * @param g
	 * @param originalSize
	 * @param connectedComponentsOfGraph
	 * @return
	 */
	private HashMap<ConnectedPair, DMSTEdge> findMinimumEdgeBetweenSCCs(DMSTGraph g, int originalSize,
			ConnectedComponentsOfGraph connectedComponentsOfGraph) {
		List<DFSVertex> dfsFinListReverse = connectedComponentsOfGraph.dfsFinListReverse;
		HashMap<ConnectedPair, DMSTEdge> minEdge = new HashMap<>();
		ConnectedPair con;
		Edge prevMin;
		for (DFSVertex dv : dfsFinListReverse) {
			for (DMSTEdge e : ((DMSTVertex) dv.getElement()).DMSTadj) {
				con = new ConnectedPair(dv.getCno() + originalSize,
						connectedComponentsOfGraph.dfsGraph.getVertex(e.to).getCno() + originalSize);
				if (con.from != con.to) {
					if ((prevMin = minEdge.get(con)) == null)
						minEdge.put(con, (DMSTEdge) e);
					else if (prevMin.weight > e.weight)
						minEdge.put(con, (DMSTEdge) e);
				}
			}
		}

		for (Entry<ConnectedPair, DMSTEdge> entry : minEdge.entrySet())
			g.addEdge(g.getDMSTVertex(entry.getKey().from), g.getDMSTVertex(entry.getKey().to), entry.getValue().weight,
					g.edgeSize());
		return minEdge;
	}

	private void disableAllVertices(DMSTGraph g) {
		for (Vertex v : g)
			((DMSTVertex) v).disable();
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
				dmstEdge.weight = dmstEdge.weight - ((DMSTVertex) dmstEdge.otherEnd(dmstVertex)).minEdge;
				if (dmstEdge.weight != 0)
					dmstEdge.disabled();
			}
		}
	}
}