package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp2_q5_BoundedQueue;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @author Arun
 *
 * @param <T>
 */
public class BoundedQueue<T> {
	private int MINIMUM_CAPACITY = 16;
	private int front = -1, rear = -1, size = 0;
	T[] elements;

	/**
	 * Constructor to create a new BoundedQueue with minimum capacity
	 */
	@SuppressWarnings("unchecked")
	public BoundedQueue() {
		elements = (T[]) new Object[MINIMUM_CAPACITY];
		this.size = 0;
	}

	/**
	 * Constructor to create a new BoundedQueue with given capacity
	 *
	 * @param capacity
	 */
	@SuppressWarnings("unchecked")
	public BoundedQueue(int capacity) {
		// T[] newInstance = (T[]) Array.newInstance(c, size);
		if (capacity < MINIMUM_CAPACITY)
			capacity = MINIMUM_CAPACITY;
		elements = (T[]) new Object[capacity];
		this.size = 0;
	}

	/**
	 * Inserts the specified element into this queue at rear position if it is
	 * possible to do so immediately without violating capacity restrictions.
	 *
	 * @param element
	 * @return
	 */
	public boolean offer(T element) {
		if (size == elements.length)
			return false;

		rear = (++rear) % elements.length;
		elements[rear] = element;
		++size;
		if (front == -1) {
			front = rear;
		}
		return true;
	}

	/**
	 * Retrieves and removes the front of this queue, or returns null if this
	 * queue is empty.
	 *
	 * @return
	 */
	public T poll() {
		if (size == 0)
			return null;

		T element = elements[front];
		// to avoid memory leak
		elements[front] = null;
		front = (++front) % elements.length;

		if (--size == 0) {
			front = -1;
			rear = -1;
		}
		return element;
	}

	/**
	 * Retrieves, but does not remove, the front of this queue, or returns null
	 * if this queue is empty.
	 *
	 * @return
	 */
	public T peek() {
		if (size == 0)
			return null;

		return elements[front];
	}

	/**
	 * Tests if this queue has no elements.
	 *
	 * @return
	 */
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * Returns the number of elements in this queue.
	 *
	 * @return
	 */
	public int size() {
		return size;
	}

	/**
	 * Returns a string representation of this Queue, containing the String
	 * representation of each element.
	 *
	 * @return
	 */
	@Override
	public String toString() {
		return Arrays.toString(elements);
	}

	/**
	 * Doubles the queue size if the queue is mostly full (over 90% of the
	 * capacity), or halves it if the queue is mostly empty (less then 25%
	 * occupied of the capacity). Returns true if the array has been resized,
	 * otherwise false.
	 *
	 * @param element
	 * @return
	 */
	public boolean resize() {
		if (size > 0.9 * elements.length) {
			elements = (T[]) Arrays.copyOf(elements, elements.length << 1);
			return true;
		} else if (size < 0.25 * elements.length && elements.length >> 1 >= MINIMUM_CAPACITY) {
			// elements = (T[]) Arrays.copyOf(elements, elements.length / 2);
			@SuppressWarnings("unchecked")
			T[] newArray = (T[]) new Object[elements.length >> 1];
			for (int i = front, j = 0; i < size; i++, j++)
				newArray[j] = elements[i % elements.length];
			return true;
		} else
			return false;
	}

	/**
	 * Main method.
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		System.out.println("Enter the size of the queue");
		int capacity = s.nextInt();
		s.close();

		System.out.println("Creating an array based queue with capacity of " + capacity);
		BoundedQueue<Integer> queue = new BoundedQueue<>(capacity);

		System.out.println("Adding elements into the queue from 1 to " + capacity);
		for (int i = 1; i <= capacity; i++)
			queue.offer(i);

		// The next push will overflow the queue
		System.out.println("Trying to push an extra element into the queue exceeding its capacity");
		System.out.println("Has the element been added? " + (queue.offer(capacity + 1) ? "Yes" : "No"));

		System.out.println("The contents of the queue are " + queue);

		System.out.println("Calling resize() method to increase the size");
		System.out.println("Has the queue been resized? " + (queue.resize() ? "Yes" : "No"));

		System.out.println("Adding a new element");
		queue.offer(capacity + 1);

		System.out.println("The contents of the queue are " + queue);

		System.out.println("The front element of the queue is " + queue.peek());

		System.out.println("Removing the front element from the queue");
		System.out.println("Removed " + queue.poll());

		System.out.println("The contents of the queue are " + queue);
	}
}
