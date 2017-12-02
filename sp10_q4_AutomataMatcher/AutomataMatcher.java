package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp10_q4_AutomataMatcher;

public class AutomataMatcher {
	
	static int m = 256;
	
	public static int getState(int state, char j, char[] patternArr) {

		if (state < patternArr.length && patternArr[state] == j) {
			return state + 1;
		}

		int i;
		for (int s = state; s > 0; s--) {
			if (patternArr[s - 1] == j) {
				for (i = 0; i < s - 1; i++) {
					if (patternArr[i] != patternArr[state - s + 1 + i]) {
						break;
					}
				}
				if (i == s - 1) {
					return s;
				}

			}
		}
		return 0;
	}
	public static int[][] computeTransitionMatrix(char[] patternArr){
		
		int[][] transitionMatrix= new int[patternArr.length+1][m];
		
		for(int i = 0; i <= patternArr.length; i++){
			for(int j = 0; j< m; j++){
				transitionMatrix[i][j] = getState(i,(char) j, patternArr);
			}
		}
		return transitionMatrix;
	}

	public static void automataMatcher(String text, String pattern){
		char[] textArr = text.toCharArray();
		char[] patternArr = pattern.toCharArray();
		
		int[][] transitionMatrix = computeTransitionMatrix(patternArr);
		
		int state = 0;
		for(int i = 0; i< textArr.length; i++){
			state = transitionMatrix[state][textArr[i]];
			
			if(state == patternArr.length){
				System.out.println("Found at index " + (i-patternArr.length+1));
			}
		}
	}
	
	public static void main(String[] args){
		String text = "AABAACAADAABABACAAA";
		String pattern = "ABABACA";
		automataMatcher(text, pattern);
	}

}
