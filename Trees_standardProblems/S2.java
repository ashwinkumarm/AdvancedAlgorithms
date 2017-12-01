package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.Trees_standardProblems;

import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.BinaryTree.Entry;

public class S2<T> {

	public Entry<T> constructBinaryTree(T[] inOrder, T[] postOrder) {

		return constructBinaryTree(inOrder, postOrder, 0, inOrder.length - 1, postOrder.length - 1);

	}

	public Entry<T> constructBinaryTree(T[] inOrder, T[] postOrder, int inS, int inE, int posE) {
		if (inS > inE) {
			return null;
		}

		Entry<T> node = new Entry<T>(postOrder[posE], null, null);

		if (inS == inE) {
			return node;
		}

		int index = 0;
		for (int i = inS; i < inE; i++) {
			if (postOrder[posE] == inOrder[i]) {
				index = i;
				break;
			}
		}
		node.right = constructBinaryTree(inOrder, postOrder, index + 1, inE, posE - 1);
		node.left = constructBinaryTree(inOrder, postOrder, inS, index - 1, posE - (inE - index) - 1);

		return node;
	}

	public static void main(String[] args) {
		Integer[] inOrder = { 4, 8, 2, 5, 1, 6, 3, 7 };
		Integer[] postOrder = { 8, 4, 5, 2, 6, 7, 3, 1 };

		S2<Integer> s = new S2<Integer>();
		s.constructBinaryTree(inOrder, postOrder);

	}

}
