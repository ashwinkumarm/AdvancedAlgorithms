package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp2_q2_MergeSortSLL;

import java.util.Scanner;

import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.SinglyLinkedList;

/**
 * Class SortableList
 *
 * <P>
 * This class implements Merge sort algorithm that works on linked lists.
 *
 * @author Ashwin, Arun, Deepak, Haritha
 *
 */
public class SortableList<T extends Comparable<? super T>> extends SinglyLinkedList<T> {

	/**
	 * Merges both the unprocessed lists.
	 *
	 * @param other
	 */
	void merge(SortableList<T> other) {
		Entry<T> tailx = this.head;
		Entry<T> tc = this.head.next;
		Entry<T> oc = other.head.next;

		while (tc != null && oc != null) {
			if (tc.element.compareTo(oc.element) < 0) {
				tailx.next = tc;
				tailx = tc;
				tc = tc.next;
			} else {
				tailx.next = oc;
				tailx = oc;
				oc = oc.next;
			}
		}

		if (tc == null) {
			tailx.next = oc;
			tail = other.tail;
		} else
			tailx.next = tc;
	}

	/**
	 *Recursive method for merge sort.
	 */
	void mergeSort() {
		if (this.head.next.next != null) {
			Entry<T> mid = getMiddleElement();
			SortableList<T> other = new SortableList<>();
			other.head.next = mid.next;
			mid.next = null;

			mergeSort();
			mergeSort(other);
			merge(other);
		}
	}

	/**
	 * Function to get the middle node of the linked list.
	 *
	 * @return
	 */
	private Entry<T> getMiddleElement() {
		Entry<T> slow = this.head.next;
		Entry<T> fast = slow;
		while (fast.next != null && fast.next.next != null) {
			fast = fast.next.next;
			slow = slow.next;
		}
		return slow;
	}

	/**
	 * Helper method to call recursion.
	 *
	 * @param list
	 */
	public static <T extends Comparable<? super T>> void mergeSort(SortableList<T> list) {
		list.mergeSort();
	}

	/**
	 * Main method used for testing.
	 *
	 * @param args
	 */
	public static void main(String[] args) {

		int n = 20, value = 0;

		SortableList<Integer> sl = new SortableList<>();
		Scanner in = new Scanner(System.in);

		for (int i = 0; i < n; i++) {
			value = in.nextInt();
			sl.add(new Integer(value));
		}

		mergeSort(sl);

		System.out.println("After merge sort :");

		sl.printList();
		in.close();

	}
}
