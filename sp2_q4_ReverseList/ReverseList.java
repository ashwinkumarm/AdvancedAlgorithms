package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp2_q4_ReverseList;

import java.util.Stack;

import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp2_q4_ReverseList.SinglyLinkedList;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp2_q4_ReverseList.SinglyLinkedList.Entry;

public class ReverseList {
	public static <T> void reverseRecursive(SinglyLinkedList<T> ll) {
		ll.tail = ll.head.next;
		ll.head.next = reverse(ll.tail, null);
	}

	public static <T> Entry<T> reverse(Entry<T> node, Entry<T> prev) {
		if (node == null)
			return prev;
		Entry<T> n = node.next;
		node.next = prev;
		return reverse(n, node);
	}

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

	public static <T> void printReverseRecursive(SinglyLinkedList<T> ll) {
		printReverse(ll.head.next);
	}

	private static <T> void printReverse(Entry<T> node) {
		if (node != null) {
			printReverse(node.next);
			System.out.print(node.element + "  ");
		}
	}

	public static <T> void printReverseIterative(SinglyLinkedList<T> ll) {
		Stack<T> st = new Stack<T>();
		for(T item : ll) {
			st.push(item);
		}
		while(!st.isEmpty()) {
			System.out.print(st.pop() + "  ");
		}
	}

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

	private static SinglyLinkedList<Integer> getNewList() {
		SinglyLinkedList<Integer> ll = new SinglyLinkedList<Integer>();
		ll.add(10);
		ll.add(15);
		ll.add(20);
		ll.add(1);
		ll.add(2);

		return ll;
	}

	private static void printList(Entry<Integer> node) {
		while (node != null) {
			System.out.print(node.element + "  ");
			node = node.next;
		}
	}
}
