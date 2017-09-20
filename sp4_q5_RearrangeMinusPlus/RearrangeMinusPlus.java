package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp4_q5_RearrangeMinusPlus;

public class RearrangeMinusPlus {

	static void rearrangeMinusPlus(int[] arr) {
		rearrange(arr, 0, arr.length - 1);
	}

	private static void rearrange(int[] arr, int l, int r) {
		if (l < r) {
			int mid = (l + r) / 2;
			rearrange(arr, l, mid);
			rearrange(arr, mid + 1, r);
			merge(arr, l, mid, r);
		}
	}

	private static void merge(int[] arr, int l, int mid, int r) {
		int i = l, j = mid + 1;
		while (i <= mid && arr[i] < 0)
			i++;
		while (j <= r && arr[j] < 0)
			j++;
		//Inplace Reversal algorithm
		reverse(arr, i, mid);
		reverse(arr, mid + 1, j - 1);
		reverse(arr, i, j - 1);
	}

	private static void reverse(int[] arr, int l, int r) {
		if (l < r) {
			int tmp = arr[l];
			arr[l] = arr[r];
			arr[r] = tmp;
			l++;
			r--;
			reverse(arr, l, r);
		}
	}

	public static void main(String args[]) {
		int[] arr = { -4, 1, -3, 9, -1, 8, 10, 3, -2, -11 };
		rearrangeMinusPlus(arr);
		for(int i=0;i<arr.length;i++)
			System.out.print(arr[i]+" ");
	}

}
