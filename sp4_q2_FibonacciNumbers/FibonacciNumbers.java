package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp4_q2_FibonacciNumbers;

import java.math.BigInteger;

public class FibonacciNumbers {
	static BigInteger[] memo;

	static BigInteger linearFibonacci(int n) {
		memo = new BigInteger[n+1];
		memo[0] = BigInteger.valueOf(0);
		memo[1] = BigInteger.valueOf(1);
		for (int i = 2; i <= n; i++) {
			memo[i] = memo[i - 1].add(memo[i - 2]);
		}
		return memo[n];
	}

	static BigInteger logFibonacci(int n) {
		BigInteger[][] matrixFactor = new BigInteger[][] { { BigInteger.valueOf(1), BigInteger.valueOf(1) },
				{ BigInteger.valueOf(1), BigInteger.valueOf(0) } };
		BigInteger[][] v1 = new BigInteger[][] { { BigInteger.valueOf(1) }, { BigInteger.valueOf(0) } };
		return product(power(matrixFactor, n - 1), v1)[0][0];
	}

	private static BigInteger[][] power(BigInteger[][] matrixFactor, int n) {
		if (n == 0)
			return new BigInteger[][] { { BigInteger.valueOf(1), BigInteger.valueOf(1) },
					{ BigInteger.valueOf(1), BigInteger.valueOf(1) } };
		if (n == 1)
			return matrixFactor;
		else {
			BigInteger[][] s = power(matrixFactor, n / 2);
			if (n % 2 == 0)
				return product(s, s);
			else
				return product(product(s, s), matrixFactor);
		}
	}

	private static BigInteger[][] product(BigInteger[][] num1, BigInteger[][] num2) {
		BigInteger[][] result = new BigInteger[2][2];
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < num2[0].length; j++) {
				result[i][j] = BigInteger.valueOf(0);
				for (int k = 0; k < 2; k++) {
					result[i][j] = result[i][j].add(num1[i][k].multiply(num2[k][j]));
				}
			}
		}
		return result;
	}

	public static void main(String args[]) {
		System.out.println("The nth Fibonacci number using linear algorithm: " + linearFibonacci(1000));
		System.out.println("The nth Fibonacci number using logarthmic algorithm: " + logFibonacci(1000));
	}

}
