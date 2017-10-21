package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp7_q4_SplayTree;

import java.util.Scanner;

import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp7_q1_BST.BinarySearchTree;

public class SplayTree<T extends Comparable<? super T>> extends BinarySearchTree<T> {
	SplayTree() {
		super();
	}

	public boolean insert(T x) {
		if (root == null) {
			add(x);
			return true;
		}
		add(x);
		root = splay(root, x);
		return true;
	}

	public T delete(T x) {
		if (root == null)
			return null;
		T removedElement = remove(x);
		if (stack != null)
			root = splay(root, stack.pop().element);
		return removedElement;
	}

	public Entry<T> splay(Entry<T> root, T x) {
		Entry<T> newRoot = null, parent = null, g_parent = null;
		T prevChild;
		if (stack != null) {
			parent = stack.pop();
		}
		while (parent != null && root.element.compareTo(x) != 0) {
			newRoot = null;
			if (parent != null && stack.size() == 1) {
				if (root.left != null && x.compareTo(root.left.element) == 0)
					return rightRotate(root);
				else if (root.right != null && x.compareTo(root.right.element) == 0)
					return leftRotate(root);
			} else if (parent != null && stack.size() > 1) {
				g_parent = stack.pop();
				prevChild = g_parent.element;
				if (g_parent.left != null && parent.left != null && g_parent.left.element.compareTo(parent.element) == 0
						&& parent.left.element.compareTo(x) == 0) {
					g_parent.left = rightRotate(parent);
					newRoot = rightRotate(g_parent);
				} else if (g_parent.right != null && parent.right != null
						&& g_parent.right.element.compareTo(parent.element) == 0
						&& parent.right.element.compareTo(x) == 0) {
					g_parent.right = leftRotate(parent);
					newRoot = leftRotate(g_parent);
				} else if (g_parent.left != null && parent.right != null
						&& g_parent.left.element.compareTo(parent.element) == 0
						&& parent.right.element.compareTo(x) == 0) {
					newRoot = LeftRightRotate(g_parent);
				} else if (g_parent.right != null && parent.left != null
						&& g_parent.right.element.compareTo(parent.element) == 0
						&& parent.left.element.compareTo(x) == 0) {
					newRoot = RightLeftRotate(g_parent);
				}

				parent = stack.pop();
				if (parent != null && newRoot != null) {
					if (parent.left != null && parent.left.element.compareTo(prevChild) == 0)
						parent.left = newRoot;
					if (parent.right != null && parent.right.element.compareTo(prevChild) == 0)
						parent.right = newRoot;
				}
			}
		}
		return newRoot != null ? newRoot : root;
	}

	public Entry<T> rightRotate(Entry<T> node) {
		Entry<T> otherNode = (Entry<T>) node.left;
		node.left = otherNode.right;
		otherNode.right = node;

		return otherNode;
	}

	public Entry<T> leftRotate(Entry<T> node) {
		Entry<T> otherNode = (Entry<T>) node.right;
		node.right = otherNode.left;
		otherNode.left = node;

		return otherNode;
	}

	public Entry<T> RightLeftRotate(Entry<T> node) {
		node.right = rightRotate((Entry<T>) node.right);
		return leftRotate(node);
	}

	public Entry<T> LeftRightRotate(Entry<T> node) {
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