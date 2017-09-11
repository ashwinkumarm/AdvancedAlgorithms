package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp3_q4_IsADAG;

import java.util.LinkedList;

public class DFSCheckDAG {

	static int time = 0;
	static Vertexx vertices[] = new Vertexx[8];

	public static void main(String[] args) {
		for (int i = 0; i < 8; i++) {
			vertices[i] = new Vertexx(i);
		}

		vertices[0].adjList.add(new Edgee(1));
		vertices[0].adjList.add(new Edgee(4));
		vertices[2].adjList.add(new Edgee(3));
		vertices[1].adjList.add(new Edgee(2));
		vertices[1].adjList.add(new Edgee(4));
		vertices[3].adjList.add(new Edgee(1));
		vertices[4].adjList.add(new Edgee(3));
		vertices[5].adjList.add(new Edgee(4));
		vertices[5].adjList.add(new Edgee(0));
		vertices[6].adjList.add(new Edgee(5));
		vertices[6].adjList.add(new Edgee(7));
		vertices[7].adjList.add(new Edgee(5));
		vertices[7].adjList.add(new Edgee(6));

		for (Vertexx vertex : vertices) {
			if (vertex.color == 0)
				dfs_visit(vertex);
			System.out.println();
		}
	}

	private static void dfs_visit(Vertexx vertex) {
		vertex.d = ++time;
		vertex.color = 1;
		for (Edgee edge : vertex.adjList) {
			if (vertices[edge.v].color == 0) {
				dfs_visit(vertices[edge.v]);
				edge.type = 1;
				vertices[edge.v].parent = vertex.nodeNum;
			} else if (vertices[edge.v].color == 1) {
				edge.type = 2;
				System.out.println("Cycle is present in the graph");
			} else if (vertex.d < vertices[edge.v].d)
				edge.type = 3;
			else
				edge.type = 4;
		}
		vertex.f = ++time;
		vertex.color = 2;
		System.out.print(vertex.nodeNum + " -> ");
	}

}

class Vertexx {
	LinkedList<Edgee> adjList = new LinkedList<Edgee>();
	int nodeNum;
	int color = 0; // 0 -> White 1 -> Gray 2 -> Black
	int parent = -1;
	int d;
	int f;

	public Vertexx(int nodeNum) {
		super();
		this.nodeNum = nodeNum;
	}
}

class Edgee {
	int v;
	int type = 0; // 0-unprocessed 1-tree edge 2-back edge 3-forward edge
					// 4-cross edge

	public Edgee(int v) {
		super();
		this.v = v;
	}
}