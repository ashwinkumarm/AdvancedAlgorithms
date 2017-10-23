package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp7_q3_RedBlackTree;

import java.util.Scanner;

import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp7_q1_BST.BinarySearchTree;

public class RedBlackTree1<T extends Comparable<? super T>> extends BinarySearchTree<T> {
	static class Entry<T> extends BinarySearchTree.Entry<T> {
		boolean isRed;

		Entry(T x, Entry<T> left, Entry<T> right) {
			super(x, left, right);
			isRed = true;
		}
	}

	RedBlackTree1() {
		super();
	}

	public boolean add(T x) {
		Entry<T> newEntry = new Entry<T>(x, null, null);
		boolean isAdded = super.add(newEntry);
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

	public T remove(T x) {
		T deletedElement = super.remove(x);
		Entry<T> bypassEntry = null;
		if (deletedElement != null && stack != null && stack.size() > 1) {
			Entry<T> bypassParent = (Entry<T>) stack.peek();
			if (bypassParent.element.compareTo(deletedElement) < 0)
				bypassEntry = (Entry<T>) bypassParent.right;
			else
				bypassEntry = (Entry<T>) bypassParent.left;
			if (deletedElement != null || !bypassEntry.isRed)
				fix(bypassEntry);
		}
		return deletedElement;
	}

	public void fix(Entry<T> t) {
		Entry<T> parent = null, siblingOfT = null, newRoot = null;
		while (t != root) {
			parent = (Entry<T>) stack.pop();
			if (parent != null && parent.left == t)
				siblingOfT = (Entry<T>) parent.right;
			else
				siblingOfT = (Entry<T>) parent.left;
			Entry<T> siblingLeftChild = (Entry<T>) siblingOfT.left;
			Entry<T> siblingRightChild = (Entry<T>) siblingOfT.right;
			
			//Case 5
			if (siblingOfT != null && siblingOfT.isRed) {
				if (parent.left != null && parent.left == t)
					newRoot = rotateLeft(parent);
				else if (parent.right != null && parent.right == t)
					newRoot = rotateRight(parent);
				exchangeColors(parent, siblingOfT);
			}

			if (t.isRed) {
				t.isRed = false;
				return;
			} else if (siblingOfT != null && !siblingOfT.isRed && siblingLeftChild != null && siblingRightChild != null
					&& !siblingLeftChild.isRed && !siblingRightChild.isRed) {
				siblingOfT.isRed = true;
				t = parent;
			} else if (parent != null && siblingOfT != null) {
				if (parent.right != null && parent.right == siblingOfT && !siblingOfT.isRed && siblingRightChild != null
						&& siblingRightChild.isRed) {
					newRoot = rotateLeft(parent);
					exchangeColors(parent, siblingOfT);
					siblingRightChild.isRed = false;
					return;
				} else if (parent.left != null && parent.left == siblingOfT && !siblingOfT.isRed
						&& siblingLeftChild != null && siblingLeftChild.isRed) {
					newRoot = rotateRight(parent);
					exchangeColors(parent, siblingOfT);
					siblingLeftChild.isRed = false;
					return;
				} else if (parent.right != null && parent.right == siblingOfT && !siblingOfT.isRed
						&& siblingLeftChild != null && siblingLeftChild.isRed) {
					parent.right = rotateRight(siblingOfT);
					exchangeColors(siblingLeftChild, siblingOfT);
					newRoot = rotateLeft(parent);
					exchangeColors(parent, siblingOfT);
					siblingRightChild.isRed = false;
					return;
				} else if (parent.left != null && parent.left == siblingOfT && !siblingOfT.isRed
						&& siblingRightChild != null && siblingRightChild.isRed) {
					parent.left = rotateLeft(siblingOfT);
					exchangeColors(siblingRightChild, siblingOfT);
					newRoot = rotateRight(parent);
					exchangeColors(parent, siblingOfT);
					siblingLeftChild.isRed = false;
					return;
				}
			}
		}
	}

	public void exchangeColors(Entry<T> parent, Entry<T> siblingOfT) {
		boolean tmp = parent.isRed;
		parent.isRed = siblingOfT.isRed;
		siblingOfT.isRed = tmp;
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

	/*
	 * public void swap(Entry<T> parent, Entry<T> t) { T temp = parent.element;
	 * parent.element = t.element; t.element = temp; }
	 * 
	 * public void case2A(Entry<T> parent, Entry<T> grandParent, T prevChild) {
	 * 
	 * }
	 * 
	 * public void case2B(Entry<T> parent, Entry<T> grandParent, T prevChild) {
	 * 
	 * }
	 */

	public Entry<T> rotateRight(Entry<T> t) {
		Entry<T> tLeft = (Entry<T>) t.left;
		t.left = tLeft.right;
		tLeft.right = t;
		return tLeft;
	}

	public Entry<T> rotateLeft(Entry<T> t) {
		Entry<T> tRight = (Entry<T>) t.right;
		t.right = tRight.left;
		tRight.left = t;
		return tRight;
	}

	public static void main(String[] args) {
		RedBlackTree1<Integer> t = new RedBlackTree1<Integer>();

		Scanner in = new Scanner(System.in);
		while (in.hasNext()) {
			int x = in.nextInt();
			if (x > 0) {
				System.out.print("Add " + x + " : ");
				t.add(x);
				t.printTree();
			} else if (x < 0) {
				System.out.print("Remove " + x + " : ");
				t.remove(-x);
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
