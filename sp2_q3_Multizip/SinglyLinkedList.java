package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp2_q3_Multizip;

import java.util.ArrayList;

/** @author rbk
 *  Singly linked list: for instructional purposes only
 *  Ver 1.0: 2017/08/08
 *  Ver 1.1: 2017/08/30: Fixed error: If last element of list is removed,
 *  "tail" is no longer a valid value.  Subsequently, if items are added
 *  to the list, code would do the wrong thing.
 */

import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.NoSuchElementException;

public class SinglyLinkedList<T> implements Iterable<T> {

	/** Class Entry holds a single node of the list */
	static class Entry<T> {
		T element;
		Entry<T> next;

		Entry(T x, Entry<T> nxt) {
			element = x;
			next = nxt;
		}
	}

	// Dummy header is used. tail stores reference of tail element of list
	Entry<T> head, tail;
	int size;

	public SinglyLinkedList() {
		head = new Entry<>(null, null);
		tail = head;
		size = 0;
	}

	public Iterator<T> iterator() {
		return new SLLIterator<>(this);
	}

	private class SLLIterator<E> implements Iterator<E> {
		SinglyLinkedList<E> list;
		Entry<E> cursor, prev;
		boolean ready; // is item ready to be removed?

		SLLIterator(SinglyLinkedList<E> list) {
			this.list = list;
			cursor = list.head;
			prev = null;
			ready = false;
		}

		public boolean hasNext() {
			return cursor.next != null;
		}

		public E next() {
			prev = cursor;
			cursor = cursor.next;
			ready = true;
			return cursor.element;
		}

		// Removes the current element (retrieved by the most recent next())
		// Remove can be called only if next has been called and the element has not
		// been removed
		public void remove() {
			if (!ready) {
				throw new NoSuchElementException();
			}
			prev.next = cursor.next;
			// Handle case when tail of a list is deleted
			if (cursor == list.tail) {
				list.tail = prev;
			}
			cursor = prev;
			ready = false; // Calling remove again without calling next will result in exception thrown
			size--;
		}
	}

	// Add new elements to the end of the list
	public void add(T x) {
		tail.next = new Entry<>(x, null);
		tail = tail.next;
		size++;
	}

	public void printList() {
		/*
		 * Code without using implicit iterator in for each loop:
		 * 
		 * Entry<T> x = head.next; while(x != null) { System.out.print(x.element + " ");
		 * x = x.next; }
		 */

		System.out.print(this.size + ": ");
		for (T item : this) {
			System.out.print(item + " ");
		}

		System.out.println();
	}


	public void multizip(int k) {
		if (size < k+1) { // Too few elements. No change.
			return;
		}
        
        List<Entry<T>> tail = new ArrayList<Entry<T>>(); 
        List<Entry<T>> heads = new ArrayList<Entry<T>>();
        
        tail.add(head.next);
        int i = 1;
        while (i < k){
        		tail.add(tail.get(i-1).next);
        		heads.add(tail.get(i));
        		i++;
        }
        
		Entry<T> c = tail.get(k-1).next;
		i = 0;
		while (c != null) {
				tail.get(i%k).next = c;
				tail.set(i%k, c);
				c = c.next;
				i++;
				}
		
		i = 0;
		while(i < k-1){
			tail.get(i).next = heads.get(i);
			i++;
		}
		tail.get(i).next = null; 
	}
	
	public static void main(String[] args) throws NoSuchElementException {
		int n = 10;
		if (args.length > 0) {
			n = Integer.parseInt(args[0]);
		}

		SinglyLinkedList<Integer> lst = new SinglyLinkedList<>();
		for (int i = 1; i <= n; i++) {
			lst.add(new Integer(i));
		}
		System.out.println("Input List");
		lst.printList();
		lst.multizip(3);
		System.out.println("Output List after multizip");
		lst.printList();
	}
}
