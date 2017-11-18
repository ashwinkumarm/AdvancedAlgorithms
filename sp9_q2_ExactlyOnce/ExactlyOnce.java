package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp9_q2_ExactlyOnce;

import java.util.HashMap;

public class ExactlyOnce {

	static <T> void display(T[] o){
		for (T i : o) {
			if (i != null)
				System.out.println(i);
		}
	}
	static <T extends Comparable<? super T>> T[] exactlyOnce(T[] A) {
		T[] output = (T[]) new Comparable[A.length];

		HashMap<T, Integer> valueWithIndexPos = new HashMap<T, Integer>();
		for (int i = 0; i < A.length; i++) {
			if (!valueWithIndexPos.containsKey(A[i])) {
				valueWithIndexPos.put(A[i], i);
				output[i] = A[i];
			} else {
				output[valueWithIndexPos.get(A[i])] = null;
			}
		}

		for (T i : output) {
			if (i != null)
				System.out.println(i);
		}
		return output;
	}
		

	public static void main(String[] args) {
		Integer[] A = { 6, 3, 4, 5, 3, 5 };
		exactlyOnce(A);
		//display(exactlyOnce(A));
	}
}
