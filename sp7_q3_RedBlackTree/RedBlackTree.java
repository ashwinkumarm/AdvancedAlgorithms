package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp7_q3_RedBlackTree;

import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp7_q1_BST.BinarySearchTree;

public class RedBlackTree<T extends Comparable<? super T>> extends BinarySearchTree<T> {

	static public class Node<T> extends Entry<T> {

		NodeColor color;

		public Node(T x, Node<T> left, Node<T> right, NodeColor color) {
			super(x, left, right);
			this.color = color;
		}
		public void setColor( NodeColor color){
			this.color = color;
		}
		public NodeColor getColor(){
			return this.color;
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
			newNode.setColor(NodeColor.RED);
			root = repair(newNode);
			if (root.getColor() == NodeColor.RED) {
				root.setColor( NodeColor.BLACK);
			}
		}
		return root;
	}

	public Node<T> repair(Node<T> t) {
		
		while(t.getColor() == NodeColor.RED){
			
			if(t == root){
				return t;
			}
			
			Node<T> g_t = null;
			Node<T> p_t = null;
			Node<T> u_t = null;

			
			p_t = (Node<T>)stack.pop();
			
			if(p_t == root || p_t.getColor() == NodeColor.BLACK){
				return p_t;
			}
			
			g_t = (Node<T>)stack.pop();
			
			if(p_t.element.compareTo(g_t.element) < 0){
				u_t = (Node<T>)g_t.right;
			} 
			else{
				u_t = (Node<T>)g_t.left;
			}
			
			
			if(u_t.getColor() == NodeColor.RED){
				p_t.setColor(NodeColor.BLACK);
				u_t.setColor(NodeColor.BLACK);
				g_t.setColor(NodeColor.RED);
				t = g_t;
			}
			
			else if(u_t.getColor() == NodeColor.BLACK){
				if(g_t.left == p_t && p_t.left == t){ //LL case
					singleRightRotate(g_t, p_t);
				}
				else if(g_t.right == p_t && p_t.right == t){ //RR case
					singleLeftRotate(g_t, p_t);
				}
				else if(g_t.left == p_t && p_t.right == t){ //LR case
					doubleRotateLeftRight(g_t, p_t, t);
					
				}
				else if(g_t.right == p_t && p_t.left == t){ // RL case
					doubleRotateRightLeft(g_t, p_t, t);
				}
				break;
			}
			
		}
		return (Node<T>)root;
	}
	
	public void singleRightRotate(Node<T> g_t, Node<T> p_t){ //LL Case
		g_t.left = p_t.right;
		p_t.right = g_t;
		p_t.setColor(NodeColor.BLACK);
		g_t.setColor(NodeColor.RED);
		if(g_t == root) {
			root = p_t;
		}
	}
	
	public void singleLeftRotate(Node<T> g_t, Node<T> p_t){ //RR Case
		g_t.right = p_t.left;
		p_t.left = g_t;
		p_t.setColor(NodeColor.BLACK);
		g_t.setColor(NodeColor.RED);
		if(g_t == root) {
			root = p_t;
		}
	}
	
	public void doubleRotateLeftRight(Node<T> g_t, Node<T> p_t, Node<T> t){ //RL Case
		singleLeftRotate(p_t, t);
		singleRightRotate(g_t, t);
	}
	
	public void doubleRotateRightLeft(Node<T> g_t, Node<T> p_t, Node<T> t){ // LR Case
		singleRightRotate(p_t, t);
		singleLeftRotate(g_t, t);
	}
	

}
