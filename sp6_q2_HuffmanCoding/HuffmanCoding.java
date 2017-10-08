package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp6_q2_HuffmanCoding;

import java.util.PriorityQueue;

class HuffmanTree implements Comparable<HuffmanTree> {
	public final int frequency;

	public HuffmanTree(int freq) {
		frequency = freq;
	}

	public int compareTo(HuffmanTree otherTree) {
		return this.frequency - otherTree.frequency;
	}
}

class HuffmanLeaf extends HuffmanTree {
	public final char value;

	public HuffmanLeaf(int freq, char val) {
		super(freq);
		value = val;
	}
}

class HuffmanNode extends HuffmanTree {
	public final HuffmanTree left, right;

	public HuffmanNode(HuffmanTree l, HuffmanTree r) {
		super(l.frequency + r.frequency);
		left = l;
		right = r;
	}
}

public class HuffmanCoding {

	public static HuffmanTree buildTree(char[] charArray, int[] charFreq) {
		PriorityQueue<HuffmanTree> pq = new PriorityQueue<HuffmanTree>();
		for (int i = 0; i < charArray.length; i++) {
			pq.add(new HuffmanLeaf(charFreq[i], charArray[i]));
		}

		if (pq.size() > 0) {
			while (pq.size() > 1) {
				HuffmanTree tree1 = pq.poll();
				HuffmanTree tree2 = pq.poll();
				pq.add(new HuffmanNode(tree1, tree2));

			}
		}
		return pq.poll();
	}

	public static void printCodes(HuffmanTree tree, StringBuilder code) {
		if (tree != null) {
			if (tree instanceof HuffmanLeaf) {
				HuffmanLeaf leaf = (HuffmanLeaf) tree;
				System.out.println(leaf.value + " " + leaf.frequency + " " + code.toString());
			} else if (tree instanceof HuffmanNode) {
				HuffmanNode node = (HuffmanNode) tree;
				code.append('0');
				printCodes(node.left, code);
				code.deleteCharAt(code.length() - 1);
				code.append('1');
				printCodes(node.right, code);
				code.deleteCharAt(code.length() - 1);
			}
		}
	}

	public static void main(String[] args) {
		char[] charArray = { 'a', 'b', 'c', 'd', 'e' };
		int[] charFreq = { 15, 5, 10, 25, 20 };
		HuffmanTree tree = buildTree(charArray, charFreq);
		printCodes(tree, new StringBuilder());
	}

}
