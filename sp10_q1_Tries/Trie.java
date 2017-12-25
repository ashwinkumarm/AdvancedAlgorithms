package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp10_q1_Tries;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;

public class Trie<T, V> {
	private class Entry {
		V value;
		HashMap<T, Entry> child;

		Entry(V value) {
			this.value = value;
			child = new HashMap<>();
		}

		@Override
		public boolean equals(Object obj) {
			Entry entry = (Entry) obj;
			if (this.value == entry.value)
				return true;
			else
				return false;
		}

		@Override
		public int hashCode() {
			int result = 17;
			result = 31 * result + (Integer) value;
			return result;
		}
	}

	private Entry root;

	Trie() {
		root = new Entry((V) new Integer(-1));
	}

	/*
	 * public Entry find(Iterator<T> iter, int depth) { HashMap<T, Entry>
	 * children = root.child; Entry t = null; while (iter.hasNext()) { T c =
	 * iter.next(); if (children.containsKey(c)) { depth++; t = children.get(c);
	 * children = t.child; } else { return t; } }
	 * 
	 * return t; }
	 */

	public void print() {
		Queue<Entry> q = new LinkedList<>();
		q.add(root);
		while (!q.isEmpty()) {
			Entry node = q.poll();
			for (Map.Entry<T, Entry> e : node.child.entrySet()) {
				System.out.println("Char : " + e.getKey() + ", " + "Value : " + e.getValue().value);
				q.add(e.getValue());
			}
		}
	}

	public V add(Iterator<T> iter, V value) {
		HashMap<T, Entry> children = root.child;

		while (iter.hasNext()) {
			T c = iter.next();

			Entry t;
			if (children.containsKey(c)) {
				t = children.get(c);
			} else {
				t = new Entry((V) new Integer(-1));
				children.put(c, t);
			}

			children = t.child;

			// set leaf node
			if (!iter.hasNext())
				t.value = value;
		}

		return value;
	}

	public V get(Iterator<T> iter) {
		Map<T, Entry> children = root.child;
		Entry t = null;
		while (iter.hasNext()) {
			T c = iter.next();
			if (children.containsKey(c)) {
				t = children.get(c);
				children = t.child;
			} else {
				return null;
			}
		}

		return t.value;
	}

	public V remove(Iterator<T> iter) {
		return null;
	}

	// How many words in the dictionary start with this prefix?
	public int prefixCount(Iterator<T> iter) {
		Map<T, Entry> children = root.child;
		Entry t = null;
		int count = 0;
		while (iter.hasNext()) {
			T c = iter.next();
			if (children.containsKey(c)) {
				t = children.get(c);
				children = t.child;
			} else {
				return 0;
			}
		}
		count = wordCount(t);
		return count;
	}

	public int wordCount(Entry t) {
		int c = 0;
		Queue<Entry> q = new LinkedList<>();
		q.add(t);
		if (!t.value.equals(new Integer(-1))) {
			c++;
		}
		while (!q.isEmpty()) {
			Entry node = q.poll();
			if (!node.value.equals(new Integer(-1))) {
				c++;
			}
			for (Map.Entry<T, Entry> e : node.child.entrySet()) {
				q.add(e.getValue());
			}
		}
		return c;
	}

	public static class StringIterator implements Iterator<Character> {
		char[] arr;
		int index;

		public StringIterator(String s) {
			arr = s.toCharArray();
			index = 0;
		}

		public boolean hasNext() {
			return index < arr.length;
		}

		public Character next() {
			return arr[index++];
		}

		public void remove() {
			throw new java.lang.UnsupportedOperationException();
		}
	}

	public static void main(String[] args) {
		Trie<Character, Integer> trie = new Trie<>();
		int wordno = 0;
		Scanner in = new Scanner(System.in);
		while (in.hasNext()) {
			String s = in.next();
			if (s.equals("End")) {
				break;
			}
			wordno++;
			trie.add(new StringIterator(s), wordno);
			// trie.print();
		}
		trie.print();

		/*
		 * while (in.hasNext()) { String s = in.next(); Integer val =
		 * trie.get(new StringIterator(s)); System.out.println(s + "\t" + val);
		 * }
		 */

		// trie.remove(new StringIterator("Tries"));

		// trie.print();

		System.out.println(trie.prefixCount(new StringIterator("Tri")));
	}
}