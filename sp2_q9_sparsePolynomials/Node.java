package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp2_q9_sparsePolynomials;

public class Node implements Comparable<Node>{

	int coeff;
	int exponent;
    
	Node(int coeff, int exponent){
		this.coeff = coeff;
		this.exponent = exponent;
	}
	public int getCoeff() {
		return coeff;
	}

	public void setCoeff(int coeff) {
		this.coeff = coeff;
	}

	public int getExponent() {
		return exponent;
	}

	public void setExponent(int exponent) {
		this.exponent = exponent;
	}

	@Override
	public int compareTo(Node n) {
		return this.exponent - n.exponent;
	}
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		return sb.append(Integer.toString(coeff)).append("X^").append(Integer.toString(exponent)).toString();
	}
}
