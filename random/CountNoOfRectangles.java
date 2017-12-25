package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.random;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;


// runs in O(N^3) there could be algorithms with better performance
public class CountNoOfRectangles {

	public static class Point implements Comparable<Point> {
		int x;
		int y;

		Point(int x, int y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public int compareTo(Point o) {
			if (this.x < o.x)
				return -1;
			else if (this.x > o.x)
				return 1;
			else if (this.y < o.y)
				return -1;
			else if (this.y > o.y)
				return 1;
			return 0;
		}

		@Override
		public String toString() {
			return "(" + x + "," + y + ")";
		}
	}

	static int countRectangles(int[][] coordinates) {
		Point[] points = new Point[coordinates.length];

		int c = 0;
		TreeSet<Point> set = new TreeSet<Point>();
		for (int i = 0; i < coordinates.length; i++) {
			points[i] = new Point(coordinates[i][0], coordinates[i][1]);
			set.add(points[i]);
		}

		for (Point leftDown : set) {
			Set<Point> leftUpPoints = set.subSet(leftDown, false, new Point(Integer.MAX_VALUE, Integer.MAX_VALUE),
					false);
			for (Point leftUp : leftUpPoints) {
				Set<Point> rightDownPoints = new HashSet<Point>();
				if (leftDown.x < leftUp.x && leftDown.y == leftUp.y) {
					rightDownPoints.add(leftUp);
				}
				for (Point rightDown : rightDownPoints) {
					Point rightUp = new Point(rightDown.x, leftUp.y);
					if (set.contains(rightUp)) {
						c++;
					}
				}
			}

		}
		return c;
	}

	public static void main(String[] args) {
		int coordinates[][] = { { 1, 1 }, { 7, 1 }, { 1, 4 }, { 1, 5 }, { 7, 4 }, { 7, 5 } };
		System.out.println(countRectangles(coordinates));
	}
}
