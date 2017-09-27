package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp5_q1_QuickSortPartitions;

public class QuickSortPartitions  {


	
	public int partition1(int[] A, int p, int r){
		return 0;
	}
	
	public int partition2(int[] A, int p, int r){
		return 0;
	}
	
	public void quickSortWithPartition1(int[] A, int p, int r){
		int q = partition1(A, p, r);
		quickSortWithPartition1(A, p, q - 1);
		quickSortWithPartition1(A, q + 1, r);
	}
	
	public void quickSortWithPartition2(int[] A, int p, int r){
		int q = partition2(A, p, r);
		quickSortWithPartition1(A, p, q - 1);
		quickSortWithPartition1(A, q, r);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
