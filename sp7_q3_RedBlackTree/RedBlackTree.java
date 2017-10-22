package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp7_q3_RedBlackTree;
import java.util.Scanner;

import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp7_q1_BST.BinarySearchTree;


public class RedBlackTree<T extends Comparable<? super T>> extends BinarySearchTree<T> {
	static class Entry<T> extends BinarySearchTree.Entry<T> {
		boolean isRed;

		Entry(T x, Entry<T> left, Entry<T> right) {
			super(x, left, right);
			isRed = true;
		}
	}

	RedBlackTree() {
		super();
	}
	Entry<T> rootRBT;
	
	public Entry<T> insert(T x){
		Entry<T> newEntry = new Entry<T>(x,null,null);
		if(add(newEntry) && stack != null){
			Entry<T> parent = (Entry<T>) stack.peek();
			if(parent != null && parent.isRed){
				repair(newEntry, parent);
			}
		}
		
		rootRBT = (Entry<T>) root;
		rootRBT.isRed = false;
		return rootRBT;
	}
	
	public void repair(Entry<T> t, Entry<T> parent) {
		
		if (parent == rootRBT) {
			return;
		}

		Entry<T> prevGrandParent = null;
		
		while (t.isRed) {
			parent = (Entry<T>) stack.pop();
			if (t == rootRBT || parent == rootRBT || !parent.isRed) {
				return;
			}

			Entry<T> grandParent = (Entry<T>) stack.pop();
			prevGrandParent = new Entry<T>(grandParent.element, (Entry<T>)grandParent.left,(Entry<T>) grandParent.right);
			Entry<T> uncleOfT = (Entry<T>) ((Entry<T>) grandParent.right == parent ? grandParent.left
					: grandParent.right);

			if (uncleOfT != null && uncleOfT.isRed) {
				grandParent.isRed = true;
				parent.isRed = false;
				uncleOfT.isRed = false;
				t = grandParent;
				continue;
			}

			else if (uncleOfT == null || !uncleOfT.isRed) {
				if (t.isLeftChild && parent.isLeftChild) {
					grandParent  = rotateRight(grandParent);
					Entry<T>rightChild = (Entry<T>)grandParent.right;
					rightChild.isRed = false;
					grandParent.isRed = true;
					updateTree(prevGrandParent, grandParent);
					return;
				} else if (t.isRightChild && parent.isRightChild) {
					grandParent = rotateLeft(grandParent);
					Entry<T>leftChild = (Entry<T>)grandParent.left;
					leftChild.isRed = true;
					grandParent.isRed = false;
					updateTree(prevGrandParent, grandParent);
					return;
				} else if (t.isRightChild && parent.isLeftChild) {
					grandParent.left = rotateLeft(parent);
					Entry<T> currRoot = rotateRight(grandParent);
					Entry<T>rightChild = (Entry<T>)currRoot.right;
					currRoot.isRed = false;
					rightChild.isRed = true;
					updateTree(prevGrandParent, currRoot);
					return;
				} else if (t.isLeftChild && parent.isRightChild) {
					grandParent.right = rotateRight(parent);
					Entry<T> currRoot  = rotateLeft(grandParent);
					Entry<T>leftChild = (Entry<T>)currRoot.left;
					currRoot.isRed = false;
					leftChild.isRed = true;
					updateTree(prevGrandParent, currRoot);
					return;
				}
			}
		}
	}
	
	
	public void updateTree(Entry<T> prevGrandParent, Entry<T> currRoot){
		Entry<T> greatGp = (Entry<T>)stack.pop();
		if(greatGp!= null && prevGrandParent != null){
			if(greatGp.left.element.compareTo(prevGrandParent.element) == 0){
				greatGp.left = currRoot;
			}else{
				greatGp.right = currRoot;
			}
		}
	}
	public Entry<T> rotateRight(Entry<T> t){
		Entry<T> tLeft = (Entry<T>) t.left;
		t.left = tLeft.right;
		tLeft.right = t;
		if(t == root) {
			root = tLeft;
		}
		return tLeft;
	}

	public Entry<T> rotateLeft(Entry<T> t) {
		Entry<T> tRight = (Entry<T>) t.right;
		t.right = tRight.left;
		tRight.left = t;
		if(t == root) {
			root = tRight;
		}
		return tRight;
	}
	
	
	public static void main(String[] args){
		RedBlackTree<Integer> t = new RedBlackTree<Integer>();

		Scanner in = new Scanner(System.in);
		while (in.hasNext()) {
			int x = in.nextInt();
			if (x > 0) {
				System.out.print("Add " + x + " : ");
				t.insert(x);
				t.printTree();
			} else if (x < 0) {
				System.out.print("Remove " + x + " : ");
				//t.delete(-x);
				t.printTree();
			} else {
				Comparable[] arr = t.toArray();
				System.out.print("Final: ");
				for (int i = 0; i < t.size; i++) {
					System.out.print(arr[i] + " ");
				}
				System.out.println();
				in.close();
				return;
			}
		}
		in.close();
	}
}
