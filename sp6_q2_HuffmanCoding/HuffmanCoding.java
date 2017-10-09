package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp6_q2_HuffmanCoding;

import java.util.PriorityQueue;

/**
 * This class implements the Huffman Coding Algorithm by building trees and
 * outputs the codewords for each symbol
 *
 * @author Ashwin, Arun, Deepak, Haritha
 *
 */
class HuffmanTree implements Comparable<HuffmanTree> {
	public final float frequency;

	/**
	 * This class manages leaf nodes of a tree with two parameters:
	 * character(symbol) and frequency associated with it
	 *
	 */
	static class LeafNode extends HuffmanTree {
		public final char value;

		public LeafNode(float freq, char val) {
			super(freq);
			value = val;
		}
	}

	/**
	 * This class manages with inner nodes of a tree with three parameters: left
	 * subtree pointer, right subtree pointer and frequency of the tree
	 *
	 */
	static class InnerNode extends HuffmanTree {
		public final HuffmanTree left, right;

		public InnerNode(HuffmanTree l, HuffmanTree r) {
			super(l.frequency + r.frequency);
			left = l;
			right = r;
		}
	}

	/**
	 * Constructor for HuffmanTree
	 * 
	 * @param freq
	 */
	public HuffmanTree(float freq) {
		frequency = freq;
	}

	public int compareTo(HuffmanTree otherTree) {
		return (int) (this.frequency - otherTree.frequency);
	}
}

public class HuffmanCoding {

	/**
	 * This method builds the Huffman Tree based on the frequencies of the character
	 * Takes two least frequency character trees and combines them
	 * 
	 * @param charArray
	 * @param charFreq
	 * @return
	 */
	public static HuffmanTree buildTree(char[] charArray, float[] charFreq) {
		PriorityQueue<HuffmanTree> pq = new PriorityQueue<HuffmanTree>();
		for (int i = 0; i < charArray.length; i++) {
			pq.add(new HuffmanTree.LeafNode(charFreq[i], charArray[i]));
		}

		if (pq.size() > 0) {
			while (pq.size() > 1) {
				HuffmanTree tree1 = pq.poll();
				HuffmanTree tree2 = pq.poll();
				pq.add(new HuffmanTree.InnerNode(tree1, tree2));

			}
		}
		return pq.poll();
	}

	/**
	 * This method prints the codes for each symbol by traversing the tree
	 * 
	 * @param tree
	 * @param code
	 */
	public static void printCodes(HuffmanTree tree, StringBuilder code) {
		if (tree != null) {
			if (tree instanceof HuffmanTree.LeafNode) {
				HuffmanTree.LeafNode leaf = (HuffmanTree.LeafNode) tree;
				System.out.println(leaf.value + "    " + leaf.frequency + "   " + code.toString());
			} else if (tree instanceof HuffmanTree.InnerNode) {
				HuffmanTree.InnerNode node = (HuffmanTree.InnerNode) tree;
				code.append('0');
				printCodes(node.left, code);
				code.deleteCharAt(code.length() - 1);
				code.append('1');
				printCodes(node.right, code);
				code.deleteCharAt(code.length() - 1);
			}
		}
	}

	/**
	 * Main method for testing
	 * @param args
	 */
	public static void main(String[] args) {
		char[] charArray = { 'a', 'b', 'c', 'd', 'e' };
		float[] charFreq = { 15.0F, 5.0F, 10.0F, 25.0F, 20.0F };
		HuffmanTree tree = buildTree(charArray, charFreq);
		System.out.println("Char" + " " + "Freq" + " " + "Code");
		printCodes(tree, new StringBuilder());
	}

}
