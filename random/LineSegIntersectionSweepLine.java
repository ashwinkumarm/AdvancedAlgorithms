package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.random;

import java.util.ArrayList;
import java.util.PriorityQueue;

import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp7_q1_BST.BST;
import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.BinaryTree.Entry;

public class LineSegIntersectionSweepLine {

	public class PointDescription implements Comparable<PointDescription> {

		int leftX;
		LineSegment l;

		PointDescription(int leftX, LineSegment l) {
			this.leftX = leftX;
			this.l = l;
		}

		public int compareTo(PointDescription pd) {
			return this.leftX - pd.leftX;
		}
	}

	public static class LineSegment implements Comparable<LineSegment> {
		Points p1;
		Points p2;

		LineSegment(Points p1, Points p2) {
			this.p1 = p1;
			this.p2 = p2;
		}

		boolean isVertical() {
			return p1.x == p2.x;
		}

		boolean leftPoint(Points p) {
			return this.p1 == p;
		}

		@Override
		public int compareTo(LineSegment o) {
			return this.p1.y - o.p1.y;
		}

	}

	public static class Points {
		int x;
		int y;

		Points(int x, int y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append("(").append(x).append(",").append(y).append(")");
			return sb.toString();
		}
	}

	public ArrayList<LineSegment> rangeSearch(BST<LineSegment> bst, PointDescription pd) {

		ArrayList<LineSegment> points = new ArrayList<LineSegment>();
		rangeSearch(bst.root, pd.l.p1.y, pd.l.p2.y, points);

		return points;
	}

	private void rangeSearch(Entry<LineSegment> node, int k1, int k2, ArrayList<LineSegment> points) {

		if (node == null) {
			return;
		}

		if (node.element.p1.y > k1) {
			rangeSearch(node.left, k1, k2, points);
		}

		if (node.element.p1.y >= k1 && node.element.p1.y < k2) {
			points.add(node.element);
		}

		if (node.element.p1.y < k2) {
			rangeSearch(node.right, k1, k2, points);
		}
	}

	public void sweepLine(LineSegment[] lSegments) {
		PriorityQueue<PointDescription> pq = new PriorityQueue<>();

		for (LineSegment l : lSegments) {
			if (l.isVertical())
				pq.add(new PointDescription(l.p1.x, l));
			else {
				pq.add(new PointDescription(l.p1.x, l));
				pq.add(new PointDescription(l.p2.x, l));
			}
		}

		BST<LineSegment> bst = new BST<>();
		while (!pq.isEmpty()) {
			PointDescription pd = pq.poll();

			if (pd.l.isVertical()) {
				ArrayList<LineSegment> intersectingLines = rangeSearch(bst, pd);
				for (LineSegment l : intersectingLines) {
					System.out.println("LineSegment Pairs: " + "Horizontal Line : " + l.p1 + " , " + l.p2
							+ " Vertical Line Segment" + pd.l.p1 + " , " + pd.l.p2);
				}

			} else if (pd.l.p1.x == pd.leftX) {
				bst.add(pd.l);
			} else {
				bst.remove(pd.l);
			}

		}
	}

	public static void main(String[] args) {
		LineSegment l1 = new LineSegment(new Points(1, 4), new Points(3, 4));
		LineSegment l2 = new LineSegment(new Points(4, 4), new Points(6, 6));
		LineSegment l3 = new LineSegment(new Points(5, 1), new Points(5, 3));
		LineSegment l4 = new LineSegment(new Points(3, 3), new Points(4, 3));
		LineSegment l5 = new LineSegment(new Points(2, 2), new Points(6, 2));

		LineSegment[] lSegments = { l1, l2, l3, l4, l5 };
		LineSegIntersectionSweepLine intersectionPoints = new LineSegIntersectionSweepLine();
		intersectionPoints.sweepLine(lSegments);
	}

}
