//Starter code for lp1.

//Change following line to your group number
//Changed type of base to long: 1:15 PM, 2017-09-08.
package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.lp1_l1;

import java.util.Iterator;
import java.util.LinkedList;

public class Num implements Comparable<Num> {
	// Long can store only upto 9 digits
	// So the base has to be set in a way such that it can divide upto 9 digits

	static long defaultBase = 10; // This can be changed to what you want it to
									// be.
	static long base = defaultBase; // Change as needed
	static Num baseInNum = new Num(base);
	static Num TEN = new Num(10L);
	static Num ZERO = new Num(0L);
	static Num ONE = new Num(1L);
	static Num TWO = new Num(2L);
	static long ZERO_LONG = 0L;
	static long ONE_LONG = 1L;
	static long TEN_LONG = 10L;
	boolean isNegative = false;
	static int baseLength = (int) ((base % 10 == 0) ? Math.log10(base) : (Math.log10(base) + 1));
	/* Start of Level 1 */
	LinkedList<Long> digits = new LinkedList<>();

	/**
	 * @param s
	 */
	Num(String s) {
		this(s, defaultBase);
	}

	/**
	 * Converts the string into a linked list
	 *
	 * @param s
	 */
	Num(String s, long userBase) {
		int cursor = 0;
		base = userBase;

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
			return;
		}
		Num numInBaseTen = new Num(s.charAt(cursor));
		for (int i = cursor + 1; i < len; i++)
			numInBaseTen = add(product(numInBaseTen, TEN), new Num(s.charAt(i)));

