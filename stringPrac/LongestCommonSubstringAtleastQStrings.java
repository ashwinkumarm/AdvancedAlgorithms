package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.stringPrac;

import java.util.Arrays;
import java.util.HashSet;

import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.stringPrac.LongestCommonSubstringAtleastQStrings.SuffixArrayLCS.SuffixLCS;



public class LongestCommonSubstringAtleastQStrings {

	// should use SuffixArr class and add new parameters not create class like this here. 
	public static class SuffixArrayLCS {

		SuffixLCS[] sffArr;

		SuffixArrayLCS(String text) {
			int n = text.length();
			sffArr = new SuffixLCS[n];
			int s = 33;
			for (int i = 0; i < n; i++) {
				sffArr[i] = new SuffixLCS(text.substring(i), i, s);
				if (text.charAt(i) == (char) s) {
					s++;
				}

			}
			Arrays.sort(sffArr);
		}

		public static class SuffixLCS implements Comparable<SuffixLCS> {

			String text;
			int index;
			int sentinel;

			SuffixLCS(String text, int index, int sentinel) {
				this.text = text;
				this.index = index;
				this.sentinel = sentinel;
			}

			@Override
			public int compareTo(SuffixLCS suffix) {
				SuffixLCS s = (SuffixLCS) suffix;
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
			if (i == 0) {
				return 0;
			}
			return computeLcp(sffArr[i - 1], sffArr[i]);
		}

		public int computeLcp(SuffixLCS prev, SuffixLCS curr) {
			int n = Math.min(prev.text.length(), curr.text.length());

			for (int i = 0; i < n; i++) {
				if (prev.text.charAt(i) != curr.text.charAt(i)) {
					return i;
				}
			}
			return n;
		}
	}

	public String lcs(SuffixArrayLCS sffArr, int q) {

		SuffixLCS[] sffArray = sffArr.sffArr;
		// should think of better way
		HashSet<Integer> set = new HashSet<Integer>();

		int maxLcsLength = 0;
		String lcs = "";
		int l = 0, r = l + 1;
		set.add(sffArray[l].sentinel);
		while (r < sffArray.length) {
			if (set.contains(sffArray[r].sentinel)) {
				set = new HashSet<>();
				l++;
				r = l + 1;
				set.add(sffArray[l].sentinel);
			} else {
				set.add(sffArray[r].sentinel);
				r++;
			}

			if (set.size() == q) {
				int min = Integer.MAX_VALUE;
				for (int s = 0; s < q; s++) {
					min = Math.min(min, sffArr.lcp(l + s));
				}
				if (maxLcsLength < min) {
					maxLcsLength = min;
					lcs = sffArray[l].text.substring(0, min );
				}
			}

		}
		return lcs;
	}

	public String longestCommonSubstring(String[] strArr, int q) {
		StringBuilder joinedString = new StringBuilder();
		int s = 33;
		for (int i = 0; i < strArr.length - 1; i++) {
			joinedString.append(strArr[i]).append((char) s);
			s++;
		}
		joinedString.append(strArr[strArr.length - 1]).append((char) s);

		SuffixArrayLCS sffArr = new SuffixArrayLCS(joinedString.toString());
		/*SuffixLCS[] sffArray = sffArr.sffArr;
		for (int i = 0; i < sffArray.length; i++) {
			System.out.println(sffArray[i].text + " " + sffArr.lcp(i) +" "+sffArray[i].sentinel);
		}*/

		return lcs(sffArr, q);
	}

	public static void main(String[] args) {
		String[] strArr = { "Blue", "BlueBerry", "Rice", "Wheat", "WhiteRice", "WhiteColor", "BlackColor", "RedColor" , "BlueColor"};
		LongestCommonSubstringAtleastQStrings l = new LongestCommonSubstringAtleastQStrings();
		System.out.println(l.longestCommonSubstring(strArr,3));
	}
}
