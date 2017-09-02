package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp1_q2_GraphDiameter;

public class CC {

	class CCVertex {
		Graph.Vertex element;
		boolean seen;
		Graph.Vertex parent;

		CCVertex(Graph.Vertex u) {
			element = u;
			seen = false;
			parent = null;
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

	boolean seen(Graph.Vertex u) {
		CCVertex ccu = getCCVertex(u);
		return ccu.seen;
	}

	// Visit a node by marking it as seen and assigning it a component no
	void visit(Graph.Vertex u, int cno) {
		CCVertex ccu = getCCVertex(u);
		ccu.seen = true;
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
