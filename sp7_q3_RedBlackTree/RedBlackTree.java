package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp7_q3_RedBlackTree;

import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp7_q1_BST.BinarySearchTree;

public class RedBlackTree<T extends Comparable<? super T>> extends BinarySearchTree<T> {

	static public class Node<T> extends Entry<T> {

		public Node<T> parent;
		NodeColor color;

		public Node(T x, Node<T> left, Node<T> right, Node<T> parent, NodeColor color) {
			super(x, left, right);
			this.parent = parent;
			this.color = color;
		}
	}

	public RedBlackTree() {
		root = null;
		size = 0;
	}

	public Node<T> add(T x) {
		Node<T> root = null;
		Node<T> newNode = (Node<T>) super.add(x);
		if (newNode != null) {
			newNode.color = NodeColor.RED;
			root = repair(newNode);
			if (root.color == NodeColor.RED) {
				root.color = NodeColor.BLACK;
			}
		}
		return root;
	}

	public Node<T> repair(Node<T> t) {
		
		
		
		while(t.color == NodeColor.RED){
			
			if(t == root){
				return t;
			}
			
			Node<T> g_t = null;
			Node<T> p_t = null;
			Node<T> u_t = null;
			Node<T> s_t = null;
			
			p_t = (Node<T>)stack.pop();
			
			if(p_t == null || p_t.color == NodeColor.BLACK){
				return p_t;
			}
			
			g_t = (Node<T>)stack.pop();
			
			if(p_t.element.compareTo(g_t.element) < 0){
				u_t = (Node<T>)g_t.right;
			} 
			else{
				u_t = (Node<T>)g_t.left;
			}
			
			if(t.element.compareTo(p_t.element)<0){
				s_t = (Node<T>)p_t.right;
			} 
			else{
				s_t = (Node<T>)p_t.left;
			}
			
		}
		
		return null;
	}


}
