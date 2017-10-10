package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp6_q3_PerfectPowers;

/**
 * Triplet class for perfect power
 *
 * @author Ashwin, Arun, Deepak, Haritha
 *
 */
public class Triplet implements Comparable<Triplet> {

	int a;
	int b;
	double value;

	/**
	 * Constructor for initializing the triplet
	 * 
	 * @param a
	 * @param b
	 * @param value
	 */
	Triplet(int a, int b, double value) {
		this.a = a;
		this.b = b;
		this.value = value;
	}

	@Override
	public int compareTo(Triplet t) {
		return (int) (this.value - t.value);
	}

}
