package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp2_q5_BoundedQueue;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

/**
 * @author Arun
 *
 * @param <T>
 */
public class BoundedQueue<T> {
	Queue<Integer> que = new PriorityQueue<>();
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
	 * Inserts the specified element into this queue if it is possible to do so
	 * immediately without violating capacity restrictions, returning true upon
	 * success and throwing an IllegalStateException if no space is currently
	 * available.
	 *
	 *
	 *
	 * @param element
	 * @return
	 */
	public boolean push(T element) {
		if (size == elements.length)
			throw new IllegalStateException("Exception : Queue is full");

		rear = (++rear) % elements.length;
		elements[rear] = element;
		return false;
	}

	/**
	 * Retrieves and removes the front of this queue, or returns null if this
	 * queue is empty.
	 *
	 * @return
	 */
	public T pop() {
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
	 * Retrieves, but does not remove, the head of this queue, or returns null
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
	 * Tests if this stack has no elements.
	 *
	 * @return
	 */
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * Returns the number of elements in this stack.
	 *
	 * @return
	 */
	public int size() {
		return size;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return Arrays.toString(elements);
	}

	/**
	 * @param element
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public boolean resize(T element) {
		if (size > 0.9 * elements.length) {
			elements = (T[]) new Object[MINIMUM_CAPACITY];
		} else if (size < 0.25 * elements.length) {
			elements = (T[]) new Object[MINIMUM_CAPACITY];
		}
		return false;
	}

	/**
	 * Main method.
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		System.out.println("Enter the size of the stack");
		int capacity = s.nextInt();
		s.close();

		System.out.println("Creating an array based queue with capacity of " + capacity);
		BoundedQueue<Integer> stack = new BoundedQueue<>(capacity);

		System.out.println("Adding elements into the stack from 1 to " + capacity);
		for (int i = 1; i <= capacity; i++)
			stack.push(i);

		// The next push will overflow the stack
		System.out.println("Trying to push an extra element into the stack exceeding its capacity");
		try {
			stack.push(capacity + 1);
		} catch (IllegalStateException e) {
			System.out.println(e.getMessage());
		}

		System.out.println("The contents of the stack are " + stack);

		System.out.println("The peek element of the stack is " + stack.peek());

		System.out.println("Removing the top element from the stack");
		System.out.println("Removed " + stack.pop());

		System.out.println("Adding a new element");
		stack.push(capacity + 1);

		System.out.println("The contents of the stack are " + stack);
	}
}
