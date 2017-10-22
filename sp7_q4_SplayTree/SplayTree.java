package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp7_q4_SplayTree;

import java.util.Scanner;

import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp7_q1_BST.BinarySearchTree;

public class SplayTree<T extends Comparable<? super T>> extends BinarySearchTree<T> {
	SplayTree() {
		super();
	}

	public boolean insert(T x) {
		Entry<T> newEntry = new Entry<T>(x, null, null);
		boolean isAdded = add(newEntry);
		if (isAdded && stack != null)
			splay(newEntry);
		return isAdded;
	}

	public T delete(T x) {
		T removedElement = remove(x);
		if (removedElement != null && stack != null)
			splay(stack.pop());
		return removedElement;
	}

	// TODO: Add methods for contains, get, min, max

	public void splay(Entry<T> t) {
		Entry<T> parent = null, grandParent = null;
		T prevChild;
		while (t != root) {
			if (root != null && (root.left == t || root.right == t)) {
				if (root.left != null && root.left == t)
					root = rightRotate((Entry<T>) root);
				else if (root.right != null && root.right == t)
					root = leftRotate((Entry<T>) root);
			} else if (stack.size() > 2) {
				parent = stack.pop();
				grandParent = stack.pop();
				prevChild = grandParent.element;
				if (grandParent.left != null && parent.left != null && grandParent.left == parent && parent.left == t) {
					grandParent.left = rightRotate(parent);
					t = rightRotate(grandParent);
				} else if (grandParent.right != null && parent.right != null && grandParent.right == parent
						&& parent.right == t) {
					grandParent.right = leftRotate(parent);
					t = leftRotate(grandParent);
				} else if (grandParent.left != null && parent.right != null && grandParent.left == parent
						&& parent.right == t) {
					t = leftRightRotate(grandParent);
				} else if (grandParent.right != null && parent.left != null && grandParent.right == parent
						&& parent.left == t) {
					t = rightLeftRotate(grandParent);
				}
				updateTree(prevChild, t);
			}
		}
	}

	public void updateTree(T prevChild, Entry<T> t) {
		Entry<T> greatGp = stack.peek();
		if (greatGp != null) {
			if (greatGp.left != null && greatGp.left.element.compareTo(prevChild) == 0)
				greatGp.left = t;
			if (greatGp.right != null && greatGp.right.element.compareTo(prevChild) == 0)
				greatGp.right = t;
		} else
			root = t;
	}

	public Entry<T> rightRotate(Entry<T> root) {
		Entry<T> otherNode = (Entry<T>) root.left;
		root.left = otherNode.right;
		otherNode.right = root;

		return otherNode;
	}

	public Entry<T> leftRotate(Entry<T> node) {
		Entry<T> otherNode = (Entry<T>) node.right;
		node.right = otherNode.left;
		otherNode.left = node;

		return otherNode;
	}

	public Entry<T> rightLeftRotate(Entry<T> node) {
		node.right = rightRotate((Entry<T>) node.right);
		return leftRotate(node);
	}

	public Entry<T> leftRightRotate(Entry<T> node) {
		node.left = leftRotate((Entry<T>) node.left);
		return rightRotate(node);
	}

	public static void main(String args[]) {

		SplayTree<Integer> t = new SplayTree<Integer>();

		Scanner in = new Scanner(System.in);
		while (in.hasNext()) {
			int x = in.nextInt();
			if (x > 0) {
				System.out.print("Add " + x + " : ");
				t.insert(x);
				t.printTree();
			} else if (x < 0) {
				System.out.print("Remove " + x + " : ");
				t.delete(-x);
				t.printTree();
			} else {
				Comparable[] arr = t.toArray();
				System.out.print("Final: ");
				for (int i = 0; i < t.size; i++) {
					System.out.print(arr[i] + " ");
				}
				System.out.println();
				in.close();
				return;
			}
		}
		in.close();
	}
}