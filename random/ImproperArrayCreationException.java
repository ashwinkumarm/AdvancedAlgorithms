package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.random;

public class ImproperArrayCreationException extends Exception{

	private static final long serialVersionUID = 1L;
	
	String msg;
	
	ImproperArrayCreationException(String msg) {
		this.msg = msg;
	}
	
	@Override
	public String toString(){
		return "Exception Message : " + msg;
	}

}
