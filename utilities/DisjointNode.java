package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities;

public class DisjointNode {
	int index;
	public DisjointNode p;
	int rank;

	public DisjointNode(int index, DisjointNode p, int rank) {
		super();
		this.index = index;
		this.p = p;
		this.rank = rank;
	}
}