package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp7_q1_BST;

import java.util.Iterator;
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

	public Entry<T> add(T x) {

		Entry<T> newEntry = new Entry<T>(x, null, null);
		if (root == null) {
			root = newEntry;
			size = 1;
			return newEntry;
		}

		Entry<T> entry = find(x);
		if (x.compareTo(entry.element) == 0) {
			entry.element = x;
			return null;
		} else if (x.compareTo(entry.element) < 0) {
			entry.left = newEntry;
			stack.push(entry);
		} else {
			entry.right = newEntry;
			stack.push(entry);
		}
		size++;
		return newEntry;

		/*
		 * Entry<T> current = root; Entry<T> parent; while (true) { parent = current; if
		 * (value.compareTo(current.element) == 0) { return false; } else if
		 * (value.compareTo(current.element) < 0) { current = current.left; if (current
		 * == null) { parent.left = newEntry; size++; return true; } } else { current =
		 * current.right; if (current == null) { parent.right = newEntry; size++; return
		 * true; } } }
		 */
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

	public boolean contains(T x) {
		Entry<T> entry = find(x);
		return entry.element.compareTo(x) == 0 ? true : false;

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
}
