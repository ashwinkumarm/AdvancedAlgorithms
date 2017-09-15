package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities;

import java.util.List;

import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Graph;

public class DFS extends GraphAlgorithm<DFS.DFSVertex> {
	
	public static int cno, time;

	// Class to store information about a vertex in this algorithm
	static class DFSVertex {
		
		Graph.Vertex element;
		Graph.Vertex parent;
		int cno, startTime, finishTime;
		GraphVertexColor visitStatus;

		DFSVertex(Graph.Vertex u) {
			element = u;
			parent = null;
			visitStatus = GraphVertexColor.WHITE;
		}

	}

	public DFS(Graph g) {
		super(g);
		node = new DFSVertex[g.size()];
		// Create array for storing vertex properties
		for (Graph.Vertex u : g) {
			node[u.getName()] = new DFSVertex(u);
		}
		cno = 0;
		time = 0;
	}
	
	public void dfsVisit(Graph.Vertex u, List<Graph.Vertex> decFinList){
		
		visit(u);
		for (Graph.Edge e : u) {
			Graph.Vertex v = e.otherEnd(u);
			if (getVertexStatus(v) == GraphVertexColor.WHITE) {
				setParent(u, v); 
				dfsVisit(v, decFinList);
			}
		}
		setVertexStatus(u, GraphVertexColor.BLACK);
		setFinishTime(u);
		decFinList.add(0, u);
	
	}
	
	public boolean dfsVisitAndIsDAG(Graph.Vertex u, List<Graph.Vertex> decFinList){
		
		visit(u);
		for (Graph.Edge e : u) {
			Graph.Vertex v = e.otherEnd(u);
			if (getVertexStatus(v) == GraphVertexColor.WHITE) {
				setParent(u, v); 
				if(!dfsVisitAndIsDAG(v,decFinList)){
					return false;
				}
			}
			else if(getVertexStatus(v) == GraphVertexColor.GREY){
				return false;
			}
		}
		setVertexStatus(u, GraphVertexColor.BLACK);
		setFinishTime(u);
		decFinList.add(0, u);
		return true;
	}

	public void visit(Graph.Vertex u){
		setVertexStatus(u, GraphVertexColor.GREY);
		setCno(u);
		setStartTime(u);
	}
	
	public void setParent(Graph.Vertex u, Graph.Vertex v){
		getVertex(v).parent = u;
	}
	
	public void setCno(Graph.Vertex u){
		getVertex(u).cno = cno;
	}
	
	public void setFinishTime(Graph.Vertex u){
		getVertex(u).finishTime = ++time;
	}
	
	public void setStartTime(Graph.Vertex u){
		getVertex(u).startTime = ++time;
	}
	
	public void setVertexStatus(Graph.Vertex u, GraphVertexColor visitStatus){
		getVertex(u).visitStatus = visitStatus;
	}
	
	public GraphVertexColor getVertexStatus(Graph.Vertex u){
		 return getVertex(u).visitStatus;
	}

}