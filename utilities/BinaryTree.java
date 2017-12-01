package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities;

/**
 * This class implements the binary tree
 *
 * @author Ashwin, Arun, Deepak, Haritha
 *
 * @param <T>
 */
public class BinaryTree<T> {

	/**
	 * This class implements the entry of Binary Tree
	 *
	 * @param <T>
	 */
	public static class Entry<T> {
		public T element;
		public Entry<T> left, right;

		/**
		 *
		 * Constructor to initialize an entry in the tree
		 *
		 * @param x
		 * @param nxt
		 * @param prv
		 */
		public Entry(T x, Entry<T> left, Entry<T> right) {
			element = x;
			this.left = left;
			this.right = right;
		}
	}

	public Entry<T> root;
	public int size;

	/**
	 * Constructor to initialize the Binary Tree
	 */
	public BinaryTree() {
		root = null;
		size = 0;
	}

	/**
	 * Prints the element of given entry
	 *
	 * @param entry
	 */
	public void visit(Entry<T> entry) {
		System.out.print(" " + entry.element);
	}

	public void postOrder() {
		postOrder(root);
	}

	public void preOrder() {
		preOrder(root);
	}

	public void inOrder() {
		inOrder(root);
	}

	/**
	 * Prints the elements of the binary tree in post-order
	 *
	 * @param r
	 */
	public void postOrder(Entry<T> r) {
		if (r != null) {
			postOrder(r.left);
			postOrder(r.right);
			visit(r);
		}
	}

	/**
	 * Prints the elements of the binary tree in pre-order
	 *
	 * @param r
	 */
	public void preOrder(Entry<T> r) {
		if (r != null) {
			visit(r);
			preOrder(r.left);
			preOrder(r.right);
		}
	}

	/**
	 * Prints the elements of the binary tree in in-order
	 *
	 * @param r
	 */
	public void inOrder(Entry<T> r) {
		if (r != null) {
			inOrder(r.left);
			visit(r);
			inOrder(r.right);
		}
	}

	public BinaryTree<T> reconstruct(T[] inOrder, T[] postOrder) {
		Index index = new Index();
		index.index = inOrder.length - 1;
		Entry<T> root = reconstruct(inOrder, postOrder, 0, inOrder.length - 1, index);
		BinaryTree<T> t = new BinaryTree<>();
		t.root = root;
		return t;
	}

	class Index {
		int index;
	}

	Entry<T> reconstruct(T[] inOrder, T[] postOrder, int start, int end, Index rootIndex) {
		if (start > end)
			return null;

		Entry<T> node = new Entry<T>(postOrder[rootIndex.index], null, null);
		rootIndex.index--;

		if (start == end)
			return node;

		int iIndex = search(inOrder, start, end, node.element);

		node.right = reconstruct(inOrder, postOrder, iIndex + 1, end, rootIndex);
		node.left = reconstruct(inOrder, postOrder, start, iIndex - 1, rootIndex);

		return node;
	}

	int search(T arr[], int strt, int end, T value) {
		int i;
		for (i = strt; i <= end; i++) {
			if (arr[i].equals(value))
				break;
		}
		return i;
	}
}
