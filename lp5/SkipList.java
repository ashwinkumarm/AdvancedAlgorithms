package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.lp5;

import java.util.Iterator;
import java.util.Random;

/**
 * This class implements the SkipList with following functions: add, remove,
 * contains, ceiling, floor, first, last, get
 *
 * @author Ashwin, Arun, Deepak, Haritha
 * @param <T>
 *
 */
public class SkipList<T extends Comparable<? super T>> {

	public static class SkipListEntry<T> {
		public T element;
		public SkipListEntry<T>[] next;
		public int[] span;

		/**
		 * Constructor for initializing the SkipList Entry
		 * 
		 * @param element
		 * @param level
		 */
		public SkipListEntry(T element, int level) {
			this.element = element;
			next = new SkipListEntry[level];
			span = new int[level];
		}

		public int getLevel() {
			return next.length;
		}

	}

	private int size;
	private int maxLevel;
	private SkipListEntry<T> head, tail;
	private int idx;
	private int[] index;

	/**
	 * Constructor for initializing the SkipList
	 */
	public SkipList() {
		size = 0;
		maxLevel = 1;
		head = new SkipListEntry<T>(null, 32);
		tail = new SkipListEntry<T>(null, 32);
		for (int i = 0; i < 32; i++) {
			head.next[i] = tail;
			tail.next[i] = null;
			head.span[i] = 1;
		}
	}

	/**
	 * Finds the previous elements for the given element
	 * 
	 * @param x
	 * @return
	 */
	public SkipListEntry<T>[] find(T x) {
		SkipListEntry<T>[] prev = new SkipListEntry[maxLevel];
		index = new int[maxLevel];
		idx = 0;
		SkipListEntry<T> p = head;
		for (int i = maxLevel - 1; i >= 0; i--) {
			while (p.next[i].element != null && p.next[i].element.compareTo(x) < 0) {
				idx += p.span[i];
				p = p.next[i];
			}
			prev[i] = p;
			index[i] = idx;
		}
		return prev;
	}

	/**
	 * Add x to list. If x already exists, replace it. Returns true if new node is
	 * added to list
	 * 
	 * @param x
	 * @return
	 */
	public boolean add(T x) {
		SkipListEntry<T>[] prev = find(x);
		if (prev[0].next[0].element != null && prev[0].next[0].element.compareTo(x) == 0) {
			prev[0].next[0].element = x;
			return false;
		} else {
			int oldMaxLevel = maxLevel;
			int lev = chooseLevel();
			SkipListEntry<T> n = new SkipListEntry<T>(x, lev);
			for (int i = 0; i < lev; i++) {
				n.next[i] = prev[i].next[i];
				prev[i].next[i] = n;
				n.span[i] = prev[i].span[i] - (idx - index[i]);
				prev[i].span[i] = idx + 1 - index[i];
			}
			size++;

			if (maxLevel > oldMaxLevel)
				head.span[oldMaxLevel] = size + 1;

			for (int i = lev; i < prev.length; i++)
				prev[i].span[i] += 1;

		}
		return true;
	}

	/**
	 * Chooses a random level for each element
	 * 
	 * @return
	 */
	public int chooseLevel() {
		Random rand = new Random();
		int mask = (1 << maxLevel) - 1;
		int lev = Integer.numberOfTrailingZeros(rand.nextInt() & mask) + 1;
		if (lev > maxLevel)
			return maxLevel++;
		else
			return lev;
	}

	/**
	 * Find smallest element that is greater or equal to x
	 * 
	 * @param x
	 * @return
	 */
	public T ceiling(T x) {
		SkipListEntry<T>[] prev = find(x);
		return prev[0].next[0].element;
	}

	/**
	 * Checks whether the list contains the element or not
	 * 
	 * @param x
	 * @return
	 */
	public boolean contains(T x) {
		SkipListEntry<T>[] prev = find(x);
		if (prev[0].next[0].element != null && prev[0].next[0].element.compareTo(x) == 0)
			return true;
		return false;
	}

