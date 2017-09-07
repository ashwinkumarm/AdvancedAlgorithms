package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp2_q4_ReverseList;

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
		Entry<T> prev = null, node = ll.head.next, nextNode = null;
		ll.tail = node;
		while (node != null) {
			nextNode = node.next;
			node.next = prev;
			prev = node;
			node = nextNode;
		}
		ll.head.next = prev;
	}

	public static <T> void printReverseRecursive(SinglyLinkedList<T> ll) {
		printReverse(ll.head.next);
	}

	private static <T> void printReverse(Entry<T> start) {
		if (start != null) {
			printReverse(start.next);
			System.out.print(start.element + "  ");
		}
	}

	public static void main(String args[]) {
		SinglyLinkedList<Integer> ll = new SinglyLinkedList<Integer>();
		ll.add(10);
		ll.add(15);
		ll.add(20);
		ll.add(1);
		ll.add(2);

		System.out.print("Reverse List Iterative: ");
		reverseIterative(ll);
		printList(ll.head.next);

		ll = new SinglyLinkedList<Integer>();
		ll.add(10);
		ll.add(15);
		ll.add(20);
		ll.add(1);
		ll.add(2);

		System.out.print("\nReverse List Recursive: ");
		reverseRecursive(ll);
		printList(ll.head.next);

		ll = new SinglyLinkedList<Integer>();
		ll.add(10);
		ll.add(15);
		ll.add(20);
		ll.add(1);
		ll.add(2);

		System.out.print("\nPrint Reverse Recursive: ");
		printReverseRecursive(ll);
	}

	private static void printList(Entry<Integer> node) {
		while (node != null) {
			System.out.print(node.element + "  ");
			node = node.next;
		}
	}
}
