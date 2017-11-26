package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.lp6;

import java.util.Arrays;
import java.util.HashSet;

public class SupplierDetails {

	private float reputation;
	private HashSet<Long> items;

	public SupplierDetails() {
		reputation = (float) -1.0;
		items = new HashSet<>();
	}

	public float getRepuation() {
		return reputation;
	}

	public void setReputation(float reputation) {
		this.reputation = reputation;
	}

	public HashSet<Long> getItems() {
		return items;
	}

	public void setItems(Long[] items) {
		this.items.addAll(Arrays.asList(items));
	}

}
