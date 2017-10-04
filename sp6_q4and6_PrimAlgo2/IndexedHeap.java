package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp6_q4and6_PrimAlgo2;

import java.util.Comparator;

public class IndexedHeap<T extends Index> extends BinaryHeap<T> {
	/** Build a priority queue with a given array q */
	public IndexedHeap(T[] q, Comparator<T> comp, int n) {
		super(q, comp, n);
	}

	/** restore heap order property after the priority of x has decreased */
	public void decreaseKey(T x) {
		percolateUp(x.getIndex());
	}
}
