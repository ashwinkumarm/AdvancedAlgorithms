package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp2_q6_BoundedStack;

public class BoundedStack<T> {

	private T[] elements;
	private int size;

	@SuppressWarnings("unchecked")
	public BoundedStack(int capacity) {
		elements = (T[]) new Object[capacity];
	}

	public boolean push(T value) {
		if (this.size >= elements.length) {
			throw new ArrayIndexOutOfBoundsException("Stack is Full");
		}
		elements[size++] = value;
		return true;
	}

	public T pop() {
		// fail-fast implementation
		if (size <= 0) {
			return null;
		}
		T top = elements[--size];
		elements[size] = null;
		return top;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public int size() {
		return size;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for (int i = size - 1; i >= 0; i--) {
			sb.append(this.pop());
			if (i > 0) {
				sb.append(",");
			}
		}
		sb.append("]");
		return sb.toString();
	}

	public static void main(String[] args) {
		int capacity = 10;
		BoundedStack<Integer> stack = new BoundedStack<>(capacity);
		for (int i = 1; i <= capacity; i++)
			stack.push(i);
		// The next push will overflow the stack
		stack.push(capacity + 1);
		System.out.println(stack);
	}

}
