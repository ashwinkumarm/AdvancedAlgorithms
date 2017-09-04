package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.sp2_q1_SetFunctions;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class SetFunctions {

	public static <T extends Comparable<? super T>> void intersect(List<T> l1, List<T> l2, List<T> outList) {
		Collections.sort(l1);
		Collections.sort(l2);
		int i = 0, j = 0;
		while (i < l1.size() && j < l2.size()) {
			T data1 = l1.get(i);
			T data2 = l2.get(j);
			if (data1.compareTo(data2) > 0)
				j++;
			else if (data1.compareTo(data2) < 0)
				i++;
			else {
				outList.add(data1);
				i++;
				j++;
			}
		}
	}

	public static <T extends Comparable<? super T>> void union(List<T> l1, List<T> l2, List<T> outList) {
		Collections.sort(l1);
		Collections.sort(l2);
		int i = 0, j = 0;
		while (i < l1.size() && j < l2.size()) {
			T data1 = l1.get(i);
			T data2 = l2.get(j);
			if (data1.compareTo(data2) > 0) {
				outList.add(data2);
				j++;
			} else if (data1.compareTo(data2) < 0) {
				outList.add(data1);
				i++;
			} else {
				outList.add(data1);
				i++;
				j++;
			}
		}
		while (i < l1.size())
			outList.add(l1.get(i++));
		while (j < l2.size())
			outList.add(l2.get(j++));
	}

	public static <T extends Comparable<? super T>> void difference(List<T> l1, List<T> l2, List<T> outList) {
		Collections.sort(l1);
		Collections.sort(l2);
		int i = 0, j = 0;
		while (i < l1.size() && j < l2.size()) {
			T data1 = l1.get(i);
			T data2 = l2.get(j);
			if (data1.compareTo(data2) > 0)
				j++;
			else if (data1.compareTo(data2) < 0) {
				outList.add(data1);
				i++;
			} else {
				i++;
				j++;
			}
		}
		while (i < l1.size()) {
			outList.add(l1.get(i));
			i++;
		}
	}

	public static void main(String args[]) {
		List<Integer> l1 = new LinkedList<Integer>();
		l1.add(10);
		l1.add(2);
		l1.add(7);
		l1.add(1);
		l1.add(3);

		List<Integer> l2 = new LinkedList<Integer>();
		l2.add(11);
		l2.add(2);
		l2.add(8);
		l2.add(4);
		l2.add(3);

		List<Integer> outList = new LinkedList<Integer>();
		intersect(l1, l2, outList);
		ListIterator<Integer> itr3 = outList.listIterator();
		while (itr3.hasNext()) {
			System.out.print(itr3.next() + " ");
		}

		outList = new LinkedList<Integer>();
		union(l1, l2, outList);
		itr3 = outList.listIterator();
		System.out.println();
		while (itr3.hasNext()) {
			System.out.print(itr3.next() + " ");
		}

		outList = new LinkedList<Integer>();
		difference(l1, l2, outList);
		itr3 = outList.listIterator();
		System.out.println();
		while (itr3.hasNext()) {
			System.out.print(itr3.next() + " ");
		}

	}

}
