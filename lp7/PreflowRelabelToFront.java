package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.lp7;

import java.util.Iterator;
import java.util.LinkedList;

import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.lp7.ResidualGraph.ResidueEdge;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.lp7.ResidualGraph.ResidueVertex;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Graph.Edge;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Graph.Vertex;

public class PreflowRelabelToFront {

	ResidualGraph gf;
	Vertex s, t;
	ResidueVertex rs, rt;

	public PreflowRelabelToFront(ResidualGraph gf, Vertex s, Vertex t) {
		super();
		this.gf = gf;
		this.s = s;
		this.rs = gf.getResidueVertexWithName(s.getName());
		this.t = t;
		this.rt = gf.getResidueVertexWithName(t.getName());
	}

	private void initialize() {
			for(Vertex u:gf){
	ResidueVertex ru= (ResidueVertex) u;
			ru.height = 0;
			ru.excess = 0;
			}
			for(Vertex u:gf)
				for(Edge e:u)
					((ResidueEdge)e).flow = 0;
			rs.height = gf.size();
			for edge e = ( s, u ) out of s do
			f( e ) = c( e )
			s.excess = s.excess - c( e );
			u.excess = u.excess + c( e );
	}

	private void push ( u, v, e ){
		// Push flow out of u using e
		// edge ( u, v ) is in G f , and e = ( u, v ) or ( v, u )
		delta = min( u.excess,cf ( e ) )
		if e.from = u then f( e ) = f( e ) + delta
		else f( e ) = f( e ) - delta
		u.excess = u.excess - delta
		v.excess = v.excess + delta
	}

	private boolean inGf(Vertex u, ResidueEdge e ){ // edge out of u in G f because of e?
		return e.from == u ? e.flow < c ( e ) : f( e ) > 0
	}

	private void relabel ( u ){ // increase the height of u, to allow u to get rid of its excess
		// Precondition: u.excess > 0, and for all ( u, v ) in G f , u.height <= v.height
		u.height = 1 + min (v.height : ( u, v ) in  Gf)
			}

	private void discharge(ResidueVertex u) { // push all excess flow out of u,
												// raising its height, as needed
		while (u.excess > 0) {
			for (ResidueEdge e : u.residueRevadj) {
				ResidueVertex v = (ResidueVertex) e.otherEnd(u);
				if (inGf(u, e) && u.height == 1 + v.height) {
					push(u, v, e);
					if (u.excess == 0)
						return;
				}
			}
			relabel(u);
		}
	}

	private void relabelToFront() {
		initialize();
		// Create an list L of nodes in V - { s, t }, in any order
		LinkedList<ResidueVertex> L = new LinkedList<>();
		for (Vertex u : gf)
			if (u != rs && u != rt)
				L.add((ResidueVertex) u);
		boolean done = false;
		ResidueVertex ru;
		while (!done) {
			Iterator<ResidueVertex> it = L.iterator();
			done = true;
			while (it.hasNext()) {
				ru = it.next();
				if (ru.excess == 0)
					continue;
				int oldHeight = ru.height;
				discharge(ru);
				if (ru.height != oldHeight) {
					done = false;
					break;
				}
			}
			if (!done) {
				// Move u to beginning of L
				it.remove();
				L.addFirst(ru);// ??
			}
		}
	}
}
