package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp7_q3_RedBlackTree;

import java.util.Scanner;

import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp7_q1_BST.BinarySearchTree;

public class RedBlackTree<T extends Comparable<? super T>> extends BinarySearchTree<T> {
	static class Entry<T> extends BinarySearchTree.Entry<T> {
		boolean isRed;

		Entry(T x, Entry<T> left, Entry<T> right) {
			super(x, left, right);
			isRed = true;
		}
	}

	RedBlackTree() {
		super();
	}

	public boolean insert(T x) {
		Entry<T> newEntry = new Entry<T>(x, null, null);
		boolean isAdded = add(newEntry);
		if (isAdded && stack != null) {
			Entry<T> parent = (Entry<T>) stack.peek();
			if (parent != null && parent.isRed) {
				repair(newEntry);
			}
		}

		((Entry<T>) root).isRed = false;
		return isAdded;
	}

	public void repair(Entry<T> t) {
		if (stack.peek() == root) {
			return;
		}

		Entry<T> parent = null, grandParent = null, uncleOfT = null;
		T prevChild;

		while (t.isRed) {
			Entry<T> newRoot;
			parent = (Entry<T>) stack.pop();

			if (t == root || parent == root || !parent.isRed) {
				return;
			}

			grandParent = (Entry<T>) stack.pop();
			uncleOfT = (Entry<T>) ((Entry<T>) grandParent.right == parent ? grandParent.left : grandParent.right);
			prevChild = grandParent.element;

			if (uncleOfT != null && uncleOfT.isRed) {
				grandParent.isRed = true;
				parent.isRed = false;
				uncleOfT.isRed = false;
				t = grandParent;
				continue;
			}

			else if (uncleOfT == null || !uncleOfT.isRed) {
				if (grandParent.left != null && parent.left != null && grandParent.left == parent && parent.left == t) {
					newRoot = rotateRight(grandParent);
					updateColor(parent, grandParent);
					updateTree(prevChild, newRoot);
					return;
				} else if (grandParent.right != null && parent.right != null && grandParent.right == parent
						&& parent.right == t) {
					newRoot = rotateLeft(grandParent);
					updateColor(parent, grandParent);
					updateTree(prevChild, newRoot);
					return;
				} else if (grandParent.left != null && parent.right != null && grandParent.left == parent
						&& parent.right == t) {
					grandParent.left = rotateLeft(parent);
					newRoot = rotateRight(grandParent);
					updateColor(newRoot, grandParent);
					updateTree(prevChild, newRoot);
					return;
				} else if (grandParent.right != null && parent.left != null && grandParent.right == parent
						&& parent.left == t) {
					grandParent.right = rotateRight(parent);
					newRoot = rotateLeft(grandParent);
					updateColor(newRoot, grandParent);
					updateTree(prevChild, newRoot);
					return;
				}
			}
		}
	}

	public void updateColor(Entry<T> newRoot, Entry<T> grandParent) {
		newRoot.isRed = false;
		grandParent.isRed = true;
	}

	public void updateTree(T prevChild, Entry<T> newRoot) {
		Entry<T> greatGp = (Entry<T>) stack.peek();
		if (greatGp != null && prevChild != null) {
			if (greatGp.left.element.compareTo(prevChild) == 0) {
				greatGp.left = newRoot;
			} else {
				greatGp.right = newRoot;
			}
		}
	}

	public Entry<T> rotateRight(Entry<T> t) {
		Entry<T> tLeft = (Entry<T>) t.left;
		t.left = tLeft.right;
		tLeft.right = t;
		if (t == root) {
			root = tLeft;
		}
		return tLeft;
	}

	public Entry<T> rotateLeft(Entry<T> t) {
		Entry<T> tRight = (Entry<T>) t.right;
		t.right = tRight.left;
		tRight.left = t;
		if (t == root) {
			root = tRight;
		}
		return tRight;
	}

	public static void main(String[] args) {
		RedBlackTree<Integer> t = new RedBlackTree<Integer>();

		Scanner in = new Scanner(System.in);
		while (in.hasNext()) {
			int x = in.nextInt();
			if (x > 0) {
				System.out.print("Add " + x + " : ");
				t.insert(x);
				t.printTree();
			} else if (x < 0) {
				System.out.print("Remove " + x + " : ");
				// t.delete(-x);
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
