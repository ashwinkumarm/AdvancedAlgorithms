package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp2_q3_Multizip;

import java.util.ArrayList;

/**
 * Class SinglyLinkedList
 * 
 * <P>
 * This class has a method named Multizip which  rearrange the elements of a singly linked list by chaining
 * together elements that are k apart.
 * 
 * @author Ashwin, Arun, Deepak, Haritha
 *
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

	/*
	 * 
	 *Rearrange the elements of the list by linking the elements that are k elements apart. Implemented by rearranging pointers
	 *of existing elements without allocating any new elements.
	 */
	public void multizip(int k) {
		if (size < k+1) { // Too few elements. No change.
			return;
		}
        
        List<Entry<T>> tail = new ArrayList<Entry<T>>(); // tail is the tail of the chain of elements that are k apart
        List<Entry<T>> heads = new ArrayList<Entry<T>>(); // head is the head of the chain of elements that are k apart
        
        tail.add(head.next);
        int i = 1;
        
        // Intialize all the head and tail to firt k elements 
        // Except that the head elements are initialized excluding the head of the list
        while (i < k){
        		tail.add(tail.get(i-1).next);
        		heads.add(tail.get(i));
        		i++;
        }
        
        // c is current element to be processed.
		Entry<T> c = tail.get(k-1).next;
		i = 0;
		while (c != null) {
				tail.get(i%k).next = c;
				tail.set(i%k, c);
				c = c.next;
				i++;
				}
		
		i = 0;
		// After processing link all the chain of k elements together
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
