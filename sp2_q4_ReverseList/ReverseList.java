package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp2_q4_ReverseList;

import java.util.Stack;

import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.SinglyLinkedList;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.SinglyLinkedList.Entry;

/**
 * Class ReverseList
 * 
 * <P>
 * This class reverses the given Linkedlist using recursion and non-recursion
 * methods Also, it prints the LinkedList in reverse order using recursion and
 * non-recursion methods
 * 
 * @author Ashwin, Arun, Deepak, Haritha
 *
 */
public class ReverseList {

	/**
	 * reverseRecursive method performs the reverse operation of a Linkedlist
	 * recursively
	 * 
	 * @param ll
	 */
	public static <T> void reverseRecursive(SinglyLinkedList<T> ll) {

		ll.tail = ll.head.next;
		ll.head.next = reverse(ll.tail, null);
	}

	/**
	 * reverse method acts as a helper method for reversing a Linkedlist recursively
	 * 
	 * @param node
	 * @param prev
	 * @return
	 */
	public static <T> Entry<T> reverse(Entry<T> node, Entry<T> prev) {

		if (node == null)
			return prev;
		Entry<T> n = node.next;
		node.next = prev;
		return reverse(n, node);
	}

	/**
	 * reverseIterative method performs the reverse operation of a Linkedlist
	 * iteratively
	 * 
	 * @param ll
	 */
	public static <T> void reverseIterative(SinglyLinkedList<T> ll) {

		Entry<T> prev = null, node = ll.head.next, curr = null;
		ll.tail = node;
		while (node != null) {
			curr = node;
			node = node.next;
			curr.next = prev;
			prev = curr;
		}
		ll.head.next = prev;
	}

	/**
	 * printReverseRecursive method prints the Linkedlist in reverse order
	 * recursively
	 * 
	 * @param ll
	 */
	public static <T> void printReverseRecursive(SinglyLinkedList<T> ll) {

		printReverse(ll.head.next);
	}

	/**
	 * printReverse method acts as a helper method for printing the Linkedlist in
	 * reverse order recursively
	 * 
	 * @param node
	 */
	private static <T> void printReverse(Entry<T> node) {

		if (node != null) {
			printReverse(node.next);
			System.out.print(node.element + "  ");
		}
	}

	/**
	 * printReverseIterative method prints the Linkedlist in reverse order
	 * iteratively
	 * 
	 * @param ll
	 */
	public static <T> void printReverseIterative(SinglyLinkedList<T> ll) {

		Stack<T> st = new Stack<T>();
		for (T item : ll) {
			st.push(item);
		}
		while (!st.isEmpty()) {
			System.out.print(st.pop() + "  ");
		}
	}

	/**
	 * Main method
	 * 
	 * @param args
	 */
	public static void main(String args[]) {

		SinglyLinkedList<Integer> ll = getNewList();
		System.out.print("Reverse List Iterative: ");
		reverseIterative(ll);
		printList(ll.head.next);

		ll = getNewList();
		System.out.print("\nReverse List Recursive: ");
		reverseRecursive(ll);
		printList(ll.head.next);

		ll = getNewList();
		System.out.print("\nPrint Reverse Recursive: ");
		printReverseRecursive(ll);

		ll = getNewList();
		System.out.print("\nPrint Reverse Iterative: ");
		printReverseIterative(ll);
	}

	/**
	 * Creates a Linkedlist by adding the elements
	 * 
	 * @return Linkedlist
	 */
	private static SinglyLinkedList<Integer> getNewList() {

		SinglyLinkedList<Integer> ll = new SinglyLinkedList<Integer>();
		ll.add(10);
		ll.add(15);
		ll.add(20);
		ll.add(1);
		ll.add(2);

		return ll;
	}

	/**
	 * Prints the linkedlist
	 * 
	 * @param node
	 */
	private static void printList(Entry<Integer> node) {

		while (node != null) {
			System.out.print(node.element + "  ");
			node = node.next;
		}
	}
}
