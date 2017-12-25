package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.stringPrac;

import java.util.Arrays;

public class SuffixArray {

	Suffix[] sffArr;

	SuffixArray(String text) {
		int n = text.length();
		sffArr = new Suffix[n];
		for (int i = 0; i < n; i++) {
			sffArr[i] = new Suffix(text.substring(i), i);
		}
		Arrays.sort(sffArr);
	}

	public static class Suffix implements Comparable<Suffix> {

		String text;
		int index;

		Suffix(String text, int index) {
			this.text = text;
			this.index = index;
		}

		@Override
		public int compareTo(Suffix suffix) {
			Suffix s = (Suffix) suffix;
			int n = Math.min(this.text.length(), s.text.length());

			for (int i = 0; i < n; i++) {
				if (this.text.charAt(i) > s.text.charAt(i))
					return 1;
				else if (this.text.charAt(i) < s.text.charAt(i))
					return -1;
			}

			return this.text.length() - s.text.length();
		}

	}

	public int lcp(int i) {
		if(i == 0){
			return 0;
		}
		return computeLcp(sffArr[i - 1], sffArr[i]);
	}

	public int computeLcp(Suffix prev, Suffix curr) {
		int n = Math.min(prev.text.length(), curr.text.length());

		for (int i = 0; i < n; i++) {
			if (prev.text.charAt(i) != curr.text.charAt(i)) {
				return i;
			}
		}
		return n;
	}

	public static void main(String[] args) {
		String s = "ABRACADABRA!";
		SuffixArray sArr = new SuffixArray(s);
		Suffix[] sffArr = sArr.sffArr;
		for (int i = 0; i < sffArr.length; i++) {
			System.out.println(sffArr[i].text + " " + sffArr[i].index + " " + sArr.lcp(i));
		}

	}
}
