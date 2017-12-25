package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp9_q2_ExactlyOnce;

import java.util.HashMap;

public class ExactlyOnce {

	static <T> void display(T[] o) {
		for (T i : o) {
				System.out.println(i);
		}
	}

	static <T extends Comparable<? super T>> T[] exactlyOnce(T[] A) {
		T[] output = (T[]) new Comparable[A.length];

		int size = 0;
		HashMap<T, Integer> valueWithIndexPos = new HashMap<T, Integer>();
		Integer ind;
		for (int i = 0; i < A.length; i++) {
			if ((ind = valueWithIndexPos.get(A[i])) == null) {
				valueWithIndexPos.put(A[i], i);
				output[i] = A[i];
				size++;
			} else {
				output[ind] = null;
				size--;
			}
		}

		T[] result = (T[]) new Comparable[size];
		int index = 0;
		for (T i : output) {
			if (i != null) {
				result[index] = i;
				index++;
			}
		}

		return result;
	}

	public static void main(String[] args) {
		Integer[] A = { 6, 3, 4, 5, 3, 5 };
		display((Comparable[]) exactlyOnce(A));
	}
}
