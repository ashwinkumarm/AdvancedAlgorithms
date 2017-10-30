package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.lp3;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Graph;

/**
 * This class extends the graph class by adding features like disabling the
 * vertices and edges, vertices array with twice the size
 *
 * @author Ashwin, Arun, Deepak, Haritha
 *
 */
public class DMSTGraph extends Graph {
	DMSTVertex[] dmstVertexArray;

	/**
	 * Nested class to represent a DMST vertex of a DMST graph
	 */
	public static class DMSTVertex extends Vertex {
		int minEdge;
		DMSTEdge incomingEdge;
		boolean disabled;
		List<DMSTEdge> DMSTadj;
		List<DMSTEdge> DMSTrevadj;

		/**
		 * Constructor for DMST vertex
		 * 
		 * @param u
		 */
		public DMSTVertex(Vertex u) {
			super(u);
			minEdge = Integer.MAX_VALUE;
			disabled = false;
			DMSTadj = new LinkedList<>();
			DMSTrevadj = new LinkedList<>();
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
			return new DMSTVertexIterator(this);
		}

		@Override
		public Iterator<Edge> reverseIterator() {
			return new DMSTVertexRevIterator(this);
		}

		/**
		 * Iterates over all the vertices of the graph
		 * 
		 * @return
		 */
		public Iterator<Edge> allIterator() {
			return new DMSTVertexAllIterator(this);
		}

		/**
		 * Nested class for Vertex iterator
		 *
		 */
		class DMSTVertexIterator implements Iterator<Edge> {
			DMSTEdge cur;
			Iterator<DMSTEdge> it;
			boolean ready;

