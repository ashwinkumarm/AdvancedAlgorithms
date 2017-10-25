package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp7_q4_SplayTree;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp7_q1_BST.BST;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Timer;

/**
 * Implementation of Splay tree on top of BST
 *
 * @author Ashwin, Arun, Deepak, Haritha
 *
 */
/**
 * @author deepaks
 *
 * @param <T>
 */
public class SplayTree<T extends Comparable<? super T>> extends BST<T> {
	/**
	 * Constructor to initialize the splay tree
	 */
	SplayTree() {
		super();
	}

	/**
	 * Adds new entry to the Splay Tree given an element and moves the added
	 * element to the root
	 *
	 * @param x
	 * @return
	 */
	public boolean add(T x) {
		Entry<T> newEntry = new Entry<T>(x, null, null);
		boolean isAdded = super.add(newEntry);
		Entry<T> parent = stack.peek();
		if (parent.left.element.compareTo(x) == 0)
			newEntry = (Entry<T>) parent.left;
		else
			newEntry = (Entry<T>) parent.left;

		if (isAdded && stack != null && stack.size() > 1)
			splay(newEntry);
		return isAdded;
	}

	/**
	 * Removes an entry from Splay Tree and moves the previous element in the
	 * tree to the root
	 *
	 * @param x
	 * @return
	 */
	public T remove(T x) {
		T removedElement = super.remove(x);
		if (stack != null && stack.size() > 1)
			splay(stack.pop());
		return removedElement;
	}

	/**
	 * Gets an element that is equal to x in the tree. Element in tree that is
	 * equal to x is returned, null otherwise.
	 */
	public T get(T x) {
		Entry<T> entry = find(x);
		if (entry != null) {
			splay(entry);
			if (entry.element.compareTo(x) == 0)
				return entry.element;
		}
		return null;
	}

	/**
	 * Check whether the given element is present in Splay Tree and moves the
	 * element to the root if present or the last accessed element to the root
	 *
	 * @param x
	 * @return
	 */
	public boolean contains(T x) {
		Entry<T> entry = find(x);
		if (entry != null) {
			splay(entry);
			if (entry.element.compareTo(x) == 0)
				return true;
		}
		return false;
	}

	/**
	 * Gets the minimum element of Splay Tree and moves that element to the root
	 *
	 * @return
	 */
	public T min() {
		if (root == null)
			return null;
		Entry<T> entry = (Entry<T>) root;
		while (entry.left != null)
			entry = (Entry<T>) entry.left;
		splay(entry);
		return entry.element;
	}

	/**
	 * Gets the maximum element of Splay Tree and moves that element to the root
	 *
	 * @return
	 */
	public T max() {
		if (root == null)
			return null;
		Entry<T> entry = (Entry<T>) root;
		while (entry.right != null)
			entry = (Entry<T>) entry.right;
		splay(entry);
		return entry.element;
	}

	/**
	 * Splay operation moves the given entry to the top of the tree(root)
	 *
	 * @param t
	 */
	public void splay(Entry<T> t) {
		Entry<T> parent = null, grandParent = null;
		while (t != root) {
			if (root != null && (root.left == t || root.right == t)) {
				if (root.left != null && root.left == t)
					root = rightRotate((Entry<T>) root);
				else if (root.right != null && root.right == t)
					root = leftRotate((Entry<T>) root);
			} else if (stack.size() > 2) {
				parent = stack.pop();
				grandParent = stack.pop();
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
				setParentChild(t);
			}
		}
	}

	/**
	 * Assigns the link to the previous element in the stack for the newly
	 * changed child
	 *
	 * @param prevChild
	 * @param t
	 */
	public void setParentChild(Entry<T> t) {
		Entry<T> greatGp = stack.peek();
		if (greatGp != null) {
			if (greatGp.left != null && greatGp.element.compareTo(t.element) > 0)
				greatGp.left = t;
			else
				greatGp.right = t;
		} else
			root = t;
	}

	/**
	 * Performs the right rotation
	 *
	 * @param root
	 * @return
	 */
	public Entry<T> rightRotate(Entry<T> root) {
		Entry<T> otherNode = (Entry<T>) root.left;
		root.left = otherNode.right;
		otherNode.right = root;

		return otherNode;
	}

