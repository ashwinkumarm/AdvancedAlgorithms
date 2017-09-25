package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp2_q6_BoundedStack;

import java.util.Scanner;

/**
 * Class BoundedStack
 *
 * <P>
 * This class implements an array-based, bounded-sized stack.
 *
 * @author Ashwin, Arun, Deepak, Haritha
 *
 */
public class BoundedStack<T> {

	private T[] elements;
	private int size;

	/**
	 * Constructor to create a bounded stack with the given capacity.
	 *
	 * @param capacity
	 */
	@SuppressWarnings("unchecked")
	public BoundedStack(int capacity) {
		elements = (T[]) new Object[capacity];
	}

	/**
	 * Pushes an item onto the top of this stack.
	 *
	 * @param value
	 * @return
	 */
	public boolean push(T value) {
		if (this.size >= elements.length) {
			throw new IllegalStateException("Exception : Stack is Full");
		}
		elements[size++] = value;
		return true;
	}

	/**
	 * Looks at the object at the top of this stack without removing it from the
	 * stack.
	 *
	 * @return
	 */
	public T peek() {
		// fail-safe implementation
		if (size <= 0)
			return null;

		return elements[size - 1];
	}

	/**
	 * Removes the object at the top of this stack and returns that object as
	 * the value of this function.
	 *
	 * @return
	 */
	public T pop() {
		// fail-safe implementation
		if (size <= 0)
			return null;

		T top = elements[--size];
		elements[size] = null;
		return top;
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

	/**
	 * Returns a string representation of this Vector, containing the String
	 * representation of each element.
	 *
	 * @return
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for (int i = size - 1; i >= 0; i--) {
			sb.append(this.elements[i]);
			if (i > 0) {
				sb.append(",");
			}
		}
		sb.append("]");
		return sb.toString();
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

		System.out.println("Creating an array based stack with capacity of " + capacity);
		BoundedStack<Integer> stack = new BoundedStack<>(capacity);

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
