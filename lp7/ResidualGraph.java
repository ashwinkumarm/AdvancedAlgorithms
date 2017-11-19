package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.lp7;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.ArrayIterator;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Graph;

/**
 * This class extends the graph class by adding features like disabling the
 * vertices and edges, vertices array with twice the size
 *
 * @author Ashwin, Arun, Deepak, Haritha
 *
 */
public class ResidualGraph extends Graph {
	ResidueVertex[] residueVertexArray;

	/**
	 * Nested class to represent a Vertex of a ResidueGraph
	 */
	public static class ResidueVertex extends Vertex {
		boolean disabled;
		List<ResidueEdge> Residueadj;
		List<ResidueEdge> Residuerevadj;

		/**
		 * Constructor for Residue vertex
		 *
		 * @param u
		 */
		public ResidueVertex(Vertex u) {
			super(u);
			disabled = false;
			Residueadj = new LinkedList<>();
			Residuerevadj = new LinkedList<>();
		}

		/**
		 * Returns the disability status of a vertex
		 *
		 * @return
		 */
		boolean isDisabled() {
			return disabled;
		}

		/**
		 * Disables a vertex
		 */
		void disable() {
			disabled = true;
		}

		/**
		 * Enables a vertex
		 */
		void enable() {
			disabled = false;
		}

		@Override
		public Iterator<Edge> iterator() {
			return new ResidueVertexIterator(this);
		}

		@Override
		public Iterator<Edge> reverseIterator() {
			return new ResidueVertexRevIterator(this);
		}

		/**
		 * Iterates over all the vertices of the graph
		 *
		 * @return
		 */
		public Iterator<Edge> allIterator() {
			return new ResidueVertexAllIterator(this);
		}

		/**
		 * Nested class for Vertex iterator
		 *
		 */
		class ResidueVertexIterator implements Iterator<Edge> {
			ResidueEdge cur;
			Iterator<ResidueEdge> it;
			boolean ready;

			/**
			 * Constructor for initializing the iterator
			 *
			 * @param u
			 */
			ResidueVertexIterator(ResidueVertex u) {
				this.it = u.Residueadj.iterator();
				ready = false;
			}

