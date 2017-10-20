package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp7_q2_AVLTree;

import java.util.Scanner;

import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp7_q1_BST.BinarySearchTree;

public class AVLTree<T extends Comparable<? super T>> extends BinarySearchTree<T> {

	static class Entry<T> extends BinarySearchTree.Entry<T> {
		int height;

		public Entry(T x, Entry<T> left, Entry<T> right) {
			super(x, left, right);
			height = 0;
		}
	}

	public AVLTree() {
		super();
	}

	public boolean insert(T x) {
		Entry<T> newEntry = new Entry<T>(x, null, null);
		insert(newEntry, x);
		return true;
	}

	public T delete(T x) {
		T deletedElement = remove(x);
		Entry<T> entry = null, newRoot = null;
		T prevChild;
		if (deletedElement != null) {
			if (stack != null) {
				entry = (Entry<T>) stack.pop();
				while (entry != null) {
					newRoot = null;
					prevChild = entry.element;
					int balance = getBalance(entry);
					// Left Left case
					if (balance > 1 && getBalance(entry.left) >= 0)
						newRoot = rightRotate(entry);

					// Right Right Case
					else if (balance < -1 && getBalance(entry.right) <= 0)
						newRoot = leftRotate(entry);

					// Left Right Case
					else if (balance > 1 && getBalance(entry.left) < 0)
						newRoot = LeftRightRotate(entry);

					// Right Left Case
					else if (balance < -1 && getBalance(entry.right) > 0)
						newRoot = RightLeftRotate(entry);

					Entry<T> parent = (Entry<T>) stack.pop();
					if (parent == null) {
						if (newRoot != null) {
							newRoot.height = 1 + Math.max(getHeight(newRoot.left), getHeight(newRoot.right));
							root = newRoot;
							break;
						} else {
							entry.height = 1 + Math.max(getHeight(entry.left), getHeight(entry.right));
							root = entry;
							break;
						}
					}

					if (newRoot != null && parent.left.element.compareTo(prevChild) == 0) {
						parent.left = newRoot;
					} else if (newRoot != null && parent.right.element.compareTo(prevChild) == 0) {
						parent.right = newRoot;
					}
					entry.height = 1 + Math.max(getHeight(entry.left), getHeight(entry.right));
					entry = parent;
				}
			}

		}
		return deletedElement;
	}

	public void insert(Entry<T> newEntry, T x) {
		Entry<T> entry = null, newRoot = null;
		T prevChild;
		if (add(newEntry)) {
			if (stack != null) {
				entry = (Entry<T>) stack.pop();
				while (entry != null) {
					newRoot = null;
					prevChild = entry.element;
					int balance = getBalance(entry);
					// Left Left case
					if (balance > 1 && x.compareTo(entry.left.element) < 0)
						newRoot = rightRotate(entry);

					// Right Right Case
					else if (balance < -1 && x.compareTo(entry.right.element) > 0)
						newRoot = leftRotate(entry);

					// Left Right Case
					else if (balance > 1 && x.compareTo(entry.left.element) > 0)
						newRoot = LeftRightRotate(entry);

					// Right Left Case
					else if (balance < -1 && x.compareTo(entry.right.element) < 0)
						newRoot = RightLeftRotate(entry);

					Entry<T> parent = (Entry<T>) stack.pop();
					if (parent == null) {
						if (newRoot != null) {
							newRoot.height = 1 + Math.max(getHeight(newRoot.left), getHeight(newRoot.right));
							root = newRoot;
						} else {
							entry.height = 1 + Math.max(getHeight(entry.left), getHeight(entry.right));
							// root = entry;
						}
						return;
					}
						if (newRoot != null && parent.left.element.compareTo(prevChild) == 0) {
							parent.left = newRoot;
						} else if (newRoot != null && parent.right.element.compareTo(prevChild) == 0) {
							parent.right = newRoot;
						}
						entry.height = 1 + Math.max(getHeight(entry.left), getHeight(entry.right));
						entry = parent;
					}
				}
			}
	}

	public Entry<T> rightRotate(Entry<T> node) {
		Entry<T> otherNode = (Entry<T>) node.left;
		node.left = otherNode.right;
		otherNode.right = node;

		node.height = Math.max(getHeight(node.left), getHeight(node.left)) + 1;
		otherNode.height = Math.max(getHeight(otherNode.left), node.height) + 1;
		return otherNode;
	}

	public Entry<T> leftRotate(Entry<T> node) {
		Entry<T> otherNode = (Entry<T>) node.right;
		node.right = otherNode.left;
		otherNode.left = node;

		node.height = Math.max(getHeight(node.left), getHeight(node.left)) + 1;
		otherNode.height = Math.max(getHeight(otherNode.left), node.height) + 1;
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

	public int getHeight(BinarySearchTree.Entry<T> entry) {
		Entry<T> node = (Entry<T>) entry;
		return node == null ? -1 : node.height;
	}

	public int getBalance(BinarySearchTree.Entry<T> entry) {
		if (entry == null)
			return 0;
		return getHeight(entry.left) - getHeight(entry.right);
	}

	public static void main(String args[]) {

		AVLTree<Integer> t = new AVLTree<Integer>();

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
