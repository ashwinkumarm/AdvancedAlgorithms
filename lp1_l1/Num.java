package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.lp1_l1;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * This class performs the arithmetic operations for the given numbers in any
 * base ranging from 2 to 10000 Operations: Addition,
 * Subtraction,Multiplication,Exponentiation, printList
 *
 * @author Ashwin, Arun, Deepak, Haritha
 *
 */
public class Num implements Comparable<Num> {
	static long defaultBase = 10000;
	static long base = defaultBase;
	//static Num TEN = new Num(10L);
	static Num ZERO = new Num(0L);
	static Num ONE = new Num(1L);
	static Num TWO = new Num(2L);
	static long ZERO_LONG = 0L;
	static long ONE_LONG = 1L;
	static long TEN_LONG = 10L;
	boolean isNegative = false;

	/* Start of Level 1 */

	// LinkedList to store the digits of a given number
	LinkedList<Long> digits = new LinkedList<>();

	/**
	 * Helper function for the above constructor
	 *
	 * @param s
	 */
	Num(String s) {
		int cursor = 0;

		char[] inputDigits = s.toCharArray();

		final int len = inputDigits.length;

		if (len == 0)
			throw new NumberFormatException("Zero length Num");

		char signChar = inputDigits[0];
		if (signChar == '+') {
			cursor = 1;
		} else if (signChar == '-') {
			isNegative = true;
			cursor = 1;
		}
		if (cursor == len)
			throw new NumberFormatException("Zero length Num");

		// Skip leading zeros and compute number of digits in magnitude
		while (cursor < len && inputDigits[cursor] == '0') {
			cursor++;
		}

		if (cursor == len) {
			return;
		}

		// Convert a number into a number of given base
		Num num = new Num(ZERO_LONG);
		for (int i = cursor; i < len; i++)
			num = add(product(num, new Num(10L)), new Num(Character.getNumericValue(inputDigits[i])));
		this.digits = num.digits;
	}

	/**
	 * Helper function for the above 'long' constructor
	 *
	 * @param x
	 */
	Num(long x) {
		if (x < 0) {
			isNegative = true;
			x = -x;
		}
		while (x != ZERO_LONG) {
			digits.add(x % base);
			x /= base;
		}
	}

	/**
	 * Adds the two numbers of Num type for the given base(Helper function)
	 * Achieved this using method overloading
	 *
	 * @param a
	 * @param b
	 * @param baseForAdd
	 * @return
	 */
	static Num add(Num a, Num b) {
		Num result = new Num(ZERO_LONG);
		if (a.isNegative == b.isNegative) {
			result.isNegative = a.isNegative;
			return add(a, b, result);
		} else {
			int cmp = a.compareMag(b);
			Num resultMag = (cmp > 0 ? subtract(a, b, result) : subtract(b, a, result));
			resultMag.isNegative = cmp > 0 ? a.isNegative : b.isNegative;
			return resultMag;
		}

	}
	
	static Num add1(Num a, Num b) {
		Num result = new Num(ZERO_LONG);
		return add(a,b,result);
	}