			public boolean hasNext() {
				if (ready) {
					return true;
				}
				if (!it.hasNext()) {
					return false;
				}
				cur = it.next();
				while ((cur.isDisabled() || ((ResidueVertex) cur.from).isDisabled()
						|| ((ResidueVertex) cur.to).isDisabled()) && it.hasNext()) {
					cur = it.next();
				}
				ready = true;
				return !(cur.isDisabled() || ((ResidueVertex) cur.from).isDisabled()
						|| ((ResidueVertex) cur.to).isDisabled());
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

		/**
		 * Nested class for all vertex iterator
		 *
		 */
		class ResidueVertexAllIterator implements Iterator<Edge> {
			ResidueEdge cur;
			Iterator<ResidueEdge> it;
			boolean ready;

			/**
			 * Constructor for initializing the all vertex iterator
			 *
			 * @param u
			 */
			ResidueVertexAllIterator(ResidueVertex u) {
				this.it = u.Residueadj.iterator();
				ready = false;
			}

			public boolean hasNext() {
				if (ready) {
					return true;
				}
				if (!it.hasNext()) {
					return false;
				}
				cur = it.next();
				while ((((ResidueVertex) cur.from).isDisabled() || ((ResidueVertex) cur.to).isDisabled())
						&& it.hasNext()) {
					cur = it.next();
				}
				ready = true;
				return !(((ResidueVertex) cur.from).isDisabled() || ((ResidueVertex) cur.to).isDisabled());
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

		/**
		 * Nested class for reverse iterator
		 *
		 */
		class ResidueVertexRevIterator implements Iterator<Edge> {
			ResidueEdge cur;
			Iterator<ResidueEdge> it;
			boolean ready;

			/**
			 * Constructor for initializing the vertex reverse iterator
			 *
			 * @param u
			 */
			ResidueVertexRevIterator(ResidueVertex u) {
				this.it = u.Residuerevadj.iterator();
				ready = false;
			}

			public boolean hasNext() {
				if (ready) {
					return true;
				}
				if (!it.hasNext()) {
					return false;
				}
				cur = it.next();
				while ((cur.isDisabled() || ((ResidueVertex) cur.from).isDisabled()
						|| ((ResidueVertex) cur.to).isDisabled()) && it.hasNext()) {
					cur = it.next();
				}
				ready = true;
				return !(cur.isDisabled() || ((ResidueVertex) cur.from).isDisabled()
						|| ((ResidueVertex) cur.to).isDisabled());
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
	/**
	 * @author deepaks
	 *
	 */
	/**
	 * @author deepaks
	 *
	 */
	public static class ResidueEdge extends Edge {
		int capacity;
		boolean disabled;

		/**
		 * Disable the Residue Edge
		 */
		void disable() {
			disabled = true;
		}

		/**
		 * Enable the Residue Edge
		 */
		void enable() {
			disabled = false;
		}

		/**
		 * Constructor for initializing the Residue Edge
		 *
		 * @param from
		 * @param to
		 * @param weight
		 * @param name
		 */
		ResidueEdge(Vertex from, Vertex to, int weight, int capacity, int name) {
			super(from, to, weight, name);
			this.capacity = capacity;
		}

		/**
		 * Checks the disability status of the edge
		 *
		 * @return
		 */
		boolean isDisabled() {
			ResidueVertex xfrom = (ResidueVertex) from;
			ResidueVertex xto = (ResidueVertex) to;
			return disabled || xfrom.isDisabled() || xto.isDisabled();
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

		for (Vertex u : g) {
			residueVertexArray[u.getName()] = new ResidueVertex(u);
		}

		// Make copy of edges
		ResidueEdge residueEdge;
		for (Vertex u : g) {
			for (Edge e : u.adj) {
				Vertex v = e.otherEnd(u);
				ResidueVertex x1 = getVertex(u);
				ResidueVertex x2 = getVertex(v);
				residueEdge = new ResidueEdge(x1, x2, e.weight, capacity.get(e), e.getName());
				x1.Residueadj.add(residueEdge);
				x2.Residuerevadj.add(residueEdge);
				residueEdge = new ResidueEdge(x2, x1, e.weight, 0, e.getName());
				x2.Residueadj.add(residueEdge);
				x1.Residuerevadj.add(residueEdge);
			}
		}
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
		return new ResidueGraphIterator(this);
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
	public ResidueEdge addEdge(ResidueVertex from, ResidueVertex to, int weight, int name) {
		ResidueEdge e = new ResidueEdge(from, to, weight, name);
		if (directed) {
			from.Residueadj.add(e);
			to.Residuerevadj.add(e);
		} else {
			from.Residueadj.add(e);
			to.Residueadj.add(e);
		}
		m++; // Increment edge count
		return e;
	}

	/**
	 * Nested class for iterating he entire graph
	 *
	 */
	class ResidueGraphIterator implements Iterator<Vertex> {
		Iterator<ResidueVertex> it;
		ResidueVertex xcur;

		ResidueGraphIterator(ResidualGraph xg) {
			this.it = new ArrayIterator<ResidueVertex>(xg.residueVertexArray, 0, xg.size() - 1);
		}

		public boolean hasNext() {
			if (!it.hasNext()) {
				return false;
			}
			xcur = it.next();
			while (xcur.isDisabled() && it.hasNext()) {
				xcur = it.next();
			}
			return !xcur.isDisabled();
		}

		public Vertex next() {
			return xcur;
		}

		public void remove() {
		}

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
	 * Disables all the edges of the graph
	 */
	public void disableAllEdges() {
		for (Vertex v : this)
			for (Edge e : v)
				((ResidueEdge) e).disable();
	}

	/**
	 * Disables all the vertices of the graph
	 */
	public void disableAllVertices() {
		for (Vertex v : this)
			((ResidueVertex) v).disable();
	}

	/**
	 * Disables all the edges of the graph
	 */
	public void enableAllEdges() {
		for (Vertex v : this.residueVertexArray)
			if (v != null) {
				for (Edge e : v)
					((ResidueEdge) e).enable();
			}
	}

	/**
	 * Enables all the vertices of the graph
	 */
	public void enableAllVertices() {
		for (Vertex v : this.residueVertexArray)
			if (v != null) {
				((ResidueVertex) v).enable();
			}
	}

}
