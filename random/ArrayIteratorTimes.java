package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.random;

import java.util.Iterator;

public class ArrayIteratorTimes<Integer> implements Iterator<Integer> {

	Integer[] arr;
	int start, end, times, cursor;

	ArrayIteratorTimes(Integer[] arr) {
		this.arr = arr;
		start = 0;
		end = arr.length - 1;
		times = 0;
		cursor = 0;
	}

	@Override
	public boolean hasNext() {
		try {
			if (arr.length == 0 || arr.length % 2 != 0) {
				throw new ImproperArrayCreationException("Check the array size");
			}
			if (times == 0 && start == 0) {
				times = (int) arr[cursor];
				start++;
			}
			while (times <= 0) {
				cursor = cursor += 2;
				if (cursor > end) {
					return false;
				}
				times = (int) arr[cursor];
			}
			times--;

		} catch (ImproperArrayCreationException e) {
			System.out.println(e.msg);
			return false;
		}catch (ClassCastException e){
			System.out.println("input parameters are not of the type integers");
			return false;
		}
		return true;
	}

	@Override
	public Integer next() {
		return arr[cursor + 1];
	}

	public static void main(String[] args) {

//		Object[] inp = {3,8,0,9,2,12};
//		Object[] inp = {};
//		Object[] inp = {-1, 8 , 0, 0, 1, 1};
		Object[] inp = {'a', 8, 0,1};
		
//		ArrayIteratorTimes<Integer> it = new ArrayIteratorTimes<Integer>(inp);
//
//		while (it.hasNext()) {
//			System.out.println(it.next());
//		}
	}
}


