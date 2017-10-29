package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.lp3;

import java.util.HashMap;
import java.util.Iterator;
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
	public void minMst(DMSTGraph g, DMSTVertex start, int graphSize) {
		List<DMSTEdge> disabledEdges = transformWeights(g, start);
		/*
		 * for (Vertex v : g) { DMSTVertex d = (DMSTVertex) v; for (Edge e : d)
		 * { DMSTEdge de = (DMSTEdge) e; System.out.println(e + " - " +
		 * de.weight + " - " + de.disabled); } }
		 */
		// Check for MST
		List<DFSVertex> components = new LinkedList<DFSVertex>();
		DFS dfsObject = new DFS(g);
		dfsObject.dfsVisit(start, components);
		if (components.size() == graphSize) {
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
			HashMap<Integer, Integer> sccLocation = addSCCVerticesAndDisableOldVertices(g, stronglyConnectedComponents);
			HashMap<ConnectedPair, DMSTEdge> minEdge = findMinimumEdgeBetweenSCCs(g, connectedComponentsOfGraph,
					sccLocation);
			disableSCCVertices(stronglyConnectedComponents);
			DMSTVertex c1 = g.getDMSTVertexWithName(
					sccLocation.get(connectedComponentsOfGraph.dfsGraph.getVertex(start).getCno()));
			/*
			 * for (Vertex v : g) { DMSTVertex d = (DMSTVertex) v; for (Edge e :
			 * d) { DMSTEdge de = (DMSTEdge) e; System.out.println(e + " - " +
			 * de.weight + " - " + de.disabled); } }
			 */
			// System.out.println(stronglyConnectedComponents.length);
			minMst(g, c1, sccLocation.size());
			g.disableAllVertices();
			expandSCCAndFindItsMST(g, minEdge, connectedComponentsOfGraph, sccLocation, stronglyConnectedComponents);
		}
	}

	private void disableSCCVertices(LinkedList<DMSTVertex>[] stronglyConnectedComponents) {
		for (LinkedList<DMSTVertex> scc : stronglyConnectedComponents)
			if (scc.size() > 1)
				for (DMSTVertex vertex : scc)
					vertex.disable();
	}

	/**
	 * This method transforms the weights of all edges such that every vertex
	 * except the root has atleast one incoming 0-weight edge.
	 *
	 * @param g
	 * @param start
	 * @return
	 */
	public List<DMSTEdge> transformWeights(DMSTGraph g, Vertex start) {
		DMSTEdge dmstEdge;
		List<DMSTEdge> disabledEdges = new LinkedList<>();
		for (Vertex dmstVertex : g) {
			for (Edge edge : (DMSTVertex) dmstVertex) {
				dmstEdge = (DMSTEdge) edge;
				dmstEdge.weight = dmstEdge.weight - ((DMSTVertex) dmstEdge.to).minEdge;
				if (dmstEdge.weight != 0) {
					dmstEdge.disable();
					disabledEdges.add(dmstEdge);
				}
			}
		}
		return disabledEdges;
	}

	/**
	 * @param g
	 * @param originalSize
	 * @param connectedComponentsOfGraph
	 * @param sccLocation
	 * @return
	 */
	private HashMap<ConnectedPair, DMSTEdge> findMinimumEdgeBetweenSCCs(DMSTGraph g,
			ConnectedComponentsOfGraph connectedComponentsOfGraph, HashMap<Integer, Integer> sccLocation) {
		List<DFSVertex> dfsFinListReverse = connectedComponentsOfGraph.dfsFinListReverse;
		HashMap<ConnectedPair, DMSTEdge> minEdge = new HashMap<>();
		ConnectedPair con;
		Edge prevMin;
		Iterator<Edge> iterator;
		for (DFSVertex dv : dfsFinListReverse) {
			iterator = ((DMSTVertex) dv.getElement()).allIterator();
			while (iterator.hasNext()) {
				Edge e = iterator.next();
				// TODO: Check if this can be moved so that disabling can be
				// done in the previous method
				con = new ConnectedPair(sccLocation.get(dv.getCno()),
						sccLocation.get(connectedComponentsOfGraph.dfsGraph.getVertex(e.to).getCno()));
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

		DMSTVertex from, to;
		DMSTEdge edge;
		for (Entry<ConnectedPair, DMSTEdge> entry : minEdge.entrySet()) {
			from = g.getDMSTVertexWithName(entry.getKey().from);
			to = g.getDMSTVertexWithName(entry.getKey().to);
			if ((edge = (DMSTEdge) getEdgeFromGraph(from, to)) == null)
				g.addEdge(from, to, entry.getValue().weight, g.edgeSize());
			else
				edge.enable();
		}
		return minEdge;
	}

	private HashMap<Integer, Integer> addSCCVerticesAndDisableOldVertices(DMSTGraph g,
			LinkedList<DMSTVertex>[] stronglyConnectedComponents) {
		HashMap<Integer, Integer> sccMappings = new HashMap<>();
		LinkedList<DMSTVertex> scc;
		for (int i = 0; i < stronglyConnectedComponents.length; i++) {
			scc = stronglyConnectedComponents[i];
			if (scc.size() > 1) {
				g.getDMSTVertexArray()[g.n] = new DMSTVertex(new Vertex(g.n));
				sccMappings.put(i + 1, g.n);
				g.n++;
				// TODO:Check if SCC vertices can be disabled here
			} else
				sccMappings.put(i + 1, scc.getFirst().getName());
		}
		return sccMappings;
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
	 * @param sccLocation
	 * @param stronglyConnectedComponents
	 * @param mst
	 */
	private void expandSCCAndFindItsMST(DMSTGraph g, HashMap<ConnectedPair, DMSTEdge> minEdge,
			ConnectedComponentsOfGraph connectedComponentsOfGraph, HashMap<Integer, Integer> sccLocation,
			LinkedList<DMSTVertex>[] stronglyConnectedComponents) {
		DMSTEdge dEdge;
		for (int vertexNo : sccLocation.values()) {
			if ((dEdge = g.getDMSTVertexWithName(vertexNo).incomingEdge) != null) {
				DMSTEdge mstIncomingEdge = minEdge.get(new ConnectedPair(dEdge.from.getName(), dEdge.to.getName()));
				DMSTVertex rootVertex = (DMSTVertex) mstIncomingEdge.to;
				rootVertex.incomingEdge = mstIncomingEdge;
				int ck = connectedComponentsOfGraph.dfsGraph.getVertex(rootVertex).getCno();
				enableSCCVertices(stronglyConnectedComponents[ck - 1]);
				doBfs(rootVertex, ck, connectedComponentsOfGraph);
				disableSCCVertices(stronglyConnectedComponents[ck - 1]);
			}
		}
	}

	private void enableSCCVertices(LinkedList<DMSTVertex> stronglyConnectedComponent) {
		for (DMSTVertex v : stronglyConnectedComponent)
			v.enable();
	}

	private void disableSCCVertices(LinkedList<DMSTVertex> stronglyConnectedComponent) {
		for (DMSTVertex v : stronglyConnectedComponent)
			v.disable();
	}

	private void doBfs(Vertex src, int ck, ConnectedComponentsOfGraph connectedComponentsOfGraph) {
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
}