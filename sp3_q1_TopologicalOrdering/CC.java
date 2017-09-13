package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp3_q1_TopologicalOrdering;

public class CC {

	class CCVertex {
		Graph.Vertex element;
		int inDegree,top;

		CCVertex(Graph.Vertex u) {
			element = u;
			inDegree = 0;
			top = -1;
		}
	}

	// Algorithm uses a parallel array for storing information about vertices
	CCVertex[] ccVertex;
	Graph g;

	public CC(Graph g) {
		this.g = g;
		ccVertex = new CCVertex[g.size()];
		for (Graph.Vertex u : g) {
			ccVertex[u.name] = new CCVertex(u);
		}
	}
	
	

	// Visit a node by marking it as seen and assigning it a component no
	void visit(Graph.Vertex u, int cno) {
		CCVertex ccu = getCCVertex(u);
	}

	// From Vertex to CCVertex (ugly)
	CCVertex getCCVertex(Graph.Vertex u) {
		return ccVertex[u.name];
	}

	// From CCVertex to Vertex
	Graph.Vertex getVertex(CCVertex c) {
		return c.element;
	}
}
