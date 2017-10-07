package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities;

public class DisjointSets<T extends DisjointNode<T>> {

	T[] s;

	/**
	 * Construct the disjoint sets object.
	 *
	 * @param numElements
	 *            the initial number of disjoint sets
	 */
	public DisjointSets(int numElements) {
		s = (T[]) new Object[numElements];
	}

	public void makeSet(T u) {
		s[u.index] = u;
	}

	/**
	 * Union two disjoint sets using the height heuristic. For simplicity, we
	 * assume root1 and root2 are distinct and represent set names.
	 *
	 * @param root1
	 *            the root of set 1.
	 * @param root2
	 *            the root of set 2.
	 */
	public void union(int x, int y) {
		if (s[x].rank > s[y].rank) // root2 is deeper
			s[y].p = s[x];// Make root2 new root
		else if (s[y].rank > s[x].rank)
			s[x].p = s[y];
		else
			s[x].rank++;
		s[y].p = s[x];
	}

	/**
	 * Perform a find. Error checks omitted again for simplicity.Since union by
	 * height method is used, path compression is not done.
	 *
	 * @param x
	 *            the element being searched for.
	 * @return the set containing x.
	 */
	public int find(int x) {
		if (s[x].equals(s[x].p))
			return x;
		else
			return find(s[x].p.index);
	}
}
