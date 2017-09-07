package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp2_q1_SetFunctions;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class SetFunctions {
	public static <T> T next(ListIterator<T> itr) {
		return itr.hasNext() ? itr.next() : null;
	}

	public static <T extends Comparable<? super T>> void intersect(List<T> l1, List<T> l2, List<T> outList) {

		ListIterator<T> itr1 = l1.listIterator();
		ListIterator<T> itr2 = l2.listIterator();
		T data1 = next(itr1);
		T data2 = next(itr2);
		while (data1 != null && data2 != null) {
			if (data1.compareTo(data2) > 0)
				data2 = next(itr2);
			else if (data1.compareTo(data2) < 0)
				data1 = next(itr1);
			else {
				outList.add(data1);
				data1 = next(itr1);
				data2 = next(itr2);
			}
		}
	}

	public static <T extends Comparable<? super T>> void union(List<T> l1, List<T> l2, List<T> outList) {

		ListIterator<T> itr1 = l1.listIterator();
		ListIterator<T> itr2 = l2.listIterator();
		T data1 = next(itr1);
		T data2 = next(itr2);
		while (data1 != null && data2 != null) {
			if (data1.compareTo(data2) > 0) {
				outList.add(data2);
				data2 = next(itr2);
			} else if (data1.compareTo(data2) < 0) {
				outList.add(data1);
				data1 = next(itr1);
			} else {
				outList.add(data1);
				data1 = next(itr1);
				data2 = next(itr2);
			}
		}
		
		while (data1 != null) {
			outList.add(data1);
			data1 = next(itr1);
		}
		while (data2 != null) {
			outList.add(data2);
			data2 = next(itr2);
		}
	}

	public static <T extends Comparable<? super T>> void difference(List<T> l1, List<T> l2, List<T> outList) {

		ListIterator<T> itr1 = l1.listIterator();
		ListIterator<T> itr2 = l2.listIterator();
		T data1 = next(itr1);
		T data2 = next(itr2);
		while (data1 != null && data2 != null) {
			if (data1.compareTo(data2) > 0)
				data2 = next(itr2);
			else if (data1.compareTo(data2) < 0) {
				outList.add(data1);
				data1 = next(itr1);
			} else {
				data1 = next(itr1);
				data2 = next(itr2);
			}
		}
		while (data1 != null) {
			outList.add(data1);
			data1 = next(itr1);
		}
	}

	public static void main(String args[]) {
		List<Integer> l1 = new LinkedList<Integer>();
		l1.add(1);
		l1.add(2);
		l1.add(3);
		l1.add(7);
		l1.add(10);

		List<Integer> l2 = new LinkedList<Integer>();
		l2.add(2);
		l2.add(3);
		l2.add(4);
		l2.add(8);
		l2.add(11);

		System.out.println("Elements in List1");
		for(int e :l1){
			System.out.print(e + " ");
		}
		System.out.println("\nElements in List2");
		for(int e :l2){
			System.out.print(e + " ");
		}
		List<Integer> outList = new LinkedList<Integer>();
		intersect(l1, l2, outList);
		ListIterator<Integer> itr3 = outList.listIterator();
		System.out.println("\nIntersection: ");
		while (itr3.hasNext()) {
			System.out.print(itr3.next() + " ");
		}

		outList = new LinkedList<Integer>();
		union(l1, l2, outList);
		itr3 = outList.listIterator();
		System.out.println("\nUnion: ");
		while (itr3.hasNext()) {
			System.out.print(itr3.next() + " ");
		}

		outList = new LinkedList<Integer>();
		difference(l1, l2, outList);
		itr3 = outList.listIterator();
		System.out.println("\nDifference(l1-l2): ");
		while (itr3.hasNext()) {
			System.out.print(itr3.next() + " ");
		}

	}

}