			/**
			 * Constructor for initializing the iterator
			 * 
			 * @param u
			 */
			DMSTVertexIterator(DMSTVertex u) {
				this.it = u.DMSTadj.iterator();
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
				while ((cur.isDisabled() || ((DMSTVertex) cur.from).isDisabled() || ((DMSTVertex) cur.to).isDisabled())
						&& it.hasNext()) {
					cur = it.next();
				}
				ready = true;
				return !(cur.isDisabled() || ((DMSTVertex) cur.from).isDisabled()
						|| ((DMSTVertex) cur.to).isDisabled());
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
		class DMSTVertexAllIterator implements Iterator<Edge> {
			DMSTEdge cur;
			Iterator<DMSTEdge> it;
			boolean ready;

			/**
			 * Constructor for initializing the all vertex iterator
			 * 
			 * @param u
			 */
			DMSTVertexAllIterator(DMSTVertex u) {
				this.it = u.DMSTadj.iterator();
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
				while ((((DMSTVertex) cur.from).isDisabled() || ((DMSTVertex) cur.to).isDisabled()) && it.hasNext()) {
					cur = it.next();
				}
				ready = true;
				return !(((DMSTVertex) cur.from).isDisabled() || ((DMSTVertex) cur.to).isDisabled());
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
		class DMSTVertexRevIterator implements Iterator<Edge> {
			DMSTEdge cur;
			Iterator<DMSTEdge> it;
			boolean ready;

			/**
			 * Constructor for initializing the vertex reverse iterator
			 * 
			 * @param u
			 */
			DMSTVertexRevIterator(DMSTVertex u) {
				this.it = u.DMSTrevadj.iterator();
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
				while ((cur.isDisabled() || ((DMSTVertex) cur.from).isDisabled() || ((DMSTVertex) cur.to).isDisabled())
						&& it.hasNext()) {
					cur = it.next();
				}
				ready = true;
				return !(cur.isDisabled() || ((DMSTVertex) cur.from).isDisabled()
						|| ((DMSTVertex) cur.to).isDisabled());
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
	 * Nested class for DMST Edge class
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
	public static class DMSTEdge extends Edge {

		boolean disabled;

		/**
		 * Disable the DMST Edge
		 */
		void disable() {
			disabled = true;
		}

		/**
		 * Enable the DMST Edge
		 */
		void enable() {
			disabled = false;
		}

		/**
		 * Constructor for initializing the DMST Edge
		 * 
		 * @param from
		 * @param to
		 * @param weight
		 * @param name
		 */
		DMSTEdge(Vertex from, Vertex to, int weight, int name) {
			super(from, to, weight, name);
		}

		/**
		 * Checks the disability status of the edge
		 * 
		 * @return
		 */
		boolean isDisabled() {
			DMSTVertex xfrom = (DMSTVertex) from;
			DMSTVertex xto = (DMSTVertex) to;
			return disabled || xfrom.isDisabled() || xto.isDisabled();
		}

	}

	/**
	 * Constructor for DMST Graph which creates the graph separately
	 * 
	 * @param g
	 */
	public DMSTGraph(Graph g) {
		super(g);
		dmstVertexArray = new DMSTVertex[2 * g.size()];

		for (Vertex u : g) {
			dmstVertexArray[u.getName()] = new DMSTVertex(u);
		}

		// Make copy of edges
		DMSTEdge dmstEdge;
		for (Vertex u : g) {
			for (Edge e : u.adj) {
				Vertex v = e.otherEnd(u);
				DMSTVertex x1 = getVertex(u);
				DMSTVertex x2 = getVertex(v);
				dmstEdge = new DMSTEdge(x1, x2, e.weight, e.getName());
				x1.DMSTadj.add(dmstEdge);
				x2.DMSTrevadj.add(dmstEdge);
				if (x2.minEdge > e.weight)
					x2.minEdge = e.weight;
			}
		}
	}

	/**
	 * Returns the DMST Vertex from vertex array
	 * 
	 * @param u
	 * @return
	 */
	DMSTVertex getVertex(Vertex u) {
		return Vertex.getVertex(dmstVertexArray, u);
	}

	@Override
	public Iterator<Vertex> iterator() {
		return new DMSTGraphIterator(this);
	}

	/**
	 * Adds the edge to the DMST graph
	 * 
	 * @param from
	 * @param to
	 * @param weight
	 * @param name
	 * @return
	 */
	public DMSTEdge addEdge(DMSTVertex from, DMSTVertex to, int weight, int name) {
		DMSTEdge e = new DMSTEdge(from, to, weight, name);
		if (directed) {
			from.DMSTadj.add(e);
			to.DMSTrevadj.add(e);
		} else {
			from.DMSTadj.add(e);
			to.DMSTadj.add(e);
		}
		if (to.minEdge > e.weight)
			to.minEdge = e.weight;
		m++; // Increment edge count
		return e;
	}

	/**
	 * Nested class for iterating he entire graph
	 *
	 */
	class DMSTGraphIterator implements Iterator<Vertex> {
		Iterator<DMSTVertex> it;
		DMSTVertex xcur;

		DMSTGraphIterator(DMSTGraph xg) {
			this.it = new ArrayIterator<DMSTVertex>(xg.dmstVertexArray, 0, xg.size() - 1); // Iterate
			// over
			// existing
			// elements
			// only
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
	 * Returns the DMST Vertex from DMST Vertex array for the given n
	 * 
	 * @param n
	 * @return
	 */
	public DMSTVertex getDMSTVertex(int n) {
		return dmstVertexArray[n - 1];
	}

	/**
	 * Returns the DMST Vertex with name from DMST Vertex array for the given n
	 * 
	 * @param n
	 * @return
	 */
	public DMSTVertex getDMSTVertexWithName(int n) {
		return dmstVertexArray[n];
	}

	/**
	 * Returns the DMST Vertex array
	 * 
	 * @return
	 */
	public DMSTVertex[] getDMSTVertexArray() {
		return dmstVertexArray;
	}

	/**
	 * Disables all the edges of the graph
	 */
	public void disableAllEdges() {
		for (Vertex v : this)
			for (Edge e : v)
				((DMSTEdge) e).disable();
	}

	/**
	 * Disables all the vertices of the graph
	 */
	public void disableAllVertices() {
		for (Vertex v : this)
			((DMSTVertex) v).disable();
	}

	/**
	 * Disables all the edges of the graph
	 */
	public void enableAllEdges() {
		for (Vertex v : this.dmstVertexArray)
			if (v != null) {
				for (Edge e : v)
					((DMSTEdge) e).enable();
			}
	}

	/**
	 * Enables all the vertices of the graph
	 */
	public void enableAllVertices() {
		for (Vertex v : this.dmstVertexArray)
			if (v != null) {
				((DMSTVertex) v).enable();
			}
	}

}
