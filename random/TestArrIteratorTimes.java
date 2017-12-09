package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.random;

public class TestArrIteratorTimes {
	
	public static void main(String[] args){
//		Integer[] inp = new Integer[]{1,2,3,4};
//		Integer[] inp = {3,8,0,9,2,12};
//		Integer[] inp = {};
		Integer[] inp = {-1, 8 , 0, 0, 1, 1};
		ArrayIteratorTimes<Integer> it = new ArrayIteratorTimes<Integer>(inp);
//	    Object[] inp = {'a', 8, 0,1};	
//		ArrayIteratorTimes it = new ArrayIteratorTimes(inp);
		
		while(it.hasNext()){
			System.out.println(it.next());
		}
	}

}
