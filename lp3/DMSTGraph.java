package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.lp3;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Graph;


public class DMSTGraph extends Graph {
	DMSTVertex[] dmstVertexArray;

	public static class DMSTVertex extends Vertex {
		int minEdge;
		boolean disabled;
		List<DMSTEdge> xadj;

		public DMSTVertex(Vertex u) {
			super(u);
			minEdge = -1;
			disabled = false;
			xadj = new LinkedList<>();
		}

		boolean isDisabled() {
			return disabled;
		}

		void disable() {
			disabled = true;
		}

		public Iterator<Edge> iterator() {
			return new DMSTVertexIterator(this);
		}

		class DMSTVertexIterator implements Iterator<Edge> {
			DMSTEdge cur;
			Iterator<DMSTEdge> it;
			boolean ready;

			DMSTVertexIterator(DMSTVertex u) {
				this.it = u.xadj.iterator();
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
				while (cur.isDisabled() && it.hasNext()) {
					cur = it.next();
				}
				ready = true;
				return !cur.isDisabled();
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

	public static class DMSTEdge extends Edge {

		boolean disabled;
		
		void disabled(){
			disabled = true;
		}

		DMSTEdge(Vertex from, Vertex to, int weight) {
			super(from, to, weight);
		}

		boolean isDisabled() {
			DMSTVertex xfrom = (DMSTVertex) from;
			DMSTVertex xto = (DMSTVertex) to;
			return disabled || xfrom.isDisabled() || xto.isDisabled();
		}

	}

	public DMSTGraph(Graph g) {
		super(g);
		dmstVertexArray = new DMSTVertex[2 * g.size()];

		for (Vertex u : g) {
			dmstVertexArray[u.getName()] = new DMSTVertex(u);
		}

		// Make copy of edges
		for (Vertex u : g) {
			int minEdgeWeight = Integer.MAX_VALUE;
			for (Edge e : u) {
				if (minEdgeWeight > e.weight) {
					minEdgeWeight = e.weight;
				}
				Vertex v = e.otherEnd(u);
				DMSTVertex x1 = getVertex(u);
				DMSTVertex x2 = getVertex(v);
				x1.xadj.add(new DMSTEdge(x1, x2, e.weight));
			}
			dmstVertexArray[u.getName()].minEdge = minEdgeWeight;
		}
	}
	
	DMSTVertex getVertex(Vertex u) {
		return Vertex.getVertex(dmstVertexArray, u);
	}


	@Override
	public Iterator<Vertex> iterator() {
		return new DMSTGraphIterator(this);
	}

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

	@Override
	public Vertex getVertex(int n) {
		return dmstVertexArray[n - 1];
	}

	public DMSTVertex[] getDMSTVertexArray() {
		return dmstVertexArray;
	}


}
