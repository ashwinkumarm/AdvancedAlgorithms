package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.lp5;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

// Skeleton for skip list implementation.

public class SkipList<T extends Comparable<? super T>> {

	public static class SkipListEntry<T> {
		public T element;
		public SkipListEntry<T>[] next;

		public SkipListEntry(T element, int level) {
			this.element = element;
			next = new SkipListEntry[level + 1];
		}

		/*
		 * public T getElement() { return element; }
		 * 
		 * public int level() { return next.size() - 1; }
		 */
	}

	private int size;
	private int maxLevel;
	private SkipListEntry<T> head, tail;

	// Constructor
	public SkipList() {
		size = 0;
		maxLevel = 0;
		head = new SkipListEntry<T>(null, 32);
		tail = new SkipListEntry<T>(null, 32);
		for (int i = 0; i <= 32; i++) {
			head.next[i] = tail;
			tail.next[i] = null;
		}
	}

	public SkipListEntry<T>[] find(T x) {
		SkipListEntry<T>[] prev = new SkipListEntry[maxLevel + 1];
		Arrays.fill(prev, null);
		SkipListEntry<T> p = head;
		for (int i = maxLevel; i >= 0; i--) {
			while (p.next[i].element != null && p.next[i].element.compareTo(x) < 0)
				p = p.next[i];
			prev[i] = p;
		}
		return prev;
	}

	// Add x to list. If x already exists, replace it. Returns true if new node is
	// added to list
	public boolean add(T x) {
		SkipListEntry<T>[] prev = find(x);
		if (prev[0].next[0].element != null && prev[0].next[0].element.compareTo(x) == 0) {
			prev[0].next[0].element = x;
			return false;
		} else {
			int lev = chooseLevel();
			SkipListEntry<T> n = new SkipListEntry<T>(x, lev);
			for (int i = 0; i <= lev; i++) {
				n.next[i] = prev[i].next[i];
				prev[i].next[i] = n;
			}
			size++;
		}
		return true;
	}

	public int chooseLevel() {
		Random rand = new Random();
		int mask = (1 << maxLevel) - 1;
		int lev = Integer.numberOfTrailingZeros(rand.nextInt() & mask);
		if (lev > maxLevel)
			return maxLevel++;
		else
			return lev;
	}

	// Find smallest element that is greater or equal to x
	public T ceiling(T x) {
		SkipListEntry<T>[] prev = find(x);
		if (prev[0].next[0].element.compareTo(x) == 0)
			return x;
		else
			return prev[0].next[0].element;
	}

	// Does list contain x?
	public boolean contains(T x) {
		SkipListEntry<T>[] prev = find(x);
		if (prev[0].next[0].element != null && prev[0].next[0].element.compareTo(x) == 0)
			return true;
		return false;
	}

	// Return first element of list
	public T first() {
		return head.next[0].element;
	}

	// Find largest element that is less than or equal to x
	public T floor(T x) {
		SkipListEntry<T>[] prev = find(x);
		if (prev[0].next[0].element.compareTo(x) == 0)
			return x;
		else
			return prev[0].element;
	}

	// Return element at index n of list. First element is at index 0.
	public T get(int n) {
		return null;
	}

	// Is the list empty?
	public boolean isEmpty() {
		return size == 0 ? true : false;
	}

	// Iterate through the elements of list in sorted order
	public Iterator<T> iterator() {
		return new SkipListIterator<>(head);
	}

	private class SkipListIterator<E> implements Iterator<E> {
		List<SkipListEntry<E>> queue = new LinkedList<SkipListEntry<E>>();

		private SkipListIterator(SkipListEntry<E> head) {
			queue.add(head.next[0]);
		}

		public boolean hasNext() {
			return !queue.isEmpty();
		}

		public E next() {
			SkipListEntry<E> entry = queue.remove(0);
			if (entry.next[0] != null)
				queue.add(entry.next[0]);
			return entry.element;
		}
	}

	// Return last element of list
	public T last() {
		SkipListEntry<T> p = head;
		for (int i = maxLevel; i >= 0; i--) {
			while (p.next[i].element != null)
				p = p.next[i];
		}
		return p.element;
	}

	// Reorganize the elements of the list into a perfect skip list
	public void rebuild() {
		// TODO: Atlast

	}

	// Remove x from list. Removed element is returned. Return null if x not in list
	public T remove(T x) {
		SkipListEntry<T>[] prev = find(x);
		SkipListEntry<T> n = prev[0].next[0];
		if (n.element.compareTo(x) != 0)
			return null;
		else {
			for (int i = 0; i <= maxLevel; i++) {
				if (prev[i].next[i].equals(n))
					prev[i].next[i] = n.next[i];
				else
					break;
			}
			size--;
		}
		return n.element;
	}

	// Return the number of elements in the list
	public int size() {
		return size;
	}

	public static void main(String args[]) {
		SkipList<Integer> skl = new SkipList<Integer>();
		System.out.println(skl.first());
		System.out.println(skl.last());
		skl.add(10);
		skl.add(20);
		skl.add(30);
		skl.add(8);
		skl.add(11);
		skl.add(21);
		skl.add(31);
		skl.add(9);
		skl.add(12);
		skl.add(22);
		skl.add(32);
		skl.add(1);
		skl.add(13);
		skl.add(23);
		skl.add(33);
		skl.add(2);
		skl.add(14);
		skl.add(24);
		skl.add(34);
		skl.add(3);
		skl.add(15);
		skl.add(25);
		skl.add(35);
		skl.add(4);
		skl.add(16);
		skl.add(26);
		skl.add(36);
		skl.add(5);
		skl.add(17);
		skl.add(27);
		skl.add(37);
		skl.add(6);
		skl.add(18);
		skl.add(28);
		skl.add(38);

		skl.remove(8);
		System.out.println(skl.first());
		System.out.println(skl.last());
		System.out.println(skl.floor(28));
		System.out.println(skl.contains(30));
		System.out.println(skl.contains(8));
		System.out.println(skl.ceiling(29));
	}
}