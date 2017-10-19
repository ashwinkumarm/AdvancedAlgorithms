package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp7_q2_AVLTree;

import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp7_q1_BST.BinarySearchTree;

public class AVLTree<T extends Comparable<? super T>> extends BinarySearchTree<T> {

	static class Entry<T> extends BinarySearchTree.Entry<T> {
		int height;

		public Entry(T x, Entry<T> left, Entry<T> right) {
			super(x, left, right);
			height = 0;
		}
	}

	/*
	 * protected Entry<T> root; public int size;
	 */

	public AVLTree() {
		super();
	}

	public boolean insert(T x) {
		if (root == null) {
			add(x);
			return true;
		}
		else {
			root = insert(root, x);
			return true;
		}
	}

	public Entry<T> insert(BinarySearchTree.Entry<T> root, T x) {
		Entry<T> entry = null;
		if (add(x)) {
			if (stack != null && !stack.isEmpty()) {
				entry = (Entry<T>) stack.pop();
				while (!stack.isEmpty() && entry != null) {
					Entry<T> parent = (Entry<T>) stack.pop();
					int balance = getHeight(entry.left) - getHeight(entry.right);
					// Left Left case
					if (balance > 1 && x.compareTo(entry.left.element) < 0)
						parent.left = rightRotate(entry);

					// Right Right Case
					if (balance < -1 && x.compareTo(entry.right.element) > 0)
						parent.right = leftRotate(entry);

					// Left Right Case
					if (balance > 1 && x.compareTo(entry.left.element) > 0) {
						parent.left = LeftRightRotate(entry);
					}

					// Right Left Case
					if (balance < -1 && x.compareTo(entry.right.element) < 0) {
						parent.right = RightLeftRotate(entry);
					}
					parent.height = 1 + Math.max(getHeight(parent.left), getHeight(parent.right));
					if (parent != null)
						entry = parent;
				}
				/*
				 * Entry<T> newNode = new Entry<>(x, null, null); if (root == null) { root =
				 * newNode;
				 * 
				 * root.height = 0; return root;
				 * 
				 * } else if (x.compareTo(root.element) < 0) { root.left = insert(root.left, x);
				 * 
				 * if ((getHeight(root.left) - getHeight(root.right)) == 2) { if
				 * (x.compareTo(root.left.element) < 0) root = leftRotate(root); else root =
				 * doubleLeftRotate(root); } } else if (x.compareTo(root.element) > 0) {
				 * root.right = insert(root.right, x);
				 * 
				 * if ((getHeight(root.right) - getHeight(root.left)) == 2) { if
				 * (x.compareTo(root.right.element) > 0) root = rightRotate(root); else root =
				 * doubleRightRotate(root); } }
				 * 
				 * root.height = Math.max(getHeight(root.left), getHeight(root.right))+1; return
				 * root;
				 */
			}
		}
		return entry != null ? entry : (Entry<T>) root;
	}

	public Entry<T> leftRotate(Entry<T> node) {
		Entry<T> otherNode = (Entry<T>) node.left;
		node.left = otherNode.right;
		otherNode.right = node;

		node.height = Math.max(getHeight(node.left), getHeight(node.left)) + 1;
		otherNode.height = Math.max(getHeight(otherNode.left), node.height) + 1;
		return otherNode;
	}

	public Entry<T> rightRotate(Entry<T> node) {
		Entry<T> otherNode = (Entry<T>) node.right;
		node.right = otherNode.left;
		otherNode.left = node;

		node.height = Math.max(getHeight(node.left), getHeight(node.left)) + 1;
		otherNode.height = Math.max(getHeight(otherNode.left), node.height) + 1;
		return otherNode;
	}

	public Entry<T> RightLeftRotate(Entry<T> node) {
		node.left = rightRotate((Entry<T>) node.left);
		return leftRotate(node);
	}

	public Entry<T> LeftRightRotate(Entry<T> node) {
		node.right = leftRotate((Entry<T>) node.right);
		return rightRotate(node);
	}

	public int getHeight(BinarySearchTree.Entry<T> entry) {
		Entry<T> node = (Entry<T>) entry;
		return node == null ? -1 : node.height;
	}

	public static void main(String args[]) {

		AVLTree<Integer> tree = new AVLTree<Integer>();

		tree.insert(new Integer(2));
		tree.insert(new Integer(1));
		tree.insert(new Integer(4));
		tree.insert(new Integer(5));
		tree.insert(new Integer(9));
		tree.insert(new Integer(3));
		tree.insert(new Integer(6));
		tree.insert(new Integer(7));

		tree.inOrder(tree.root);

	}
}
