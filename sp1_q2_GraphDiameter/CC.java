package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp1_q2_GraphDiameter;

//import java.io.File;
//import java.io.FileNotFoundException;
//import java.util.Scanner;


public class CC { 
	class CCVertex {
		Graph.Vertex element;
		boolean seen;
		Graph.Vertex parent;
		CCVertex(Graph.Vertex u) {
		    element = u;
		    seen = false;
		    parent = null;
		}
    }
    // Algorithm uses a parallel array for storing information about vertices
    CCVertex[] ccVertex;
    Graph g;

    public CC(Graph g) {
	this.g = g;
	ccVertex = new CCVertex[g.size()];
	for(Graph.Vertex u: g) 
		{ ccVertex[u.name] = new CCVertex(u); }
    }

    // Main algorithm for finding the number of connected components of g using DFS
//    int findCC() {
//	int cno = 0;
//	for(Graph.Vertex u: g) {
//	    if(!seen(u)) {
//		cno++;
//		dfsVisit(u, cno);
//	    }
//	}
//	return cno;
//    }
//
//    void dfsVisit(Graph.Vertex u, int cno) {
//	visit(u, cno);
//	for(Graph.Edge e: u) {
//	    Graph.Vertex v = e.otherEnd(u);
//	    if(!seen(v)) {
//		dfsVisit(v, cno);
//	    }
//	}
//    }

    boolean seen(Graph.Vertex u) {
	CCVertex ccu = getCCVertex(u);
	return ccu.seen;
    }

    // Visit a node by marking it as seen and assigning it a component no
    void visit(Graph.Vertex u, int cno) {
	CCVertex ccu = getCCVertex(u);
	ccu.seen = true;
	//ccu.parent = cno;
    }

    // From Vertex to CCVertex (ugly)
    CCVertex getCCVertex(Graph.Vertex u) {
	return ccVertex[u.name];
    }

    // From CCVertex to Vertex
    Graph.Vertex getVertex(CCVertex c) {
	return c.element;
    }



//    public static void main(String[] args) throws FileNotFoundException {
//	int evens = 0;
//	Scanner in;
//        if (args.length > 0) {
//            File inputFile = new File(args[0]);
//            in = new Scanner(inputFile);
//        } else {
//            in = new Scanner(System.in);
//        }
//	Graph g = Graph.readGraph(in);
//	CC cc = new CC(g);
////	int nc = cc.findCC();
////
////	System.out.println("Input Graph has " + nc + " components:");
////	for(Graph.Vertex u: g) {
////	    System.out.print(u + " [ " + cc.getCCVertex(u).cno + " ] :");
////	    for(Graph.Edge e: u.adj) {
////		Graph.Vertex v = e.otherEnd(u);
////		System.out.print(e + " ");
////	    }
////	    System.out.println();
////	}
//    }
	
	

}
