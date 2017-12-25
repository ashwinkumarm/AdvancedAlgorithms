package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities;

/**
 * This class keeps track of the next element from every subarray.
 * 
 * @author Ashwin, Arun, Deepak, Haritha
 *
 */
public class Node implements Comparable<Node> {
	

	int element; // current value in the subarray
	int nextIndex; //index of the next element in the subarray
	int arrayIndex; // index of the subarray to which the element belongs
	int size; // number of element in the subarray
	
	public Node(int element, int nextIndex, int arrayIndex, int size){
		this.element = element;
		this.nextIndex = nextIndex;
		this.arrayIndex = arrayIndex;
		this.size = size;
	}
	
	public boolean hasNext(){
		return nextIndex < size;
	}

	public int getArrayIndex() {
		return arrayIndex;
	}

	public int getElement() {
		return element;
	}
	
	/**
	 * sets the next element in the sub array to element
	 * @param subA : int[][] : reference to sub arrays
	 */
	public void setElement(int subA[][]) {
		this.element = subA[getArrayIndex()][getNextIndex()];
	}
	
	public int getNextIndex() {
		return nextIndex;
	}

	public void setNextIndex(int nextIndex) {
		this.nextIndex = nextIndex;
	}

	@Override
	public int compareTo(Node n) {

	    if(getElement() == n.getElement())	{
	    	    return 0;
	    }
	    else if(getElement() > n.getElement()) {
	    	    return 1;
	    }
	    else {
	    	return -1;
	    } 
	}
}
