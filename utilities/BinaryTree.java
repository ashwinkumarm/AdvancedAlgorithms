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
}
