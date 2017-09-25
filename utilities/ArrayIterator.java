package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities;

import java.util.Iterator;
import java.lang.UnsupportedOperationException;

public class ArrayIterator<T> implements Iterator<T> {
	T[] arr;
	int startIndex, endIndex, cursor;

	ArrayIterator(T[] a) {
		arr = a;
		startIndex = 0;
		endIndex = a.length - 1;
		cursor = -1;
	}

	ArrayIterator(T[] a, int start, int end) {
		arr = a;
		startIndex = start;
		endIndex = end;
		cursor = start - 1;
	}

	public boolean hasNext() {
		return cursor < endIndex;
	}

	public T next() {
		return arr[++cursor];
	}

	public void remove() {
		throw new UnsupportedOperationException();
	}
}