package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp8_q1to6_ShortestPathAlgos;


/**
 * This class stores the Vertex used in Shorttest Paths algorithm
 * 
 * @author Ashwin, Arun, Deepak, Haritha
 *
 */
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp6_q4and6_PrimAlgo2.Index;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Graph;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Graph.Vertex;

public class ShortestPathVertex implements Index {
	
	Graph.Vertex vertex;
	int distance, index, count;
	boolean seen;
	Graph.Vertex parent;
	
	/**
	 * Constructor to initialize the variables
	 * 
	 * @param vertex
	 * @param distance
	 * @param index
	 */
	public ShortestPathVertex(Vertex vertex, int index,  int distance) {
	
		this.vertex = vertex;
		this.distance = distance;
		this.seen = false;
		this.parent = null;
		this.index = index;
		this.count = 0;
		
	}
	
	void reinitializeVertex(int distance, int index) {
			this.seen = false;
			this.parent = null;
			this.distance = distance;
			this.index = index;
			this.count = 0;
	}

	
	public void putIndex(int i) {
		index = i;
	}

	public int getIndex() {
		return index;
	}
	
	
}
