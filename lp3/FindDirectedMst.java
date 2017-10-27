package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.lp3;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
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
	}

	/**
	 * @param g
	 * @param start
	 * @return
	 */
	public List<Edge> minMst(DMSTGraph g, DMSTVertex start) {
		// Transform weights function
		transformWeights(g, start);
		//Check for MST using DFS
		List<DFSVertex> components = new LinkedList<DFSVertex>();
		List<Edge> mst;
		DFS dfsObject = new DFS(g);
		dfsObject.dfsVisit(start, components);
		if (components.size() == g.size()) {
			mst = new LinkedList<Graph.Edge>();
			Vertex parent = null;
			for (DFSVertex dv : components) {
				if ((parent = dv.getParent()) != null)
					mst.add(getEdgeFromGraph(g.getDMSTVertex(parent.getName() + 1),
							g.getDMSTVertex(dv.getElement().getName() + 1), g));
				else
					mst.add(null);
			}
		}
		// If MST is not found
		else {
			ConnectedComponentsOfGraph.stronglyConnectedComponents(g);
			HashSet<Graph.Vertex>[] stronglyConnectedComponents = new HashSet[ConnectedComponentsOfGraph.numberOfSCCs];
			int index;
			for (DFSVertex dv : ConnectedComponentsOfGraph.dfsFinListReverse) {
				index = dv.getCno() - 1;
				if (stronglyConnectedComponents[index] == null)
					stronglyConnectedComponents[index] = new HashSet<>();
				stronglyConnectedComponents[index].add(dv.getElement());
			}
			Graph h = new Graph(ConnectedComponentsOfGraph.numberOfSCCs);
			HashMap<ConnectedPair, Edge> minEdge = findMinimumEdgeBetweenSCCs(h);
			DMSTGraph hDMST = new DMSTGraph(h);
			DMSTVertex c1 = hDMST.getDMSTVertex(ConnectedComponentsOfGraph.dfsGraph.getVertex(start).getCno());
			mst = minMst(hDMST, c1);
			expandSCCAndFindItsMST(g, h, stronglyConnectedComponents, minEdge, mst);
		}
		mst.sort(new Comparator<Edge>() {
			public int compare(Edge o1, Edge o2) {
				int edge1 = o1 != null ? o1.to.getName() : start.getName();
				int edge2 = o2 != null ? o2.to.getName() : start.getName();
				return edge1 - edge2;
			}
		});
		return mst;
	}

	/**
	 * @param parent
	 * @param vertex
	 * @param g
	 * @return
	 */
	private Edge getEdgeFromGraph(DMSTVertex parent, DMSTVertex vertex, DMSTGraph g) {
		for (Edge e : parent.DMSTadj)
			if (e.to.getName() == vertex.getName())
				return e;
		return null;
	}

	/**
	 * @param g
	 * @param h
	 * @param stronglyConnectedComponents
	 * @param minEdge
	 * @param mst
	 */
	private void expandSCCAndFindItsMST(DMSTGraph g, Graph h, HashSet<Vertex>[] stronglyConnectedComponents,
			HashMap<ConnectedPair, Edge> minEdge, List<Edge> mst) {
		List<Edge> kMinusOneSCCMST = new LinkedList<>();
		for (Edge dEdge : mst) {
			Vertex rootVertex = minEdge.get(new ConnectedPair(dEdge.from.getName(), dEdge.to.getName())).to;
			doBfs(rootVertex, dEdge.to, kMinusOneSCCMST);
		}
		mst.addAll(kMinusOneSCCMST);
	}

	private void doBfs(Vertex src, Vertex ck, List<Edge> kMinusOneSCCMST) {
		Queue<Graph.Vertex> q = new LinkedList<>();
		q.add(src);
		while (!q.isEmpty()) {
			Graph.Vertex u = q.remove();
			for (Graph.Edge e : u) {
				Graph.Vertex v = e.otherEnd(u);
				if (ConnectedComponentsOfGraph.dfsGraph.getVertex(v).getCno() != ck.getName())
					continue;
				if (v.seen == false) {
					v.seen = true;
					q.add(v);
					kMinusOneSCCMST.add(e);
				}
			}
		}
	}

	/**
	 * @param g
	 * @return
	 */
	private HashMap<ConnectedPair, Edge> findMinimumEdgeBetweenSCCs(Graph g) {
		List<DFSVertex> dfsFinListReverse = ConnectedComponentsOfGraph.dfsFinListReverse;
		HashMap<ConnectedPair, Edge> minEdge = new HashMap<>();
		ConnectedPair con;
		Edge prevMin;
		for (DFSVertex dv : dfsFinListReverse) {
			for (Edge e : dv.getElement()) {
				con = new ConnectedPair(dv.getCno(), ConnectedComponentsOfGraph.dfsGraph.getVertex(e.to).getCno());
				if ((prevMin = minEdge.get(con)) == null)
					minEdge.put(con, e);
				else if (prevMin.weight > e.weight)
					minEdge.put(con, e);
			}
		}

		for (Entry<ConnectedPair, Edge> entry : minEdge.entrySet())
			g.addEdge(g.getVertex(entry.getKey().from), g.getVertex(entry.getKey().to), entry.getValue().weight);
		return minEdge;
	}

	/**
	 * This method transforms the weights of all edges such that every vertex except
	 * the root has atleast one incoming 0-weight edge.
	 *
	 * @param g
	 * @param start
	 * @return
	 */
	public void transformWeights(DMSTGraph g, Vertex start) {
		for (DMSTVertex dmstVertex : g.getDMSTVertexArray()) {
			if (dmstVertex != null) {
				for (DMSTEdge dmstEdge : dmstVertex.DMSTadj) {
					dmstEdge.weight = dmstEdge.weight - ((DMSTVertex) dmstEdge.otherEnd(dmstVertex)).minEdge;
					if (dmstEdge.weight != 0)
						dmstEdge.disabled();
				}
			}
		}
	}
}