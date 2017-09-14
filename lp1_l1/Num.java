//Starter code for lp1.

//Change following line to your group number
//Changed type of base to long: 1:15 PM, 2017-09-08.
package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.lp1_l1;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

public class Num implements Comparable<Num> {
	// Long can store only upto 9 digits
	// So the base has to be set in a way such that it can divide upto 9 digits
	static long defaultBase = 1000;
	static long base = defaultBase; // Change as needed
	static Num baseInNum = new Num(base);
	static Num TEN = new Num(10L);
	static Num ZERO = new Num(0L);
	static long ZERO_LONG = 0L;
	static long ONE_LONG = 1L;
	boolean isNegative = false;
	static int baseLength = (int) ((base % 10 == 0) ? Math.log10(base) : (Math.log10(base) + 1));
	/* Start of Level 1 */
	LinkedList<Long> digits = new LinkedList<>();

	int numDigits;

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
		while (cursor < len && s.charAt(cursor) == '0') {
			cursor++;
		}

		if (cursor == len) {
			digits.add(0L);
			return;
		}
		numDigits = len - cursor;
		Num numInBaseTen = new Num(s.charAt(cursor));
		for (int i = cursor + 1; i < len; i++)
			numInBaseTen = add(product(numInBaseTen, TEN), new Num(s.charAt(i)));

