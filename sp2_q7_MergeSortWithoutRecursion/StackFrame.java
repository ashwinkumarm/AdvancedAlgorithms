package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp2_q7_MergeSortWithoutRecursion;

public class StackFrame {

	// Parameters
	int[] a, tmp;
	int p, q;

	public StackFrame(int[] a, int[] tmp, int p, int q) {
		super();
		this.p = p;
		this.q = q;
		this.a = a;
		this.tmp = tmp;
	}
}