		while (numInBaseTen.compareTo(ZERO) != 0) {
			mod(numInBaseTen, baseInNum);
			numInBaseTen = divide(numInBaseTen, baseInNum);
		}
	}

	/**
	 * @param x
	 */
	Num(long x) {
		this(x, defaultBase);
	}

	/**
	 * Converts the long integer into a linked list
	 *
	 * @param x
	 */
	Num(long x, long userBase) {
		if (x < 0) {
			isNegative = true;
			x = -x;
		}
		while (x != ZERO_LONG) {
			digits.add(x % userBase);
			x /= userBase;
		}
	}

	static Num add(Num a, Num b) {
		return add(a, b, base);
	}

	static Num add(Num a, Num b, long baseForAdd) {
		Num result = new Num("0");
		result.digits.clear();
		if (a.isNegative == b.isNegative) {
			result.isNegative = a.isNegative;
			return add(a, b, result, baseForAdd);
		} else {
			int cmp = a.compareMag(b);
			Num resultMag = (cmp > 0 ? subtract(a, b, result) : subtract(b, a, result));
			resultMag.isNegative = cmp > 0 ? a.isNegative : b.isNegative;
			return resultMag;
		}

	}

	static Num add(Num a, Num b, Num result, long baseForAdd) {
		long carry = 0;
		Iterator<Long> aIterator = a.digits.iterator();
		Iterator<Long> bIterator = b.digits.iterator();
		while (aIterator.hasNext() && bIterator.hasNext()) {
			long sum = aIterator.next() + bIterator.next() + carry;
			if (sum >= baseForAdd) {
				carry = 1;
				sum -= baseForAdd;
			} else {
				carry = 0;
			}
			result.digits.add(sum);
		}
		while (aIterator.hasNext()) {
			long sum = aIterator.next() + carry;
			if (sum >= baseForAdd) {
				carry = 1;
				sum -= baseForAdd;
			} else {
				carry = 0;
			}
			result.digits.add(sum);
		}
		while (bIterator.hasNext()) {
			long sum = bIterator.next() + carry;
			if (sum >= baseForAdd) {
				carry = 1;
				sum -= baseForAdd;
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
		Num result = new Num(ZERO_LONG);
		if (a.isNegative != b.isNegative) {
			result.isNegative = a.isNegative;
			return add(a, b, result, base);
		} else {
			int cmp = a.compareMag(b);
			if (cmp == 0)
				return result;
			Num resultMag = (cmp > 0 ? subtract(a, b, result) : subtract(b, a, result));
			resultMag.isNegative = (cmp > 0 ? a.isNegative : !a.isNegative);
			return resultMag;
		}
	}

	static Num subtract(Num a, Num b, Num result) {

		long borrow = 0;
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

	static String makeEqualLength(String str1, String str2) {
		int len1 = str1.length();
		int len2 = str2.length();
		if (len1 < len2) {
			for (int i = 0; i < len2 - len1; i++)
				str1 = '0' + str1;
			return str1;
		} else if (len1 > len2) {
			for (int i = 0; i < len1 - len2; i++)
				str2 = '0' + str2;
			return str2;
		}
		return null;
	}

	static Num leftShift(Num a, long N) {
		while (N > 0) {
			a.digits.add(ZERO_LONG);
			N--;
		}
		return a;
	}

	static Num rightShift(Num a, long N) {
		Iterator<Long> aIterator = a.digits.iterator();
		while (N > 0 && aIterator.hasNext()) {
			aIterator.next();
			aIterator.remove();
			N--;
		}
		return a;
	}

	// Implement Karatsuba algorithm for excellence credit
	static Num product(Num a, Num b) {
		return new Num(karatsubaMultiplication(a.toString(), b.toString()));
	}

	static Num multiply(Num a, Num b) {
		return a;
	}

	static Num karatsubaMultiplication(Num a, Num b) {

		long len1 = a.getNumberOfDigits();
		long len2 = b.getNumberOfDigits();
		long m = Math.max(len1, len2);

		if (m >= 5) {
			return multiply(a, b);
		}

		m = (m / 2) + (m % 2);

		Num aHigh = rightShift(a, m);
		Num aLow = subtract(a, leftShift(aHigh, m));
		Num bHigh = rightShift(b, m);
		Num bLow = subtract(b, leftShift(bHigh, m));

		Num abLow = karatsubaMultiplication(aLow, bLow);
		Num abHigh = karatsubaMultiplication(aHigh, bHigh);
		Num abcd = karatsubaMultiplication(add(aLow, aHigh), add(bLow, bHigh));

		// add(leftShift(abHigh,2*m),add(leftShift(subtract(subtract(abcd,abHigh),abLow),m),abLow))
		return a;

	}

	static long karatsubaMultiplication(String a, String b) {
		int len1 = a.length();
		int len2 = b.length();
		int m = Math.max(len1, len2);

		if (len1 < len2)
			a = makeEqualLength(a, b);
		else if (len2 < len1)
			b = makeEqualLength(a, b);

		if (m == 0)
			return 0;
		if (m == 1)
			return (a.charAt(0) - '0') * (b.charAt(0) - '0');

		int m2 = m / 2;

		String low1 = a.substring(0, m2);
		String high1 = a.substring(m2, m);
		String low2 = b.substring(0, m2);
		String high2 = b.substring(m2, m);

		long z0 = karatsubaMultiplication(low1, low2);
		long z1 = karatsubaMultiplication(Long.toString(Long.parseLong(low1) + Long.parseLong(high1)),
				Long.toString(Long.parseLong(low2) + Long.parseLong(high2)));
		long z2 = karatsubaMultiplication(high1, high2);
		return (long) (z0 * (Math.pow(base, 2 * (m - m2))) + (z1 - z2 - z0) * (Math.pow(base, m - m2)) + z2);
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

	static Num divideMagnitudeByTwo(Num a) {
		Num result = new Num(ZERO_LONG);

		if (a.digits.isEmpty())
			return result;

		Iterator<Long> iterator = a.digits.descendingIterator();
		long digit, div, carry = 0;
		boolean msd = true;

		while (iterator.hasNext()) {
			digit = iterator.next();
			if ((div = digit / 2) == 0) {
				if (msd != true)
					result.digits.addFirst(carry);
				carry = (digit % 2 == 0) ? 0 : base / 2;
			} else {
				result.digits.addFirst(carry + div);
				carry = (digit % 2 == 0) ? 0 : base / 2;
			}
			msd = false;
		}

		return result;
	}

	static Num divide(Num a, Num b) {
		Num result = new Num(ZERO_LONG);
		if (b.isZero())
			throw new ArithmeticException("denominator is zero");

		if (b.compareMag(a) == 1)
			return result;

		if (a.isNegative != b.isNegative)
			result.isNegative = true;
		if (b.compareMag(a) == 0)
			return new Num(ONE_LONG);

		Num p = ONE;
		Num r = divideMagnitudeByTwo(a);
		while (p.compareTo(r) != 1) {
			result = divideMagnitudeByTwo(add(p, r));
			if (product(result, b).compareMag(a) == 1)
				r = subtract(result, ONE);
			else if (product(add(result, ONE), b).compareMag(a) != 1)
				p = add(result, ONE);
			else
				break;
		}

		return result;
	}

	static Num mod(Num a, Num b) {
		Num mod = subtract(a, product(divide(a, b), b));
		return mod;
	}

	// Use divide and conquer
	static Num power(Num a, Num n) {
		return null;
	}

	static Num squareRoot(Num a) {
		Num result = new Num(ZERO_LONG);
		if (a.isZero())
			return result;

		if (a.isNegative)
			throw new NumberFormatException("Number is Negative. This method returns only positive square roots.");

		Num p = ONE;
		Num r = divideMagnitudeByTwo(a);
		while (p.compareTo(r) != 1) {
			result = divideMagnitudeByTwo(add(p, r));
			if (product(result, result).compareMag(a) == 1)
				r = subtract(result, ONE);
			else if (product(add(result, ONE), add(result, ONE)).compareMag(a) != 1)
				p = add(result, ONE);
			else
				break;
		}

		return result;
	}
	/* End of Level 2 */

	// Utility functions
	// compare "this" to "other": return +1 if this is greater, 0 if equal, -1
	// otherwise
	public int compareTo(Num other) {
		int magnitude = 0;
		if (this.isNegative == other.isNegative) {
			if (this.isNegative) {
				magnitude = compareMag(other);
			} else {
				magnitude = compareMag(other);
			}
		} else if (this.isNegative) {
			magnitude = -1;
		} else {
			magnitude = 1;
		}

		return magnitude;
	}

	public int compareMag(Num this,Num other) {
		if (this.getNumberOfDigits() > other.getNumberOfDigits()) {
			return 1;
		} else if (this.getNumberOfDigits() < other.getNumberOfDigits()) {
			return -1;
		}
		Iterator<Long> thisIterator = this.digits.descendingIterator();
		Iterator<Long> otherIterator = other.digits.descendingIterator();
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
		if (isZero())
			return "0";
		if (isNegative == true)
			sb.append('-');
		Iterator<Long> iterator = digits.iterator();
		Num sum = new Num(0L, 10L);
		long i = 0;
		while (iterator.hasNext())
			sum = add(sum, new Num((long) (iterator.next() * Math.pow(base, i++)), TEN_LONG), TEN_LONG);

		Iterator<Long> iteratorBaseTen = sum.digits.descendingIterator();
		while (iteratorBaseTen.hasNext())
			sb.append(iteratorBaseTen.next().toString());
		return sb.toString();
	}

	public long base() {
		return base;
	}

	public boolean isZero() {
		return digits.size() == 0;
	}

	public int getNumberOfDigits() {
		return digits.size();
	}
}
