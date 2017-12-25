package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.lp6;

import java.util.HashMap;

/**
 * This class stores the supplier details such as reputation, items sold by that
 * supplier with their corresponding prices along with getter and setter methods
 *
 * @author Ashwin, Arun, Deepak, Haritha
 *
 */
public class SupplierDetails {

	private Float reputation;
	private HashMap<Long, Integer> itemPriceMap;

	/**
	 * Constructor to initialize the reputation
	 */
	public SupplierDetails() {
		reputation = (float) -1.0;
		itemPriceMap = new HashMap<>();
	}

	/**
	 * Getter method for reputation
	 * 
	 * @return
	 */
	public float getRepuation() {
		return reputation;
	}

	/**
	 * Setter method for reputation
	 * 
	 * @param reputation
	 */
	public void setReputation(float reputation) {
		this.reputation = reputation;
	}

	/**
	 * Getter method for itemPrice Map
	 * 
	 * @return
	 */
	public HashMap<Long, Integer> getItemPriceMap() {
		return itemPriceMap;
	}

	/**
	 * Setter method for itemPrice Map
	 * 
	 * @param item
	 * @param price
	 */
	public void setItemPriceMap(Long item, Integer price) {
		itemPriceMap.put(item, price);
	}

}
