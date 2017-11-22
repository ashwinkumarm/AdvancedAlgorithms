package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.lp6;

import java.util.HashMap;
import java.util.LinkedHashSet;

public class ItemDetails {
	
	HashMap<Long, Integer> pricePerSupplier;
	LinkedHashSet<Long> description;
	
	public ItemDetails(){	
		pricePerSupplier = new HashMap<>();
		description = new LinkedHashSet<>();
	}

	public HashMap<Long, Integer> getPricePerSupplier() {
		return pricePerSupplier;
	}
	public void setPricePerSupplier(HashMap<Long, Integer> pricePerSupplier) {
		this.pricePerSupplier = pricePerSupplier;
	}
	public LinkedHashSet<Long> getDescription() {
		return description;
	}
	public void setDescription(LinkedHashSet<Long> description) {
		this.description = description;
	}

}
