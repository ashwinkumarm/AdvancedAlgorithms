package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

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
	public int maximumWeight;

	/**
	 * Nested class to represent a Vertex of a ResidueGraph
	 */
	public static class ResidueVertex extends Vertex {
		public int height, excess, p, priority;
		List<ResidueEdge> residueAdj;
		List<ResidueEdge> residueRevadj;
		public Iterator<Edge> iterator;

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

		public int getPriority() {
			return priority;
		}

		public void setPriority(int priority) {
			this.priority = priority;
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
				while (!cur.inGf(u) && it.hasNext()) {
					cur = it.next();
				}
				ready = cur.inGf(u);
				return ready;
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
		public int flow;
		public boolean isEr = false;

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

		/**
		 * Method to calculate the residual capacity of an edge based on whether
		 * it is an actual edge from the graph or eR.
		 *
		 * @param u
		 * @return
		 */
		public int getResidualCapacity(Vertex u) {
			return from == u ? edgeCapacity.get(this) - flow : flow;
		}

		/**
		 * Method to check if the edge (edge out of u in Gf because of e/ edge
		 * out of u in Gf because of flow) has residual capacity greater than 0.
		 *
		 * @param u
		 * @param e
		 * @return
		 */
		public boolean inGf(Vertex u) {
			return from == u ? flow < edgeCapacity.get(this) : flow > 0;
		}

		public int cost(Vertex u) {
			return from == u ? weight : -weight;
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
				if (e.weight > maximumWeight)
					maximumWeight = e.weight;
				Vertex v = e.otherEnd(u);
				ResidueVertex x2 = getVertex(v);
				residueEdge = new ResidueEdge(x1, x2, e.weight, capacity.get(e), e.getName());
				x1.residueAdj.add(residueEdge);
				x2.residueRevadj.add(residueEdge);
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
	public ResidueVertex getVertex(Vertex u) {
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

	/**
	 * This method gets the edge from the graph.
	 *
	 * @param parent
	 * @param vertex
	 * @param g
	 * @return
	 */
	public ResidueEdge getEdgeFromGraph(Vertex parent, Vertex vertex) {
		for (Edge e : ((ResidueVertex) parent).residueAdj)
			if (e.otherEnd(parent).getName() == vertex.getName())
				return (ResidueEdge) e;
		return null;
	}
}