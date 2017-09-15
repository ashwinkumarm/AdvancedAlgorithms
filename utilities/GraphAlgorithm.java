package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities;

public class GraphAlgorithm<T> {
	protected Graph g;
	// Algorithm uses a parallel array for storing information about vertices
	public T[] node;

	public GraphAlgorithm(Graph g) {
		this.g = g;
	}

	public T getVertex(Graph.Vertex u) {
		return Graph.Vertex.getVertex(node, u);
	}
}