package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp7_q2_AVLTree;

import java.util.Scanner;

import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp7_q1_BST.BinarySearchTree;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.BinaryTree;

public class AVLTree<T extends Comparable<? super T>> extends BinarySearchTree<T> {

	public static class Entry<T> extends BinarySearchTree.Entry<T> {
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
		boolean isAdded = add(newEntry);
		if (isAdded && stack != null)
			root = balance();
		return isAdded;
	}

	public T delete(T x) {
		T deletedElement = remove(x);
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

		Entry<T> entry = null;
		Entry<T> parent;
			parent = (Entry<T>) stack.pop();
					entry = LeftRightRotate(entry);
				setParentChild(parent, entry);
			} else if (balance < -1) {
				if (getHeight(entry.right.right) >= getHeight(entry.right.left))
					entry = leftRotate(entry);
				else
					entry = RightLeftRotate(entry);
				setParentChild(parent, entry);
			} else
				entry.height = Math.max(getHeight(entry.left), getHeight(entry.right)) + 1;
			if (parent == null)
				break;
			entry = parent;
		}
		return entry;

	}

	private void setParentChild(Entry<T> parent, Entry<T> entry) {
		if (parent != null)
			if (parent.element.compareTo(entry.element) > 0) {
				parent.left = entry;
			} else {
				parent.right = entry;
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

	public int getHeight(BinaryTree.Entry<T> entry) {
		Entry<T> node = (Entry<T>) entry;
		return node == null ? -1 : node.height;
	}

	public int getBalance(BinaryTree.Entry<T> entry) {
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
