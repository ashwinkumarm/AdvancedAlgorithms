package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp2_q7_MergeSortWithoutRecursion;

public class StackFrame {

	// Parameters
	boolean childrenAdded;
	int p, r;

	public StackFrame(boolean childrenAdded, int p, int r) {
		super();
		this.childrenAdded = childrenAdded;
		this.p = p;
		this.r = r;
	}

	@Override
	public String toString() {
		return "StackFrame [childrenAdded=" + childrenAdded + ", p=" + p + ", r=" + r + "]";
	}


}