	/**
	 * Returns first element of list
	 * 
	 * @return
	 */
	public T first() {
		return head.next[0].element;
	}

	/**
	 * Finds largest element that is less than or equal to x
	 * 
	 * @param x
	 * @return
	 */
	public T floor(T x) {
		SkipListEntry<T>[] prev = find(x);
		if (prev[0].next[0].element.compareTo(x) == 0)
			return prev[0].next[0].element;
		else
			return prev[0].element;
	}

	/**
	 * Returns element at index n of list
	 * 
	 * @param n
	 * @return
	 */
	public T get(int n) {
		if (n >= size) {
			return null;
		}
		SkipListEntry<T> p = head;
		idx = -1;
		for (int i = maxLevel - 1; i >= 0; i--) {
			while (p.next[i].element != null && idx + p.span[i] <= n) {
				idx += p.span[i];
				p = p.next[i];
				if (idx == n)
					return p.element;
			}
		}
		return p.element;
	}

	/**
	 * Returns whether the list is empty or not
	 * 
	 * @return
	 */
	public boolean isEmpty() {
		return size == 0 ? true : false;
	}

	/**
	 * Iterates through the elements of list in sorted order
	 * 
	 * @return
	 */
	public Iterator<T> iterator() {
		return new SkipListIterator<>(head);
	}

	private class SkipListIterator<E> implements Iterator<E> {
		SkipListEntry<E> prev = null;

		private SkipListIterator(SkipListEntry<E> head) {
			prev = head.next[0];
		}

		public boolean hasNext() {
			return prev != null;
		}

		public E next() {
			SkipListEntry<E> entry = prev;
			if (entry.next[0] != null && entry.next[0].element != null)
				prev = entry.next[0];
			else
				prev = null;
			return entry.element;
		}
	}

	/**
	 * Returns last element of list
	 * 
	 * @return
	 */
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

	}

	/**
	 * Remove x from list. Removed element is returned. Return null if x not in list
	 * 
	 * @param x
	 * @return
	 */
	public T remove(T x) {
		SkipListEntry<T>[] prev = find(x);
		SkipListEntry<T> n = prev[0].next[0];
		if (n.element.compareTo(x) != 0)
			return null;
		else {
			for (int i = 0; i < maxLevel; i++) {
				if (prev[i].next[i].equals(n)) {
					prev[i].next[i] = n.next[i];
					prev[i].span[i] += n.span[i] - 1;
				} else
					break;
			}
			size--;
		}
		return n.element;
	}

	/**
	 * Returns the number of elements in the list
	 * 
	 * @return
	 */
	public int size() {
		return size;
	}

	public void printList() {
		SkipListEntry<T> p = head;
		for (int i = maxLevel - 1; i >= 0; i--) {
			p = head;
			String spaces = String.format("%" + p.span[i] + "s", " ");
			System.out.print(p.element + "(" + p.span[i] + ")" + spaces);
			p = p.next[i];
			while (p.element != null) {
				spaces = String.format("%" + p.span[i] + "s", "\t");
				System.out.print(p.element + "(" + p.span[i] + ")" + spaces);
				p = p.next[i];
			}
			System.out.println();
		}
	}

	public static void main(String args[]) {
		SkipList<Integer> skl = new SkipList<Integer>();
		// System.out.println(skl.first());
		// System.out.println(skl.last());
		skl.add(10);
		skl.add(20);
		skl.add(30);
		skl.add(8);
		skl.add(11);
		skl.add(21);
		skl.add(31);
		skl.printList();
		// skl.remove(8);
		// System.out.println(skl.first());
		// System.out.println(skl.last());
		// System.out.println(skl.floor(28));
		// System.out.println(skl.contains(30));
		// System.out.println(skl.contains(8));
		// System.out.println(skl.ceiling(29));
		System.out.println("Get: " + skl.get(5));
		// Iterator<Integer> sklIterator = skl.iterator();
		// while (sklIterator.hasNext()) {
		// System.out.print(sklIterator.next() + " ");
		// }
	}
}