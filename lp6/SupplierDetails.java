package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.lp6;

import java.util.HashMap;

public class SupplierDetails {

	private Float reputation;
	private HashMap<Long, Integer> itemPriceMap;

	public SupplierDetails() {
		reputation = (float) -1.0;
		itemPriceMap = new HashMap<>();
	}

	public float getRepuation() {
		return reputation;
	}

	public void setReputation(float reputation) {
		this.reputation = reputation;
	}

	public HashMap<Long, Integer> getItemPriceMap() {
		return itemPriceMap;
	}

	public void setItemPriceMap(Long item, Integer price) {
		itemPriceMap.put(item, price);
	}

}
