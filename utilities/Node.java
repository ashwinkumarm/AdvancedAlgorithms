package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities;

public class Node implements Comparable<Node> {
	

	int element;
	int nextIndex;
	int arrayIndex;
	int size;
	
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

	public void setElement(int element) {
		this.element = element;
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
