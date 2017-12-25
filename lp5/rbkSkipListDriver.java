package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.lp5;

//Driver program for skip list implementation.

public class rbkSkipListDriver {
	static boolean VERBOSE = false;

	public static void main(String[] args) {
		java.util.Scanner in = new java.util.Scanner(System.in);
		if (args.length > 0) {
			VERBOSE = true;
		}
		String operation = "";
		long operand = 0;
		int modValue = 999983;
		long result = 0;
		Long returnValue = null;
		SkipList<Long> skipList = new SkipList<>();
		int lineno = 0;

		while (!((operation = in.next()).equals("End"))) {
			lineno++;
			switch (operation) {
			case "Add":
				operand = in.nextLong();
				returnValue = skipList.add(operand) ? 1L : 0L;
				break;
			case "Ceiling":
				operand = in.nextLong();
				returnValue = skipList.ceiling(operand);
				break;
			case "Contains":
				operand = in.nextLong();
				returnValue = skipList.contains(operand) ? 1L : 0L;
				break;
			case "First":
				returnValue = skipList.first();
				break;
			case "Floor":
				operand = in.nextLong();
				returnValue = skipList.floor(operand);
				break;
			case "Get":
				int intOperand = in.nextInt();
				returnValue = skipList.get(intOperand);
				break;
			case "Last":
				returnValue = skipList.last();
				break;
			case "Remove":
				operand = in.nextLong();
				returnValue = skipList.remove(operand) != null ? 1L : 0L;
				break;
			default:
				System.out.println("Unknown operation: " + operation);
				returnValue = null;
			}
			if (returnValue != null) {
				result = (result + returnValue) % modValue;
			}
			if (VERBOSE) {
				System.out.println(lineno + " " + operation + " : " + (returnValue == null ? 0 : returnValue));
			}
		}
		System.out.println(result);
	}
}
