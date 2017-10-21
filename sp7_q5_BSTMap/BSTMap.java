package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp7_q5_BSTMap;

import java.util.Iterator;

import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp7_q2_AVLTree.AVLTree;

/**
 * Implemenation of Binary search tree map built on top of AVLTree.
 *
 * @author Ashwin, Arun, Deepak, Haritha
 *
 */
public class BSTMap<K extends Comparable<? super K>, V> implements Iterable<K> {
	AVLTree<BSTMap.Entry<K, V>> tree;

	/**
	 * This class represents a map entry (key-value pair) which will be stored
	 * in each node of the AVLTree.
	 *
	 * @param <K>
	 * @param <V>
	 */
	static class Entry<K extends Comparable<? super K>, V> implements Comparable<Entry<K, V>> {
		K key;
		V value;

		/**
		 * Constructor to create a key-value pair.
		 *
		 * @param key
		 * @param value
		 */
		public Entry(K key, V value) {
			this.key = key;
			this.value = value;
		}

		/**
		 * Compares two entries based on their keys.
		 */
		@Override
		public int compareTo(Entry<K, V> entry) {
			return this.key.compareTo(entry.key);
		}
	}

	/**
	 * Returns an iterator to iterate over the keys stored in the map, in sorted
	 * order.
	 */
	public Iterator<K> iterator() {
		return new BSTMapIterator();
	}

	/**
	 * Iterator implementation to iterate over the keys in sorted order.
	 *
	 * @author Arun
	 *
	 */
	private class BSTMapIterator implements Iterator<K> {
		Iterator<Entry<K, V>> iterator;

		/**
		 * Constructor to create a new iterator for the map.
		 */
		BSTMapIterator() {
			iterator = tree.iterator();
		}

		/**
		 * Returns true if the iteration has more elements.
		 */
		public boolean hasNext() {
			return iterator.hasNext();
		}

		/**
		 * Returns the next element pointed by the iterator.
		 */
		public K next() {
			return iterator.next().key;
		}
	}

	/**
	 * Constructor which creates the tree instance.
	 */
	public BSTMap() {
		tree = new AVLTree<>();
	}

	/**
	 * @param key
	 * @return
	 */
	public boolean containsKey(K key) {
		Entry<K, V> entry = new Entry<>(key, null);
		return tree.contains(entry);
	}

	/**
	 * Returns the value to which the specified key is mapped, or null if this
	 * map contains no mapping for the key.
	 *
	 * @param key
	 * @return
	 */
	public V get(K key) {
		Entry<K, V> entry = new Entry<>(key, null);
		Entry<K, V> treeEntry = tree.get(entry);
		return treeEntry != null ? treeEntry.value : null;
	}

	/**
	 * Associates the specified value with the specified key in this map. If the
	 * map previously contained a mapping for the key, the old value is
	 * replaced.
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean put(K key, V value) {
		Entry<K, V> newEntry = new Entry<>(key, value);
		return tree.insert(newEntry);
	}

	/**
	 * Driver method.
	 *
	 * @param args
	 */
	public static void main(String args[]) {

		BSTMap<Integer, Integer> map = new BSTMap<Integer, Integer>();
		int[] arr = { 3, 3, 4, 5, 3, 234567654, 5, 3, 2, 77, 543, 234567654 };
		for (int i = 0; i < arr.length; i++) {
			if (!map.containsKey(arr[i]))
				map.put(arr[i], 1);
			else {
				int val = map.get(arr[i]);
				map.put(arr[i], val + 1);
			}
		}

		Iterator<Integer> it = map.iterator();
		while (it.hasNext()) {
			int key = it.next();
			System.out.println(key + " - " + map.get(key));
		}
	}
}
