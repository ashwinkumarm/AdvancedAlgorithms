package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp10_q2_KMP;

public class KMP {

	public static int[] computeSuffixPrefixArray(String s) {
		int[] suffPrefArray = new int[s.length()];

		int j = 0;
		for (int i = 1; i < s.length();) {
			if (s.charAt(i) == s.charAt(j)) {
				suffPrefArray[i] = j + 1;
				i++;
				j++;
			} else {
				if (j != 0) {
					j = suffPrefArray[j - 1];
				} else {
					suffPrefArray[i] = 0;
					i++;
				}
			}
		}
		return suffPrefArray;
	}

	public static void main(String[] args) {
		String s = "catacb";
		String sNew = s + "#" + new StringBuilder(s).reverse().toString();
		int[] sufPrefArray = computeSuffixPrefixArray(sNew);

		System.out.println(
				new StringBuilder(s.substring(sufPrefArray[sufPrefArray.length - 1])).reverse().toString() + s);

	}

}