	/**
	 * Performs the left rotation
	 *
	 * @param node
	 * @return
	 */
	public Entry<T> leftRotate(Entry<T> node) {
		Entry<T> otherNode = (Entry<T>) node.right;
		node.right = otherNode.left;
		otherNode.left = node;

		return otherNode;
	}

	/**
	 * Performs the double rotation i.e. right rotation on parent and left
	 * rotation on grandparent
	 *
	 * @param node
	 * @return
	 */
	public Entry<T> rightLeftRotate(Entry<T> node) {
		node.right = rightRotate((Entry<T>) node.right);
		return leftRotate(node);
	}

	/**
	 * Performs the double rotation i.e. left rotation on parent and right
	 * rotation on grandparent
	 *
	 * @param node
	 * @return
	 */
	public Entry<T> leftRightRotate(Entry<T> node) {
		node.left = leftRotate((Entry<T>) node.left);
		return rightRotate(node);
	}

	/**
	 * Main method for testing
	 *
	 * @param args
	 */
	public static void main(String args[]) {

		// SplayTree<Integer> t = new SplayTree<Integer>();
		BST<Integer> t = new BST<Integer>();
		Timer timer = new Timer();
		int a[] = getSkewedInput();
		for (int i = 0; i < a.length; i++) {
			int x = a[i];
			if (x > 0) {
				// System.out.print("Add " + x + " : ");
				t.add(x);
				// t.printTree();
			} else if (x < 0) {
				// System.out.print("Remove " + x + " : ");
				t.remove(-x);
				// t.printTree();
			} else {
				Comparable[] arr = t.toArray();
				System.out.print("Final: ");
				for (int j = 0; j < t.size; j++) {
					System.out.print(arr[j] + " ");
				}
				System.out.println();
				return;
			}
		}
		timer.end();
		System.out.println(timer);

		timer.start();
		SplayTree<Integer> t1 = new SplayTree<Integer>();
		for (int i = 0; i < a.length; i++) {
			int x = a[i];
			if (x > 0) {
				// System.out.print("Add " + x + " : ");
				t.add(x);
				// t.printTree();
			} else if (x < 0) {
				// System.out.print("Remove " + x + " : ");
				t.remove(-x);
				// t.printTree();
			} else {
				Comparable[] arr = t.toArray();
				System.out.print("Final: ");
				for (int j = 0; j < t.size; j++) {
					System.out.print(arr[j] + " ");
				}
				System.out.println();
				return;
			}
		}
		timer.end();
		System.out.println(timer);
	}

	private static int[] getSkewedInput() {
		int count1 = 0, count2 = 0, size = 1000000;
		Random random = new Random(System.currentTimeMillis());
		int arr[] = new int[size];
		int insert[] = new int[size];
		System.out.println("Enter the operation to be skewed: \n 1.add \n 2.remove");
		Scanner s = new Scanner(System.in);
		int a = s.nextInt();
		int dev = Math.max(2 - a, a - 1);
		Random r = new Random();
		int skew = r.nextInt(size);
		int dev1 = Math.max(size - a, a - 0);
		for (int i = 0; i < size - 1; i++) {
			int n = (int) nextSkewedBounded(1, 2, a, dev, random);
			switch (n) {
			case 1:
				int num = (int) nextSkewedBounded(1, size, skew, dev1, random);
				arr[i] = num;
				insert[count1] = num;
				count1++;
				break;
			case 2:
				num = (int) nextSkewedBounded(0, count1 - 1, skew, dev1, random);
				if (insert[num] == 0)
					continue;
				arr[i] = -insert[num];
				count2++;
				break;
			default:
				System.out.println("Wrong Input");
			}
		}
		arr[size - 1] = 0;
		s.close();
		System.out.println("Number of Add operations- " + count1);
		System.out.println("Number of Remove operations- " + count2);
		return arr;
	}

	static public double nextSkewedBounded(int min, int max, int a, double stddev, Random random) {
		return Math.max(min, Math.min(max, a + random.nextGaussian() * stddev));
	}

}