	/**
	 * Helper function to perform addition
	 *
	 * @param a
	 * @param b
	 * @param result
	 * @param baseForAdd
	 * @return
	 */
	static Num add(Num a, Num b, Num result) {
		long carry = 0;
		Iterator<Long> aIterator = a.digits.iterator();
		Iterator<Long> bIterator = b.digits.iterator();
		while (aIterator.hasNext() && bIterator.hasNext()) {
			long sum = aIterator.next() + bIterator.next() + carry;
			if (sum >= base) {
				carry = 1;
				sum -= base;
			} else {
				carry = 0;
			}
			result.digits.add(sum);
		}
		while (aIterator.hasNext()) {
			long sum = aIterator.next() + carry;
			if (sum >= base) {
				carry = 1;
				sum -= base;
			} else {
				carry = 0;
			}
			result.digits.add(sum);
		}
		while (bIterator.hasNext()) {
			long sum = bIterator.next() + carry;
			if (sum >= base) {
				carry = 1;
				sum -= base;
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

	/**
	 * Performs the subtraction of two numbers of Num type for the given base
	 *
	 * @param a
	 * @param b
	 * @return
	 */
	static Num subtract(Num a, Num b) {
		Num result = new Num(ZERO_LONG);
		if (a.isNegative != b.isNegative) {
			result.isNegative = a.isNegative;
			return add(a, b, result);
		} else {
			int cmp = a.compareMag(b);
			if (cmp == 0)
				return result;
			Num resultMag = (cmp > 0 ? subtract(a, b, result) : subtract(b, a, result));
			resultMag.isNegative = (cmp > 0 ? a.isNegative : !a.isNegative);
			return resultMag;
		}
	}
	
	static Num subtract1(Num a, Num b) {
		Num result = new Num(ZERO_LONG);
		return subtract(a,b,result);
	}

	/**
	 * Helper function to perform subtraction
	 *
	 * @param a
	 * @param b
	 * @param result
	 * @return
	 */
	static Num subtract(Num a, Num b, Num result) {
		long borrow = 0;
		Iterator<Long> aIterator = a.digits.iterator();
		Iterator<Long> bIterator = b.digits.iterator();
		while (aIterator.hasNext() && bIterator.hasNext()) {
			long sub = aIterator.next() - bIterator.next() - borrow;
			if (sub < 0) {
				borrow = 1;
				sub += base;
			} else {
				borrow = 0;
			}
			result.digits.add(sub);
		}
		while (aIterator.hasNext()) {
			long sub = aIterator.next() - borrow;
			if (sub < 0) {
				borrow = 1;
				sub += base;
			} else {
				borrow = 0;
			}
			result.digits.add(sub);
		}
		while (bIterator.hasNext()) {
			long sub = bIterator.next() - borrow;
			if (sub < 0) {
				borrow = 1;
				sub += base;
			} else {
				borrow = 0;
			}
			result.digits.add(sub);
		}
		return trimZero(result);
	}

	/**
	 * Trim(Delete) the zeros from the most significant part for the given
	 * number
	 *
	 * @param a
	 * @return
	 */
	static Num trimZero(Num a) {
		Iterator<Long> aIterator = a.digits.descendingIterator();
		while (aIterator.hasNext() && aIterator.next() == 0) {
			aIterator.remove();
		}
		return a;
	}

	/**
	 * Performs the left shift operation Adds N zeros to the least significant
	 * part of the given number
	 *
	 * @param a
	 * @param N
	 * @return
	 */
	static Num leftShift(Num a, long N) {
		Num dupA = copyNum(a);
		while (N > 0) {
			dupA.digits.add(0, ZERO_LONG);
			N--;
		}
		return dupA;
	}

	/**
	 * Performs the right shift operation Removes N digits from the most
	 * significant part of the given number
	 *
	 * @param a
	 * @param N
	 * @return
	 */
	static Num rightShift(Num a, long N) {
		Num dupA = copyNum(a);
		Iterator<Long> aIterator = dupA.digits.iterator();
		while (N > 0 && aIterator.hasNext()) {
			aIterator.next();
			aIterator.remove();
			N--;
		}
		return dupA;
	}

	/**
	 * Makes a copy of the given number
	 *
	 * @param a
	 * @return
	 */
	static Num copyNum(Num a) {
		Num ta = new Num(ZERO_LONG);
		ta.digits.clear();
		Iterator<Long> aIterator = a.digits.iterator();
		while (aIterator.hasNext()) {
			ta.digits.add(aIterator.next());
		}
		return ta;
	}

	/**
	 * Performs the multiplication of two given numbers using Karatsuba
	 * algorithm
	 *
	 * @param a
	 * @param b
	 * @return
	 */
	static Num product(Num a, Num b) {
		Num result = karatsubaMultiplication(a, b);
		result.isNegative = a.isNegative ^ b.isNegative;
		return result;
	}

	/**
	 * Performs normal multiplication for the numbers with number of digits <=10
	 * @param a
	 * @param b
	 * @return
	 */
	static Num multiplyGradeSchool(Num a, Num b) {
		Num result = new Num(ZERO_LONG);

		Iterator<Long> aIterator = a.digits.iterator();
		Iterator<Long> bIterator = b.digits.iterator();

		long shift = 0;
		while (bIterator.hasNext()) {
			long carry = ZERO_LONG;
			long digit;
			Num partial = new Num(ZERO_LONG);
			long bDigit = bIterator.next();

			while (aIterator.hasNext()) {
				digit = (bDigit * aIterator.next()) + carry;
				carry = (long) Math.floor(digit / base);
				partial.digits.add(digit % base);
			}

			if (carry > 0)
				partial.digits.add(carry);

			partial = leftShift(partial, shift);

			// Append this partial product to the list
			result = add(result, partial);
			shift++;
			aIterator = a.digits.iterator();
		}
		return result;
	}
	/**
	 * Performs the Karatsuba multiplication by recursively dividing the numbers
	 *
	 * @param a
	 * @param b
	 * @return
	 */
	static Num karatsubaMultiplication(Num a, Num b) {
		long len1 = a.getNumberOfDigits();
		long len2 = b.getNumberOfDigits();
		long m = Math.min(len1, len2);

		if (m <= 100) {
			return multiplyGradeSchool(a, b);
		}

		m = (m / 2) + (m % 2);
		// Divides the two operands into low parts and high parts
		Num aHigh = rightShift(a, m);
		Num aLow = subtract1(a, leftShift(aHigh, m));
		Num bHigh = rightShift(b, m);
		Num bLow = subtract1(b, leftShift(bHigh, m));

		Num abLow = karatsubaMultiplication(aLow, bLow);
		Num abHigh = karatsubaMultiplication(aHigh, bHigh);
		Num abcd = karatsubaMultiplication(add1(aLow, aHigh), add1(bLow, bHigh));

		return add1(leftShift(abHigh, 2 * m), add1(leftShift(subtract1(subtract1(abcd, abHigh), abLow), m), abLow));
	}

	// Use divide and conquer

	/**
	 * Performs the exponent operation of Num type base and long integer type
	 * exponent using multiplication
	 *
	 * @param a
	 * @param n
	 * @return
	 */
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

	/**
	 * Divides the magnitude of the given number of any base by 2
	 *
	 * @param a
	 * @return
	 */
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

	/**
	 * Performs the division operation using binary search between 1 and a/2
	 *
	 * @param a
	 * @param b
	 * @return
	 */
	static Num divide(Num a, Num b) {
		Num result = new Num(ZERO_LONG);
		if (b.isZero())
			throw new ArithmeticException("denominator is zero");

		if (b.compareMag(a) == 1)
			return result;

		if (b.compareMag(a) == 0)
			return new Num(ONE_LONG);

		Num p = ONE;
		Num r = divideMagnitudeByTwo(a);
		while (p.compareTo(r) != 1) {
			result = divideMagnitudeByTwo(add1(p, r));
			if (product(result, b).compareMag(a) == 1)
				r = subtract1(result, ONE);
			else if (product(add1(result, ONE), b).compareMag(a) != 1)
				p = add1(result, ONE);
			else
				break;
		}

		if (a.isNegative != b.isNegative)
			result.isNegative = true;

		return result;
	}

	/**
	 * Performs mod operation a-(a/b)*b which returns the remainder of the
	 * division
	 *
	 * @param a
	 * @param b
	 * @return
	 */
	static Num mod(Num a, Num b) {
		Num mod = subtract1(a, product(divide(a, b), b));
		return mod;
	}

	// Use divide and conquer
	/**
	 * Performs the exponent operation of Num type base and Num type exponent
	 * using multiplication s = shift(n) by base B a^n = (a^s)^base * x^a0
	 *
	 * @param a
	 * @param n
	 * @return
	 */
	static Num power(Num a, Num n) {
		if (!n.isZero()) {
			long a0 = n.digits.getFirst();
			Num s = rightShift(n, ONE_LONG);
			return product(power(power(a, s), base), power(a, a0));
		}
		return ONE;
	}

	/**
	 * Finds the square root for the given number using binary search between
	 * x*x and (x+1)*(x+1)
	 *
	 * @param a
	 * @return
	 */
	static Num squareRoot(Num a) {
		Num result = new Num(ZERO_LONG);
		if (a.isZero())
			return result;

		if (a.isNegative)
			throw new NumberFormatException("Number is Negative. This method returns only positive square roots.");

		Num p = ONE;
		Num r = divideMagnitudeByTwo(a);
		while (p.compareTo(r) != 1) {
			result = divideMagnitudeByTwo(add1(p, r));
			if (product(result, result).compareMag(a) == 1)
				r = subtract1(result, ONE);
			else if (product(add1(result, ONE), add1(result, ONE)).compareMag(a) != 1)
				p = add1(result, ONE);
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

	/**
	 * Compares the magnitude of the two given numbers Returns 1 if a>b, 0 if
	 * a==b, else -1 if a<b
	 *
	 * @param other
	 * @return
	 */
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

	/**
	 * Outputs the number in the format "base: elements of list ..." For
	 * example, if base=100, and the number stored corresponds to 10965, then
	 * the output is "100: 65 9 1"
	 */
	void printList() {
		System.out.print(base + ": ");
		if (digits.isEmpty())
			System.out.print(0);
		else {
			Iterator<Long> iterator = digits.iterator();
			while (iterator.hasNext())
				System.out.print(iterator.next() + " ");
		}
	}

	// Return number to a string in base 10
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (isZero())
			return "0";
		if (isNegative == true)
			sb.append('-');
		Iterator<Long> iterator = digits.iterator();
		long oldbase = base;
		base = 10;
		Num sum = new Num(0L);
		long i = 0;
		Num base10 = new Num(oldbase);
		while (iterator.hasNext()) {
			sum = add(sum, product(new Num(iterator.next()), power(base10, i++)));
		}
		Iterator<Long> iteratorBaseTen = sum.digits.descendingIterator();
		while (iteratorBaseTen.hasNext())
			sb.append(iteratorBaseTen.next().toString());
		base = oldbase;
		return sb.toString();
	}

	/**
	 * @return the base
	 */
	public long base() {
		return base;
	}

	/**
	 * @return whether the number is zero or not
	 */
	public boolean isZero() {
		return digits.size() == 0;
	}

	/**
	 * Returns the number of digits for a given number
	 *
	 * @return
	 */
	public int getNumberOfDigits() {
		return digits.size();
	}
}
