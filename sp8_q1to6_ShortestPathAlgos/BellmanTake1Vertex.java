package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp8_q1to6_ShortestPathAlgos;

import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Graph.Vertex;

public class BellmanTake1Vertex extends ShortestPathVertex {
	int d[];

	public BellmanTake1Vertex(Vertex vertex, int index, int distance, int size) {
		super(vertex, index, distance);
		d  =new int[size];
		d[0] = distance;
	}

}
