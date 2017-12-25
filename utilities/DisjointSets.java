package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities;

public class DisjointSets<T extends DisjointNode> {

	DisjointNode[] s;

	/**
	 * Construct the disjoint sets object.
	 *
	 * @param numElements
	 *            the initial number of disjoint sets
	 */
	public DisjointSets(int numElements) {
		s = new DisjointNode[numElements];
	}

	public void makeSet(T u) {
		s[u.index] = u;
		u.p = u;
	}

	/**
	 * Union two disjoint sets using the rank heuristic.
	 *
	 * @param x
	 *            the root of set 1.
	 * @param y
	 *            the root of set 2.
	 */
	public void union(int x, int y) {
		DisjointNode u = s[x], v = s[y];
		if (u.rank > v.rank) // root2 is deeper
			v.p = u;// Make root2 new root
		else if (v.rank > u.rank)
			u.p = v;
		else {
			u.rank++;
			v.p = u;
		}
	}

	/**
	 * Performs a find.
	 *
	 * @param x
	 *            the element being searched for.
	 * @return the set containing x.
	 */
	public int find(int x) {
		DisjointNode u = s[x];
		if (u.index != u.p.index)
			u.p = s[find(u.p.index)]; // path compresssion
		return u.p.index;
	}
}
