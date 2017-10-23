package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp7_q2_AVLTree;

import java.util.Scanner;

import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp7_q1_BST.BinarySearchTree;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.BinaryTree;

/**
 * Implementation of AVL tree on top of BST
 *
 * @author Ashwin, Arun, Deepak, Haritha
 *
 */
public class AVLTree<T extends Comparable<? super T>> extends BinarySearchTree<T> {

	/**
	 * This class implements the Entry of AVL Tree
	 *
	 * @param <T>
	 */
	public static class Entry<T> extends BinarySearchTree.Entry<T> {
		int height;

		/**
		 * Constructor to initialize each entry of AVL Tree
		 * 
		 * @param x
		 * @param left
		 * @param right
		 */
		public Entry(T x, Entry<T> left, Entry<T> right) {
			super(x, left, right);
			height = 0;
		}
	}

	/**
	 * Constructor to initialize the AVL tree
	 */
	public AVLTree() {
		super();
	}

	/**
	 * Adds new entry to the AVL Tree given an element and updates the height for
	 * the elements above the newly added element up to the root
	 * 
	 * @param x
	 * @return
	 */
	public boolean add(T x) {
		Entry<T> newEntry = new Entry<T>(x, null, null);
		boolean isAdded = add(newEntry);
		if (isAdded && stack != null)
			root = balance();
		return isAdded;
	}

	/**
	 * Removes an entry from AVL Tree and and updates the height for the elements
	 * above the deleted element up to the root
	 * 
	 * @param x
	 * @return
	 */
	@Override
	public T remove(T x) {
		T deletedElement = super.remove(x);
		if (deletedElement != null && stack != null)
			root = balance();
		return deletedElement;
	}

	/**
	 * Performs the rotation of the tree based on the height difference(i.e
	 * height>1)
	 * 
	 * @return
	 */
	private Entry<T> balance() {
		Entry<T> entry = null;
		entry = (Entry<T>) stack.pop();
		int balance;
		Entry<T> parent;
		while (true) {
			parent = (Entry<T>) stack.pop();
			balance = getBalance(entry);
			if (balance > 1) {
				if (getHeight(entry.left.left) >= getHeight(entry.left.right))
					entry = rightRotate(entry);
				else
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

	/**
	 * Assigns the link to the previous element in the stack for the newly changed
	 * child
	 * 
	 * @param parent
	 * @param entry
	 */
	private void setParentChild(Entry<T> parent, Entry<T> entry) {
		if (parent != null)
			if (parent.element.compareTo(entry.element) > 0) {
				parent.left = entry;
			} else {
				parent.right = entry;
			}
	}

	/**
	 * Performs the right rotation and updates the height
	 * 
	 * @param node
	 * @return
	 */
	public Entry<T> rightRotate(Entry<T> node) {
		Entry<T> otherNode = (Entry<T>) node.left;
		node.left = otherNode.right;
		otherNode.right = node;

		node.height = Math.max(getHeight(node.left), getHeight(node.left)) + 1;
		otherNode.height = Math.max(getHeight(otherNode.left), node.height) + 1;
		return otherNode;
	}

	/**
	 * Performs the left rotation and updates the height
	 * 
	 * @param node
	 * @return
	 */
	public Entry<T> leftRotate(Entry<T> node) {
		Entry<T> otherNode = (Entry<T>) node.right;
		node.right = otherNode.left;
		otherNode.left = node;

		node.height = Math.max(getHeight(node.left), getHeight(node.left)) + 1;
		otherNode.height = Math.max(getHeight(otherNode.left), node.height) + 1;
		return otherNode;
	}

	/**
	 * Performs the double rotation i.e. right rotation on parent and left rotation
	 * on grandparent
	 * 
	 * @param node
	 * @return
	 */
	public Entry<T> RightLeftRotate(Entry<T> node) {
		node.right = rightRotate((Entry<T>) node.right);
		return leftRotate(node);
	}

	/**
	 * Performs the double rotation i.e. left rotation on parent and right rotation
	 * on grandparent
	 * 
	 * @param node
	 * @return
	 */
	public Entry<T> LeftRightRotate(Entry<T> node) {
		node.left = leftRotate((Entry<T>) node.left);
		return rightRotate(node);
	}

	/**
	 * Gets the height of the given entry
	 * 
	 * @param entry
	 * @return
	 */
	public int getHeight(BinaryTree.Entry<T> entry) {
		Entry<T> node = (Entry<T>) entry;
		return node == null ? -1 : node.height;
	}

	/**
	 * Gives the height difference between left subtree and right subtree
	 * 
	 * @param entry
	 * @return
	 */
	public int getBalance(BinaryTree.Entry<T> entry) {
		if (entry == null)
			return 0;
		return getHeight(entry.left) - getHeight(entry.right);
	}

	/**
	 * Main method for testing
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		AVLTree<Integer> t = new AVLTree<Integer>();
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
