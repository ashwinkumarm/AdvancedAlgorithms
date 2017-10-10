package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities;

public class BinaryTree<T> {

	public static class Entry<T> {
		public T element;
		public Entry<T> left;
		public Entry<T> right;

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

	// Dummy header is used. tail stores reference of tail element of list
	public Entry<T> root;
	public int size;

	/**
	 * Constructor to initialize the list
	 */
	public BinaryTree() {
		root = new Entry<>(null, null, null);
		size = 0;
	}

	public void visit(Entry<T> entry) {
		System.out.println(entry.element);
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

	public void postOrder(Entry<T> r) {
		if (r != null) {
			postOrder(r.left);
			postOrder(r.right);
			visit(r);
		}
	}

	public void preOrder(Entry<T> r) {
		if (r != null) {
			visit(r);
			preOrder(r.left);
			preOrder(r.right);
		}
	}

	public void inOrder(Entry<T> r) {
		if (r != null) {
			inOrder(r.left);
			visit(r);
			inOrder(r.right);
		}
	}
}
