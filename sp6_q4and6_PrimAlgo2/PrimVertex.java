package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp6_q4and6_PrimAlgo2;

import java.util.Comparator;

import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Graph;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Graph.Vertex;

class PrimVertex implements Comparator<PrimVertex>, Index {
	Graph.Vertex vertex;
	int d, index;
	boolean seen;
	Graph.Vertex parent;

	public PrimVertex(Vertex vertex, int d, int index, boolean seen, Graph.Vertex parent) {
		super();
		this.vertex = vertex;
		this.d = d;
		this.index = index;
		this.seen = seen;
		this.parent = parent;
	}

	public int compare(PrimVertex u, PrimVertex v) {
		if (u.d < v.d)
			return -1;
		else if (u.d == v.d)
			return 0;
		else
			return 1;
	}

	public void putIndex(int i) {
		index = i;
	}

	public int getIndex() {
		return index;
	}

	@Override
	public String toString() {
		return "PrimVertex [vertex=" + vertex + "]";
	}


}