//Starter code for lp1.

//Change following line to your group number
//Changed type of base to long: 1:15 PM, 2017-09-08.
package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.lp1_l1;

import java.util.LinkedList;

public class Num implements Comparable<Num> {
	// Long can store only upto 9 digits
	// So the base has to be set in a way such that it can divide upto 9 digits
	static long defaultBase = 999999999; // This can be changed to what you want it to be.
	long base = defaultBase; // Change as needed
	boolean isNegative = false;
	long baseLength = (long) (Math.log10(base) + 1);
	/* Start of Level 1 */
	LinkedList<Long> ll = new LinkedList<>();
	LinkedList<Long> outList = new LinkedList<>();

	/**
	 * Converts the string into a linked list
	 *
	 * @param s
	 */
	Num(String s) {
		int cursor = 0;
		final int len = s.length();

		if (len == 0)
			throw new NumberFormatException("Zero length BigInteger");

		char signChar = s.charAt(0);
		if (signChar == '+') {
			cursor = 1;
		} else if (signChar == '-') {
			isNegative = true;
			cursor = 1;
		}
		if (cursor == len)
			throw new NumberFormatException("Zero length BigInteger");

		// Skip leading zeros and compute number of digits in magnitude
		// while (cursor < len && Character.digit(s.charAt(cursor), (int) defaultBase)
		// == 0) {
		// cursor++;
		// }
		//
		// if (cursor == len) {
		// ll.add(0L);
		// return;
		// }

		// numDigits = len - cursor;

		String group;
		long groupVal = 0;
		int reversePtr = len;
		while (reversePtr - baseLength >= cursor) {
			group = s.substring((int) (reversePtr - baseLength), reversePtr);
			groupVal = Long.parseLong(group);
			reversePtr -= baseLength;
			ll.add(groupVal);
		}
		if (reversePtr != cursor) {
			group = s.substring(cursor, reversePtr);
			groupVal = Long.parseLong(group);
			ll.add(groupVal);
		}
	}

	/**
	 * Converts the long integer into a linked list
	 *
	 * @param x
	 */
	Num(long x) {
		int cursor = 0;
		int len = (int) (Math.log10(x) + 1);

		if (len == 0)
			throw new NumberFormatException("Zero length LongInteger");

		String s = Long.toString(x);
		String group;
		long groupVal = 0;
		int reversePtr = len;
		while (reversePtr - baseLength >= cursor) {
			group = s.substring((int) (reversePtr - baseLength), reversePtr);
			groupVal = Long.parseLong(group);
			reversePtr -= baseLength;
			ll.add(groupVal);
		}
		if (reversePtr != cursor) {
			group = s.substring(cursor, reversePtr);
			groupVal = Long.parseLong(group);
			ll.add(groupVal);
		}
	}

	static Num add(Num a, Num b) {
		return null;
	}

	static Num subtract(Num a, Num b) {
		return null;
	}

	// Implement Karatsuba algorithm for excellence credit
	static Num product(Num a, Num b) {
		return null;
	}

	// Use divide and conquer
	static Num power(Num a, long n) {
		return null;
	}
	/* End of Level 1 */

	/* Start of Level 2 */
	static Num divide(Num a, Num b) {
		return null;
	}

	static Num mod(Num a, Num b) {
		return null;
	}

	// Use divide and conquer
	static Num power(Num a, Num n) {
		return null;
	}

	static Num squareRoot(Num a) {
		return null;
	}
	/* End of Level 2 */

	// Utility functions
	// compare "this" to "other": return +1 if this is greater, 0 if equal, -1
	// otherwise
	public int compareTo(Num other) {
		return 0;
	}

	// Output using the format "base: elements of list ..."
	// For example, if base=100, and the number stored corresponds to 10965,
	// then the output is "100: 65 9 1"
	void printList() {
	}

	// Return number to a string in base 10
	public String toString() {
		return null;
	}

	public long base() {
		return base;
	}
}
