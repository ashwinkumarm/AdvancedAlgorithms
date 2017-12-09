package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp10_q5_RabinKarp;

public class RobinKarp {

	public static void robinkarp(String text, String pattern) {
		char[] textArr = text.toCharArray();
		char[] patternArr = pattern.toCharArray();
		double patternHash = 0;
		double textHash = 0;
		int pow = pattern.length() - 1;
		for (int i = 0; i < pattern.length(); i++) {
			patternHash += patternArr[i] * Math.pow(26, pow);
			textHash += textArr[i] * Math.pow(26, pow);
			pow--;
		}

		for (int i = patternArr.length; i < textArr.length; i++) {
			if (patternHash == textHash) {
				for (int p = 0; p < pattern.length(); p++) {
					if (textArr[p] != patternArr[p]) {
						break;
					}
				}
				System.out.println("Match at the index position " + (i - patternArr.length));

			}
			textHash = ((textHash - (textArr[i - patternArr.length] * Math.pow(26, pattern.length() - 1))) * 26)
					+ (textArr[i]);
		}

		if (patternHash == textHash) {
			for (int p = 0; p < pattern.length(); p++) {
				if (textArr[p] != patternArr[p]) {
					break;
				}
			}
			System.out.println("Match at the index position " + (textArr.length - patternArr.length));
		}
	}

	public static void main(String[] args) {
		String text = "AABAACAADAABAABA";
		String pattern = "AABA";
		robinkarp(text, pattern);

	}

}
