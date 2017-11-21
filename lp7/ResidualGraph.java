package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.lp7;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.ArrayIterator;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Graph;

/**
 * This class extends the graph class with a modified iterator to iterate only
 * through the edges which has a positive residual capacity for maximum flow
 * applications.
 *
 * @author Ashwin, Arun, Deepak, Haritha
 *
 */
public class ResidualGraph extends Graph {
	ResidueVertex[] residueVertexArray;
	static HashMap<Edge, Integer> edgeCapacity;

	/**
	 * Nested class to represent a Vertex of a ResidueGraph
	 */
	public static class ResidueVertex extends Vertex {
		int height, excess;
		List<ResidueEdge> residueAdj;
		List<ResidueEdge> residueRevadj;

		/**
		 * Constructor for Residue vertex
		 *
		 * @param u
		 */
		public ResidueVertex(Vertex u) {
			super(u);
			residueAdj = new LinkedList<>();
			residueRevadj = new LinkedList<>();
		}

		@Override
		public Iterator<Edge> iterator() {
			return new ResidueVertexIterator(this);
		}

		/**
		 * Nested class for Vertex iterator
		 *
		 */
		class ResidueVertexIterator implements Iterator<Edge> {
			ResidueEdge cur;
			Iterator<ResidueEdge> it;
			boolean ready;
			Vertex u;

			/**
			 * Constructor for initializing the iterator
			 *
			 * @param u
			 */
			ResidueVertexIterator(ResidueVertex u) {
				this.it = u.residueAdj.iterator();
				ready = false;
				this.u = u;
			}

			public boolean hasNext() {
				if (ready) {
					return true;
				}
				if (!it.hasNext()) {
					return false;
				}
				cur = it.next();
				while (((cur.from == u && edgeCapacity.get(cur) - cur.flow == 0) || cur.flow > 0) && it.hasNext()) {
					cur = it.next();
				}
				ready = true;
				return !((cur.from == u && edgeCapacity.get(cur) - cur.flow == 0) || cur.flow > 0);
			}

			public Edge next() {
				if (!ready) {
					if (!hasNext()) {
						throw new java.util.NoSuchElementException();
					}
				}
				ready = false;
				return cur;
			}

			public void remove() {
				throw new java.lang.UnsupportedOperationException();
			}
		}
	}

	/**
	 * Nested class for Residue Edge class
	 *
	 */
	public static class ResidueEdge extends Edge {
		int flow;

		/**
		 * Constructor for initializing the Residue Edge
		 *
		 * @param from
		 * @param to
		 * @param weight
		 * @param name
		 */
		ResidueEdge(Vertex from, Vertex to, int weight, int residualCapacity, int name) {
			super(from, to, weight, name);
		}
	}

	/**
	 * Constructor for Residue Graph which creates the graph separately
	 *
	 * @param g
	 * @param capacity
	 */
	public ResidualGraph(Graph g, HashMap<Edge, Integer> capacity) {
		super(g);
		residueVertexArray = new ResidueVertex[g.size()];
		edgeCapacity = capacity;
		for (Vertex u : g) {
			residueVertexArray[u.getName()] = new ResidueVertex(u);
		}

		// Make copy of edges
		ResidueEdge residueEdge;
		for (Vertex u : g) {
			ResidueVertex x1 = getVertex(u);
			for (Edge e : u) {
				Vertex v = e.otherEnd(u);
				ResidueVertex x2 = getVertex(v);
				residueEdge = new ResidueEdge(x1, x2, e.weight, capacity.get(e), e.getName());
				x1.residueAdj.add(residueEdge);
				x2.residueRevadj.add(residueEdge);
				// TODO: x2.adj.add(residueEdge);
			}
		}
		for (ResidueVertex u : residueVertexArray)
			u.residueAdj.addAll(u.residueRevadj);
	}

	/**
	 * Returns the Residue Vertex from vertex array
	 *
	 * @param u
	 * @return
	 */
	ResidueVertex getVertex(Vertex u) {
		return Vertex.getVertex(residueVertexArray, u);
	}

	@Override
	public Iterator<Vertex> iterator() {
		return new ArrayIterator<Vertex>(residueVertexArray);
	}

	/**
	 * Adds the edge to the Residue graph
	 *
	 * @param from
	 * @param to
	 * @param weight
	 * @param name
	 * @return
	 */
	public ResidueEdge addEdge(ResidueVertex from, ResidueVertex to, int weight, int capacity, int name) {
		ResidueEdge e = new ResidueEdge(from, to, weight, capacity, name);
		if (directed) {
			from.residueAdj.add(e);
			to.residueRevadj.add(e);
		} else {
			from.residueAdj.add(e);
			to.residueAdj.add(e);
		}
		m++; // Increment edge count
		return e;
	}

	/**
	 * Returns the Residue Vertex from Residue Vertex array for the given n
	 *
	 * @param n
	 * @return
	 */
	public ResidueVertex getResidueVertex(int n) {
		return residueVertexArray[n - 1];
	}

	/**
	 * Returns the Residue Vertex with name from Residue Vertex array for the
	 * given n
	 *
	 * @param n
	 * @return
	 */
	public ResidueVertex getResidueVertexWithName(int n) {
		return residueVertexArray[n];
	}

	/**
	 * Returns the Residue Vertex array
	 *
	 * @return
	 */
	public ResidueVertex[] getResidueVertexArray() {
		return residueVertexArray;
	}
}