package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.lp4;

import java.util.List;

import cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.utilities.Graph.Vertex;

public class RewardPath implements Comparable<RewardPath>{

	
	List<Vertex> path;
	int totalRewards;
	
	public int getTotalRewards() {
		return totalRewards;
	}

	public void setTotalRewards(int totalRewards) {
		this.totalRewards = totalRewards;
	}


	@Override
	public int compareTo(RewardPath o) {
		// TODO Auto-generated method stub
		if(getTotalRewards() == o.totalRewards ){
			return 0;
		}
		else if(getTotalRewards() > o.totalRewards ){
			return 1;
		}
		else{
			return -1;
		}
		
	}

}
