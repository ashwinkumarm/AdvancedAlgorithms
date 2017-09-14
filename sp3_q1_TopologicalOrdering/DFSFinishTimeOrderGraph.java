package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp3_q1_TopologicalOrdering;

import java.util.List;

import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp3_q1_TopologicalOrdering.TopoGraph.TopoVertex;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Graph;

public class DFSFinishTimeOrderGraph {
	
	static int componentNo, time, topNum;
	TopoGraph topoGraph;

	public DFSFinishTimeOrderGraph(Graph g) {
		topoGraph = new TopoGraph(g);
	}

	public void dfsVisit(Graph.Vertex u, List<Graph.Vertex> decFinList){
		TopoVertex tv = this.topoGraph.getVertex(u);
		tv.seen = true;
		tv.disTime = ++time;
		tv.cno = componentNo;

		for (Graph.Edge e : u) {
			Graph.Vertex v = e.otherEnd(u);
			if (!seen(v)) {
				setParent(u, v);
				dfsVisit(v,decFinList);
			}
		}
		tv.finishTime = ++time;
		tv.top = topNum--;
		decFinList.add(0, u);
	}


	public void setParent(Graph.Vertex u, Graph.Vertex v){
		topoGraph.getVertex(v).parent = u;
	}

	public boolean seen(Graph.Vertex u) {
		return topoGraph.getVertex(u).seen;
	}
	
}