		while (numInBaseTen.compareTo(ZERO) != 0) {
			mod(numInBaseTen, baseInNum);
			numInBaseTen = divide(numInBaseTen, baseInNum);
		}
	}

	/**
	 * Converts the long integer into a linked list
	 *
	 * @param x
	 */
	Num(long x) {
		while (x != ZERO_LONG) {
			digits.add(x % base);
			x /= base;
		}
	}

	static Num add(Num a, Num b) {
		Num result = new Num("0");
		result.digits.clear();
		if (!a.isNegative) {
			if (!b.isNegative) {
				return add(a, b, result);
			} else {
				return subtract(a, b, result);
			}
		} else {
			if (!b.isNegative) {
				return subtract(b, a, result);
			} else {
				result.isNegative = true;
				return add(a, b, result);
			}
		}
	}

	static Num add(Num a, Num b, Num result) {
		long carry = 0;
		Iterator<Long> aIterator = a.digits.iterator();
		Iterator<Long> bIterator = b.digits.iterator();
		while (aIterator.hasNext() && bIterator.hasNext()) {
			long sum = aIterator.next() + bIterator.next() + carry;
			if (sum >= defaultBase) {
				carry = 1;
				sum -= defaultBase;
			} else {
				carry = 0;
			}
			result.digits.add(sum);
		}
		while (aIterator.hasNext()) {
			long sum = aIterator.next() + carry;
			if (sum >= defaultBase) {
				carry = 1;
				sum -= defaultBase;
			} else {
				carry = 0;
			}
			result.digits.add(sum);
		}
		while (bIterator.hasNext()) {
			long sum = bIterator.next() + carry;
			if (sum >= defaultBase) {
				carry = 1;
				sum -= defaultBase;
			} else {
				carry = 0;
			}
			result.digits.add(sum);
		}
		if (carry > 0) {
			result.digits.add(carry);
		}

		return result;

	}

	static Num subtract(Num a, Num b) {
		Num result = new Num("0");
		result.digits.clear();
		if (!a.isNegative) {
			if (!b.isNegative) {
				return subtract(a, b, result);
			} else {
				return add(a, b, result);
			}
		} else {
			if (!b.isNegative) {
				result.isNegative = true;
				return add(a, b, result);
			} else {
				return subtract(b, a, result);
			}
		}
	}

	static Num subtract(Num a, Num b, Num result) {
		if (a.compareMag(b) < 0) {
			result.isNegative = true;
			Num t = a;
			a = b;
			b = t;
		}
		long borrow = 0;
		result.digits.clear();
		if (a.compareMag(b) < 0) {
			result.isNegative = true;
		}
		Iterator<Long> aIterator = a.digits.iterator();
		Iterator<Long> bIterator = b.digits.iterator();
		while (aIterator.hasNext() && bIterator.hasNext()) {
			long sub = aIterator.next() - bIterator.next() - borrow;
			if (sub < 0) {
				borrow = 1;
				sub += defaultBase;
			} else {
				borrow = 0;
			}
			result.digits.add(sub);
		}
		while (aIterator.hasNext()) {
			long sub = aIterator.next() - borrow;
			if (sub < 0) {
				borrow = 1;
				sub += defaultBase;
			} else {
				borrow = 0;
			}
			result.digits.add(sub);
		}
		while (bIterator.hasNext()) {
			long sub = bIterator.next() - borrow;
			if (sub < 0) {
				borrow = 1;
				sub += defaultBase;
			} else {
				borrow = 0;
			}
			result.digits.add(sub);
		}
		return result;
	}

	// Implement Karatsuba algorithm for excellence credit
	static Num product(Num a, Num b) {
		ListIterator<Long> itr1 = a.digits.listIterator();
		ListIterator<Long> itr2 = b.digits.listIterator();
		long result = 0;

		String multiplierString = "";
		for (int i = 0; i <= baseLength; i++)
			if (i == 0)
				multiplierString += '1';
			else
				multiplierString += '0';
		long multiplier = Long.parseLong(multiplierString);
		long units1 = 1, units2, data;

		while (itr1.hasNext()) {
			data = itr1.next();
			units2 = 1;
			while (itr2.hasNext()) {
				result += karatsubaMultiplication(data, itr2.next()) * units2 * units1;
				units2 *= multiplier;
			}
			units1 *= multiplier;
			itr2 = b.digits.listIterator();
		}
		return new Num(0L);
	}

	static long karatsubaMultiplication(Long a, Long b) {
		String s1 = Long.toString(a);
		String s2 = Long.toString(b);

		int m = Math.max(s1.length(), s2.length());
		int m2 = m / 2;
		if (m2 == 0)
			return 0;
		if (m2 == 1)
			return a * b;

		String[] partition1 = strCopy(m2, s1);
		String[] partition2 = strCopy(m2, s2);
		long low1 = Long.parseLong(partition1[0]);
		long high1 = Long.parseLong(partition1[1]);
		long low2 = Long.parseLong(partition2[0]);
		long high2 = Long.parseLong(partition2[1]);

		long z0 = karatsubaMultiplication(low1, low2);
		long z1 = karatsubaMultiplication(low1 + high1, low2 + high2);
		long z2 = karatsubaMultiplication(high1, high2);
		return z0 * (1 << m) + (z1 - z2 - z0) * (1 << m2) + z2;
	}

	public static String[] strCopy(int index, String string) {
		String first = "", last = "";
		int actualIndex = string.length() - index;
		for (int i = 0; i < actualIndex; i++) {
			first += string.charAt(i);
		}
		for (int i = actualIndex; i < string.length(); i++) {
			last += string.charAt(i);
		}
		return new String[] { first, last };
	}

	// Use divide and conquer
	static Num power(Num a, long n) {
		if (n == ZERO_LONG)
			return new Num(ONE_LONG);
		else if (n == ONE_LONG)
			return a;
		else {
			Num s = power(a, n / 2);
			if (n % 2 == 0)
				return product(s, s);
			else
				return product(product(s, s), a);
		}
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
		if (this.isNegative == other.isNegative) {
			if (this.isNegative) {
				compareMag(other);
			} else {
				compareMag(other);
			}
		} else if (this.isNegative) {
			return -1;
		} else {
			return 1;
		}

		return 0;
	}

	public int compareMag(Num other) {
		if (this.numDigits > other.numDigits) {
			return 1;
		} else if (this.numDigits < other.numDigits) {
			return -1;
		}
		Iterator<Long> thisIterator = this.digits.descendingIterator();
		Iterator<Long> otherIterator = this.digits.descendingIterator();
		while (thisIterator.hasNext()) {
			long thisIteratorValue = thisIterator.next();
			long otherIteratorValue = otherIterator.next();
			if (thisIteratorValue != otherIteratorValue) {
				return thisIteratorValue > otherIteratorValue ? 1 : -1;
			}
		}

		return 0;
	}

	// Output using the format "base: elements of list ..."
	// For example, if base=100, and the number stored corresponds to 10965,
	// then the output is "100: 65 9 1"
	void printList() {
	}

	// Return number to a string in base 10
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (isNegative == true)
			sb.append('-');
		Iterator<Long> iterator = digits.descendingIterator();
		while (iterator.hasNext()) {
			sb.append(iterator.next().toString());
		}
		return sb.toString();
	}

	public long base() {
		return base;
	}
}
