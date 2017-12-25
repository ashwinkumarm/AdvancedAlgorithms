package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities;

/** @author rbk
 *  Ver 1.0: 2017/09/29
 *  Example to extend Graph/Vertex/Edge classes to implement algorithms in which nodes and edges
 *  need to be disabled during execution.  Design goal: be able to call other graph algorithms
 *  without changing their codes to account for disabled elements.
 **/

import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Graph;
//import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Graph.Vertex;
//import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Graph.Edge;

import java.util.Iterator;
import java.util.List;
import java.util.LinkedList;
import java.util.Scanner;

public class XGraph extends Graph {
	public static class XVertex extends Vertex {
		boolean disabled;
		List<XEdge> xadj;

		XVertex(Vertex u) {
			super(u);
			disabled = false;
			xadj = new LinkedList<>();
		}

		boolean isDisabled() {
			return disabled;
		}

		void disable() {
			disabled = true;
		}

		@Override
		public Iterator<Edge> iterator() {
			return new XVertexIterator(this);
		}

		class XVertexIterator implements Iterator<Edge> {
			XEdge cur;
			Iterator<XEdge> it;
			boolean ready;

			XVertexIterator(XVertex u) {
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

	static class XEdge extends Edge {
		boolean disabled;

		XEdge(XVertex from, XVertex to, int weight) {
			super(from, to, weight);
			disabled = false;
		}

		void edgeDisable(XEdge e){
			e.disabled = true;
		}
		boolean isDisabled() {
			XVertex xfrom = (XVertex) from;
			XVertex xto = (XVertex) to;
			return disabled || xfrom.isDisabled() || xto.isDisabled();
		}
	}

	XVertex[] xv; // vertices of graph

	public XGraph(Graph g) {
		super(g);
		xv = new XVertex[2 * g.size()]; // Extra space is allocated in array for
										// nodes to be added later
		for (Vertex u : g) {
			xv[u.getName()] = new XVertex(u);
		}

		// Make copy of edges
		for (Vertex u : g) {
			for (Edge e : u) {
				Vertex v = e.otherEnd(u);
				XVertex x1 = getVertex(u);
				XVertex x2 = getVertex(v);
				x1.xadj.add(new XEdge(x1, x2, e.weight));
			}
		}
	}

	@Override
	public Iterator<Vertex> iterator() {
		return new XGraphIterator(this);
	}

	class XGraphIterator implements Iterator<Vertex> {
		Iterator<XVertex> it;
		XVertex xcur;

		XGraphIterator(XGraph xg) {
			this.it = new ArrayIterator<XVertex>(xg.xv, 0, xg.size() - 1); // Iterate
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
		return xv[n - 1];
	}

	XVertex getVertex(Vertex u) {
		return Vertex.getVertex(xv, u);
	}

	void disable(int i) {
		XVertex u = (XVertex) getVertex(i);
		u.disable();
	}

	public static void main(String[] args) {
		Graph g = Graph.readGraph(new Scanner(System.in));
		XGraph xg = new XGraph(g);
		Vertex src = xg.getVertex(2);

		System.out.println("Node : Dist : Edges");
		BFS b = new BFS(xg, src);
		b.bfs();
		Vertex farthest = DiameterTree.findFarthest(b);
		xg.printGraph(b,xg);
		System.out.println("Source: " + src + " Farthest: " + farthest + " Distance: " + b.distance(farthest));

		System.out.println("\nDisabling vertices 8 and 9");
		xg.disable(7);
		xg.disable(8);
		
		b.reinitialize(src);
		b.bfs();
		farthest = DiameterTree.findFarthest(b);
		xg.printGraph(b,xg);
		System.out.println("Source: " + src + " Farthest: " + farthest + " Distance: " + b.distance(farthest));
	}

	void printGraph(BFS b, Graph xg) {
		for (Vertex u : xg) {
			System.out.print("  " + u + "  :   " + b.distance(u) + "  : ");
			for (Edge e : u) {
				System.out.print(e);
			}
			System.out.println();
		}

	}
}
