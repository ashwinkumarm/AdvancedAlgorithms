package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.lp6;

import java.util.Arrays;
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
	public void setPricePerSupplier(Long supplier, Integer price) {
		pricePerSupplier.put(supplier, price);
	}
	public LinkedHashSet<Long> getDescription() {
		return description;
	}
	public void setDescription(Long[] description) {
		this.description.addAll(Arrays.asList(description));
	}

}
