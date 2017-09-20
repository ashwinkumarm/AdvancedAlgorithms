package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp4_q1_DLLToBST;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class DoublyLinkedList<T> implements Iterable<T> {

	public static class Entry<T> {
		public T element;
		public Entry<T> next;
		public Entry<T> prev;

		Entry(T x, Entry<T> nxt, Entry<T> prv) {
			element = x;
			next = nxt;
			prev = prv;
		}
	}

	// Dummy header is used. tail stores reference of tail element of list
	public Entry<T> head;
	public Entry<T> tail;
	int size;

	public DoublyLinkedList() {
		head = new Entry<>(null, null, null);
		tail = head;
		size = 0;
	}

	public Iterator<T> iterator() {
		return new DLLIterator<>(this);
	}

	private class DLLIterator<E> implements Iterator<E> {
		DoublyLinkedList<E> list;
		Entry<E> cursor;
		boolean ready; // is item ready to be removed?

		DLLIterator(DoublyLinkedList<E> list) {
			this.list = list;
			cursor = list.head;

			ready = false;
		}

		public boolean hasNext() {
			return cursor.next != null;
		}

		public E next() {

			cursor = cursor.next;
			ready = true;
			return cursor.element;
		}

		// Removes the current element (retrieved by the most recent next())
		// Remove can be called only if next has been called and the element has
		// not been removed
		public void remove() {
			if (!ready) {
				throw new NoSuchElementException();
			}
			cursor.prev.next = cursor.next;
			
			// Handle case when tail of a list is deleted
			if (cursor == list.tail) {
				list.tail = cursor.prev;
			} else {
				cursor.next.prev = cursor.prev;
			}
			cursor = cursor.prev;
			ready = false; // Calling remove again without calling next will
							// result in exception thrown
			size--;
		}
	}

	// Add new elements to the end of the list
	public void add(T x) {
		tail.next = new Entry<T>(x, null, tail);
		tail = tail.next;
		size++;
	}

	public void printList() {
		/*
		 * Code without using implicit iterator in for each loop:
		 * 
		 * Entry<T> x = head.next; while(x != null) { System.out.print(x.element
		 * + " "); x = x.next; }
		 */

		System.out.print(this.size + ": ");
		for (T item : this) {
			System.out.print(item + " ");
		}

		System.out.println();
	}
	
	void sortedListToBST() {
		head.next = sortedListToBST(size); //Assign root of the BST as next the dummy header
	}
	
	Entry<T> sortedListToBST(int n)
    {
        if (n <= 0)        //Base condition
            return null;
 
        /* Recursively construct the left subtree */
        Entry<T> left = sortedListToBST(n / 2);
        Entry<T> root = head.next; // the middle element because the root in each sub tree construction
        root.prev = left;
        head.next = head.next.next; //update the header to the next element in the list
        
        /*Recursively construct the right subtree by passing the remaining number of elements left
         * constructing the left subtree and the root
         */
        root.next = sortedListToBST(n - n / 2 - 1);
 
        return root;
    }
	
	public static void main(String[] args) throws NoSuchElementException {
		int n = 4;
		if (args.length > 0) {
			n = Integer.parseInt(args[0]);
		}

		DoublyLinkedList<Integer> lst = new DoublyLinkedList<>();
		for (int i = 1; i <= n; i++) {
			lst.add(new Integer(i));
		}
		lst.printList();

		Iterator<Integer> it = lst.iterator();
		Scanner in = new Scanner(System.in);
		whileloop: while (in.hasNext()) {
			int com = in.nextInt();
			switch (com) {
			case 1: // Move to next element and print it
				if (it.hasNext()) {
					System.out.println(it.next());
				} else {
					break whileloop;
				}
				break;
			case 2: // Remove element
				it.remove();
				lst.printList();
				break;
			default: // Exit loop
				break whileloop;
			}
		}
		lst.printList();
		lst.sortedListToBST();
		lst.printList();
	}

}
