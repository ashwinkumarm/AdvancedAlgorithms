package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp7_q3_RedBlackTree;

import java.util.Scanner;

import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp7_q1_BST.BinarySearchTree;

public class RedBlackTreeNew<T extends Comparable<? super T>> extends BinarySearchTree<T> {
	static class Entry<T> extends BinarySearchTree.Entry<T> {
		boolean isRed;

		Entry(T x, Entry<T> left, Entry<T> right) {
			super(x, left, right);
			isRed = true;
		}
	}

	RedBlackTreeNew() {
		super();
	}

	public boolean insert(T x) {
		if (root == null) {
			root = new Entry<T>(x, null, null);
			size = 0;
		} else {
			Entry<T> head = new Entry<T>(null, null, null);
			Entry<T> parent = null, grandParent = null, current, greatGp;
			int left = 0, last = 0;

			greatGp = head;
			current = (Entry<T>) root;
			greatGp.right = (Entry<T>) root;

			for (;;) {
				if (current == null) {
					if (left == 0) {
						parent.left = new Entry(x, null, null);
					} else {
						parent.right = new Entry(x, null, null);
					}
					current = new Entry(x, null, null);
				} else if (current.left != null && current.right != null && ((Entry<T>) current.left).isRed
						&& ((Entry<T>) current.right).isRed) {
					current.isRed = true;
					((Entry<T>) current.left).isRed = false;
					((Entry<T>) current.right).isRed = false;
				}

				if (parent != null && current != null && parent.isRed && current.isRed) {
					int right = greatGp.right == grandParent ? 1 : 0;

					if (last == 0 && parent.left != null && current.element.compareTo(parent.left.element) == 0) {
						if (right == 1) {
							greatGp.right = rightRotate(grandParent);
						} else {
							greatGp.left = rightRotate(grandParent);
						}
						updateColor(parent, grandParent);
					} else if (last == 1 && parent.right != null &&  current.element.compareTo(parent.right.element) == 0) {
						if (right == 1) {
							greatGp.right = leftRotate(grandParent);
						} else {
							greatGp.left = leftRotate(grandParent);
						}
						updateColor(parent, grandParent);
					} else if (last == 0 && parent.left != null && current.element.compareTo(parent.left.element) > 0) {
						if (right == 1) {
							greatGp.right =  rightLeftRotate(grandParent);
						} else {
							greatGp.left = rightLeftRotate(grandParent);
						}
						updateColor(current, grandParent);
					} else {
						if (right == 1) {
							greatGp.right = leftRightRotate(grandParent);
						} else {
							greatGp.left = leftRightRotate(grandParent);
						}
						updateColor(current, grandParent);
					}
				}

				if (current.element.compareTo(x) == 0) {
					break;
				}

				last = left;
				left = current.element.compareTo(x) < 0 ? 1 : 0;

				if (grandParent != null) {
					greatGp = grandParent;
				}

				grandParent = parent;
				parent = current;
				if (left == 0) {
					current = (Entry<T>) current.left;
				} else {
					current = (Entry<T>) current.right;
				}
			}
			root = head.right;
		}
		size++;
		((Entry<T>) root).isRed = false;
		return true;
	}

	public void updateColor(Entry<T> newRoot, Entry<T> grandParent) {
		newRoot.isRed = false;
		grandParent.isRed = true;
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

	public Entry<T> rightLeftRotate(Entry<T> node) {
		node.right = rightRotate((Entry<T>) node.right);
		return leftRotate(node);
	}

	public Entry<T> leftRightRotate(Entry<T> node) {
		node.left = leftRotate((Entry<T>) node.left);
		return rightRotate(node);
	}

	public static void main(String[] args) {

		RedBlackTreeNew<Integer> t = new RedBlackTreeNew<Integer>();
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
