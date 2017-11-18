package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp9_q3_LongestStreak;

import java.util.HashSet;
import java.util.Set;

/**
 * This class finds the longest consecutive integers, needn't be continous 
 * @author Ashwin, Arun, Deepak, Haritha
 */

public class LongestStreak {

	static int longestStreak(int[] A) {

		Set<Integer> elementsSet = new HashSet<Integer>();

		for (int i : A) {
			elementsSet.add(i);
		}

		int maxStreak = 1;
		for (int i : A) {
			int left = i - 1;
			int right = i + 1;
			int streak = 1;

			while (elementsSet.contains(left)) {
				streak++;
				elementsSet.remove(left);
				left--;
			}

			while (elementsSet.contains(right)) {
				streak++;
				elementsSet.remove(right);
				right++;
			}

			maxStreak = Math.max(maxStreak, streak);
		}

		return maxStreak;
	}
	
	public static void main(String[] args){
		int[] A = {1,7,9,4,1,7,4,8,7,1};
		
		System.out.println(longestStreak(A));
	}
}
