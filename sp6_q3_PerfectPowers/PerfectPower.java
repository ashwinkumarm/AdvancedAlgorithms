package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp6_q3_PerfectPowers;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Scanner;

/**
 * Class to implement Perfect Power
 *
 * @author Ashwin, Arun, Deepak, Haritha
 *
 */
public class PerfectPower {

	// to check if an element is already been displayed
	static HashSet<Double> set = new HashSet<>();

	public static void findPerfectPower(double n) {

		PriorityQueue<Triplet> pq = new PriorityQueue<Triplet>();
		pq.add(new Triplet(2, 2, 4));

		while (!pq.isEmpty()) {
			Triplet currentPerfectPower = pq.poll();
			
			if (currentPerfectPower.value > n) {
				break;
			}
			
			if(currentPerfectPower.value < 0 || currentPerfectPower.value >= Double.MAX_VALUE){
				System.out.println("Max Double Reached");
				break;
			}
			
			if (!set.contains(currentPerfectPower.value)) {
				System.out.println(currentPerfectPower.value);
				set.add(currentPerfectPower.value);
			} 
			
			if (currentPerfectPower.a == 2) {
				pq.add(new Triplet(2, currentPerfectPower.b + 1, Math.pow(2, currentPerfectPower.b + 1)));
				pq.add(new Triplet(3, currentPerfectPower.b, Math.pow(3, currentPerfectPower.b)));
			} else {
				pq.add(new Triplet(currentPerfectPower.a + 1, currentPerfectPower.b,
						Math.pow(currentPerfectPower.a + 1, currentPerfectPower.b)));
			}
		}
	}

	public static void main(String[] args) {
		System.out.println("Enter the max value upto you want to find the perfect square");
		Scanner in = new Scanner(System.in);
		double n = in.nextDouble();
		findPerfectPower(n);
		//findPerfectPower(Double.MAX_VALUE );
		in.close();
	}
}
