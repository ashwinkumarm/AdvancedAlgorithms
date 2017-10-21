package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp7_q1_BST;

import java.util.Iterator;
import java.util.Scanner;
import java.util.Stack;

import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.BinaryTree;

public class BinarySearchTree<T extends Comparable<? super T>> extends BinaryTree<T> {

	protected Stack<Entry<T>> stack;

	public Iterator<T> iterator() {
		return new BSTIterator<>(root);
	}

	private class BSTIterator<E> implements Iterator<E> {
		Stack<Entry<E>> stack;
		Entry<E> entry;

		BSTIterator(Entry<E> root) {
			stack = new Stack<Entry<E>>();
			entry = root;
			while (entry != null) {
				stack.push(entry);
				entry = entry.left;
			}
		}

		public boolean hasNext() {
			return !stack.isEmpty();
		}

		public E next() {
			Entry<E> entry = stack.pop();
			E value = entry.element;
			if (entry.right != null) {
				entry = entry.right;
				while (entry != null) {
					stack.push(entry);
					entry = entry.left;
				}
			}
			return value;
		}
	}

	public boolean add(T x) {
		return add(new Entry<T>(x, null, null));
	}

	public boolean add(Entry<T> newEntry) {
		if (root == null) {
			root = newEntry;
			size = 1;
			return true;
		}

		Entry<T> entry = find(newEntry.element);
		if (newEntry.element.compareTo(entry.element) == 0) {
			entry.element = newEntry.element;
			return false;
		} else if (newEntry.element.compareTo(entry.element) < 0) {
			entry.left = newEntry;
			stack.push(entry);
		} else {
			entry.right = newEntry;
			stack.push(entry);
		}
		size++;
		return true;
	}

	public T remove(T x) {
		if (root == null) {
			return null;
		}
		Entry<T> entry = find(x);
		if (entry.element.compareTo(x) != 0) {
			return null;
		}
		T result = entry.element;
		if (entry.left == null || entry.right == null) {
			byPass(entry);
		} else {
			stack.push(entry);
			Entry<T> minRight = find(entry.right, entry.element);
			entry.element = minRight.element;
			byPass(minRight);
		}
		size--;
		return result;
	}

	public Entry<T> find(T x) {
		stack = new Stack<Entry<T>>();
		stack.push(null);
		return find(root, x);
	}

	public Entry<T> find(Entry<T> entry, T x) {
		if (entry == null || entry.element == x) {
			return entry;
		}
		while (true) {
			if (x.compareTo(entry.element) < 0) {
				if (entry.left == null) {
					break;
				} else {
					stack.push(entry);
					entry = entry.left;
				}
			} else if (x.compareTo(entry.element) == 0) {
				break;
			} else {
				if (entry.right == null) {
					break;
				} else {
					stack.push(entry);
					entry = entry.right;
				}
			}

		}
		return entry;
	}

	/**
	 * Is there an element that is equal to x in the tree? Element in tree that
	 * is equal to x is returned, null otherwise.
	 */
	public T get(T x) {
		Entry<T> entry = find(x);
		return (entry != null && entry.element.compareTo(x) == 0) ? entry.element : null;
	}

	public boolean contains(T x) {
		Entry<T> entry = find(x);
		return entry != null && entry.element.compareTo(x) == 0;
	}

	public T min() {
		if (root == null)
			return null;
		Entry<T> entry = root;
		while (entry.left != null)
			entry = entry.left;
		return entry.element;
	}

	public T max() {
		if (root == null)
			return null;
		Entry<T> entry = root;
		while (entry.right != null)
			entry = entry.right;
		return entry.element;
	}

	public int height(Entry<T> entry) {
		if (entry == null)
			return -1;
		int lh = height(entry.left);
		int rh = height(entry.right);
		return 1 + Math.max(lh, rh);
	}

	public void byPass(Entry<T> entry) {

		Entry<T> parent = stack.peek();
		Entry<T> child = entry.left == null ? entry.right : entry.left;
		if (parent == null) {// t is root
			root = child;
		} else if (parent.left == entry) {
			parent.left = child;
		} else {
			parent.right = child;
		}
	}

	public Comparable[] toArray() {
		Comparable[] arr = new Comparable[size];
		toArray(root, arr, 0);
		return arr;
	}

	// Inorder traversal of tree
	int toArray(Entry<T> node, Comparable[] arr, int i) {
		if (node != null) {
			i = toArray(node.left, arr, i);
			arr[i++] = node.element;
			i = toArray(node.right, arr, i);
		}
		return i;
	}

	public void printTree() {
		System.out.print("[" + size + "]");
		inOrder(root);
		System.out.println();
	}

	public static void main(String[] args) {
		BinarySearchTree<Integer> t = new BinarySearchTree<>();
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
