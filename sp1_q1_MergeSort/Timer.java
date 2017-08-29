package cs6301.g12.sp1_q1_MergeSort;

public class Timer {
	
	long startTime, endTime, elapsedTime;
	long memAvailable, memUsed;

    Timer() {
    		startTime = System.currentTimeMillis();
    }

    public void start() {
    		startTime = System.currentTimeMillis();
    }

    public Timer end() {
		endTime = System.currentTimeMillis();
		elapsedTime = endTime-startTime;
		memAvailable = Runtime.getRuntime().totalMemory();
		memUsed = memAvailable - Runtime.getRuntime().freeMemory();
		return this;
	 }

    public String toString() {
    	return "Time: " + elapsedTime + " msec.\n" + "Memory: " + (memUsed/1048576) + " MB / " + (memAvailable/1048576) + " MB.";
    }


}
