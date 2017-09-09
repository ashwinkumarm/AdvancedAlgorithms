package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp2_q9_SparsePolynomials;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Implements all the functions of the sparse polynomial
 * 
 * @author Ashwin, Arun, Deepak, Haritha
 *
 */
public class SparsePolynomial {

	/**
	 * Method to check whether the next element in the polynomial is present or not
	 * If not, return null
	 * @param itr
	 *            iterator
	 * @return next element or null
	 */
	public static <T> T next(Iterator<T> itr) {

		return itr.hasNext() ? itr.next() : null;
	}

	/**
	 * Generates polynomial for the user provided input
	 * 
	 * @param in
	 *            Scanner object
	 * @return poly polynomial in the form of linkedList
	 */
	public static LinkedList<Node> generatePolynomial(Scanner in) {
		System.out.println("Enter the number of terms in the polynomial");
		int m = in.nextInt();
		System.out.println("Enter coeff first, followed by power");
		LinkedList<Node> poly = new LinkedList<Node>();
		for (int i = 0; i < m; i++) {
			Node n = new Node(in.nextInt(), in.nextInt());
			poly.add(n);
		}
		return poly;
	}

	/**
	 * Performs the addition of two given polynomials
	 * @param firstPoly
	 *            input polynomial
	 * @param secPoly
	 *            input polynomial
	 * @return resultPoly polynomial after adding two polynomials
	 */
	public static LinkedList<Node> add(LinkedList<Node> firstPoly, LinkedList<Node> secPoly) {
		LinkedList<Node> resultPoly = new LinkedList<Node>();
		Iterator<Node> firstPolyIterator = firstPoly.iterator();
		Iterator<Node> secPolyIterator = secPoly.iterator();
		Node firstPolyNode = next(firstPolyIterator);
		Node secPolyNode = next(secPolyIterator);
		while (firstPolyNode != null && secPolyNode != null) {
			if (firstPolyNode.getExponent() > secPolyNode.getExponent()) {
				resultPoly.add(secPolyNode);
				secPolyNode = next(secPolyIterator);
			} else if (firstPolyNode.getExponent() < secPolyNode.getExponent()) {
				resultPoly.add(firstPolyNode);
				firstPolyNode = next(firstPolyIterator);
			} else {
				resultPoly
						.add(new Node(firstPolyNode.getCoeff() + secPolyNode.getCoeff(), firstPolyNode.getExponent()));
				firstPolyNode = next(firstPolyIterator);
				secPolyNode = next(secPolyIterator);
			}
		}
		while (firstPolyNode != null) {
			resultPoly.add(firstPolyNode);
			firstPolyNode = next(firstPolyIterator);
		}
		while (secPolyNode != null) {
			resultPoly.add(secPolyNode);
			secPolyNode = next(secPolyIterator);
		}
		return resultPoly;

	}

	/**
	 * Performs the multiplication of two provided polynomials
	 * 
	 * @param firstPoly
	 *            input polynomial
	 * @param secPoly
	 *            input polynomial
	 * @return multiplyResult polynomial after multiplying
	 */
	public static LinkedList<Node> multiply(LinkedList<Node> firstPoly, LinkedList<Node> secPoly) {
		LinkedList<Node> multiplyResult = new LinkedList<Node>();
		Iterator<Node> firstPolyIterator = firstPoly.iterator();
		Iterator<Node> secPolyIterator = secPoly.iterator();
		Node firstPolyNode = next(firstPolyIterator);
		Node secPolyNode = next(secPolyIterator);
		while (firstPolyNode != null) {
			while (secPolyNode != null) {
				multiplyResult.add(new Node(firstPolyNode.getCoeff() * secPolyNode.getCoeff(),
						firstPolyNode.getExponent() + secPolyNode.getExponent()));
				secPolyNode = next(secPolyIterator);
			}
			firstPolyNode = next(firstPolyIterator);
			secPolyIterator = secPoly.iterator();
			secPolyNode = next(secPolyIterator);
		}
		return mergeCommonTerms(multiplyResult);

	}

	/**
	 * Performs the merging of the common coefficient terms in the polynomial
	 * 
	 * @param multiplyResult
	 *            polynomial after multiplying
	 * @return resultPoly polynomial after multiplied and merged
	 */
	public static LinkedList<Node> mergeCommonTerms(LinkedList<Node> multiplyResult) {

		Collections.sort(multiplyResult);
		LinkedList<Node> resultPoly = new LinkedList<Node>();

		int prevExponent = multiplyResult.get(0).getExponent(), coeffSum = 0;
		for (int i = 0; i < multiplyResult.size(); i++) {
			if (prevExponent == multiplyResult.get(i).getExponent()) {
				coeffSum += multiplyResult.get(i).getCoeff();
			} else {
				resultPoly.add(new Node(coeffSum, prevExponent));
				prevExponent = multiplyResult.get(i).getExponent();
				coeffSum = multiplyResult.get(i).getCoeff();
			}
		}
		resultPoly.add(new Node(coeffSum, prevExponent));
		return resultPoly;
	}

	/**
	 * Finds the x^n value
	 * 
	 * @param x
	 *            input value
	 * @param n
	 *            exponent
	 * @return x^n value
	 */
	public static long pow(int x, int n) {
		if (n == 0) {
			return 1;
		}
		if (n == 1) {
			return x;
		}
		if (n % 2 == 0) {
			return pow(x * x, n / 2);
		} else {
			return pow(x * x, n / 2) * x;
		}
	}

	/**
	 * Evaluates the polynomial for the given 'x' value
	 * 
	 * @param x
	 *            input value
	 * @param poly
	 *            polynomial to be evaluated
	 * @return op output of the polynomial for the given x
	 */
	public static long evaluate(int x, LinkedList<Node> poly) {
		long op = 0;
		Iterator<Node> polyIterator = poly.iterator();
		Node node = next(polyIterator);
		while (node != null) {
			op += node.getCoeff() * pow(x, node.getExponent());
			node = next(polyIterator);
		}
		return op;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		LinkedList<Node> firstPoly = generatePolynomial(in);
		System.out.println("1. add\n2. multiply\n3. evaluate");
		int c = in.nextInt();
		if (c == 1) {
			System.out.println("Enter the second polynomial");
			LinkedList<Node> secPoly = generatePolynomial(in);
			System.out.println(add(firstPoly, secPoly));
		} else if (c == 2) {
			System.out.println("Enter the second polynomial");
			LinkedList<Node> secPoly = generatePolynomial(in);
			System.out.println(multiply(firstPoly, secPoly));

		} else if (c == 3) {
			System.out.println("Enter the value");
			int x = in.nextInt();
			System.out.println(evaluate(x, firstPoly));
		}

	}

}
