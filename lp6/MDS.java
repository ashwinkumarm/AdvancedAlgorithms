package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.lp6;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

public class MDS {

	Map<Long,ItemDetails> itemInfo;
	Map<Long,HashSet<Long>> desrciptionItemIdMap;
	Map<Long,Float> supplierReputation;
	Map<Long,HashSet<Long>> supplierItemIdMap;

	
	public MDS() {
		itemInfo = new HashMap<>();
		desrciptionItemIdMap = new HashMap<>();
		supplierReputation = new HashMap<>();
		supplierItemIdMap = new HashMap<>();
	}

	public static class Pair {
		long id;
		int price;

		public Pair(long id, int price) {
			this.id = id;
			this.price = price;
		}
	}

	/*
	 * add a new item. If an entry with the same id already exists, the new
	 * description is merged with the existing description of the item. Returns true
	 * if the item is new, and false otherwise.
	 */
	public boolean add(Long id, Long[] description) {
		boolean newItemFlag = false;
		ItemDetails item = itemInfo.get(id);
		if(item == null){
			item = new ItemDetails();
			itemInfo.put(id, item);
			newItemFlag = true;
		}
		item.setDescription(description);
		for (Long desc : description) {
			HashSet<Long> itemIdSet = desrciptionItemIdMap.get(desc);
    		if(itemIdSet == null)
    		{
    			itemIdSet = new HashSet<>();
    		}
    		itemIdSet.add(id);
    		desrciptionItemIdMap.put(desc, itemIdSet);
		}
		return newItemFlag;
	}

	/*
	 * add a new supplier (Long) and their reputation (float in [0.0-5.0], single
	 * decimal place). If the supplier exists, their reputation is replaced by the
	 * new value. Return true if the supplier is new, and false otherwise.
	 */
	public boolean add(Long supplier, float reputation) {
		boolean newSupplierFlag = true;
		if(supplierReputation.containsKey(supplier)){
			newSupplierFlag = false;
		}
		supplierReputation.put(supplier, reputation);
		return newSupplierFlag;
	}

	/*
	 * add products and their prices at which the supplier sells the product. If
	 * there is an entry for the price of an id by the same supplier, then the price
	 * is replaced by the new price. Returns the number of new entries created.
	 */
	public int add(Long supplier, Pair[] idPrice) {
		
		int noOfNewItems = 0;
		HashSet<Long> itemIdSet = supplierItemIdMap.get(supplier);
		if(itemIdSet == null){
			itemIdSet = new HashSet<>();
			supplierItemIdMap.put(supplier, itemIdSet);
		}
		for (Pair pair : idPrice) {
			Long id = pair.id;
			Integer price = pair.price;
			ItemDetails item = itemInfo.get(id);
			if(item != null){
				Set<Long> supplierIdSet = item.getPricePerSupplier().keySet();
				if(!supplierIdSet.contains(supplier)){
					noOfNewItems++;
				}			
			}
			else{
				item = new ItemDetails();
				itemInfo.put(id,item);
				noOfNewItems++;
			}
			item.setPricePerSupplier(supplier, price);
			itemIdSet.add(id);
		}		
		return noOfNewItems;
	}

	/*
	 * return an array with the description of id. Return null if there is no item
	 * with this id.
	 */
	public Long[] description(Long id) {
		return (Long[])itemInfo.get(id).getDescription().toArray();
	}

	/*
	 * given an array of Longs, return an array of items whose description contains
	 * one or more elements of the array, sorted by the number of elements of the
	 * array that are in the item's description (non-increasing order).
	 */
	public Long[] findItem(Long[] arr) {
		TreeMap<Long, Integer> items = new TreeMap<>();
		for (Long desc : arr) {
			if(desrciptionItemIdMap.containsKey(desc)){
				HashSet<Long> itemsIdSet = desrciptionItemIdMap.get(desc);
				for (Long item : itemsIdSet) {
					items.put(item, items.getOrDefault(item, 0)+1);
				}
			}
		}
		return (Long[])sortByValues(items, false).keySet().toArray();
	}

	/*
	 * given a Long n, return an array of items whose description contains n, which
	 * have one or more suppliers whose reputation meets or exceeds the given
	 * minimum reputation, that sell that item at a price that falls within the
	 * price range [minPrice, maxPrice] given. Items should be sorted in order of
	 * their minimum price charged by a supplier for that item (non-decreasing
	 * order).
	 */
	public Long[] findItem(Long n, int minPrice, int maxPrice, float minReputation) {
		
		TreeMap<Long, Integer> items = new TreeMap<>();
		HashSet<Long> itemIdSet = desrciptionItemIdMap.get(n);
		if(itemIdSet != null){
			for (Long itemId : itemIdSet) {
				ItemDetails item = itemInfo.get(itemId);
				for(Entry<Long, Integer> pricePerSupplier : item.getPricePerSupplier().entrySet()){
					Long supplier = pricePerSupplier.getKey();
					Integer price = pricePerSupplier.getValue();
					Float reputation = supplierReputation.get(supplier);
					if(reputation != null && reputation >= minReputation){
						if(price > minPrice && price < maxPrice){
							Integer minimumPrice = items.get(itemId);
							if(minimumPrice == null || minimumPrice > price){
								items.put(itemId, price);
							}
						}
					}
					
				}
			}
		}
		return (Long[])sortByValues(items, true).keySet().toArray();
	}

	/*
	 * given an id, return an array of suppliers who sell that item, ordered by the
	 * price at which they sell the item (non-decreasing order).
	 */
	public Long[] findSupplier(Long id) {
		ItemDetails item = itemInfo.get(id);
		return (Long[])sortByValues(item.getPricePerSupplier(), true).keySet().toArray();
	}

	/*
	 * given an id and a minimum reputation, return an array of suppliers who sell
	 * that item, whose reputation meets or exceeds the given reputation. The array
	 * should be ordered by the price at which they sell the item (non-decreasing
	 * order).
	 */
	public Long[] findSupplier(Long id, float minReputation) {
		
		TreeMap<Long, Integer> suppliers = new TreeMap<>();
		ItemDetails item = itemInfo.get(id);
		for(Entry<Long, Integer> pricePerSupplier : item.getPricePerSupplier().entrySet()){
			Long supplier = pricePerSupplier.getKey();
			Integer price = pricePerSupplier.getValue();
			Float reputation = supplierReputation.get(supplier);
			if(reputation != null && reputation >= minReputation){
				suppliers.put(supplier, price);
			}
			
		}
		return (Long[])sortByValues(suppliers, true).keySet().toArray();
	}

	/*
	 * find suppliers selling 5 or more products, who have the same identical
	 * profile as another supplier: same reputation, and, sell the same set of
	 * products, at identical prices. This is a rare operation, so do not do
	 * additional work in the other operations so that this operation is fast.
	 * Creative solutions that are elegant and efficient will be awarded excellence
	 * credit. Return array of suppliers satisfying above condition. Make sure that
	 * each supplier appears only once in the returned array.
	 */
	public Long[] identical() {
		return null;
	}

	/*
	 * given an array of ids, find the total price of those items, if those items
	 * were purchased at the lowest prices, but only from sellers meeting or
	 * exceeding the given minimum reputation. Each item can be purchased from a
	 * different seller.
	 */
	public int invoice(Long[] arr, float minReputation) {
		return 0;
	}

	/*
	 * remove all items, all of whose suppliers have a reputation that is equal or
	 * lower than the given maximum reputation. Returns an array with the items
	 * removed.
	 */
	public Long[] purge(float maxReputation) {
		HashSet<Long> itemsRemoved = new HashSet<>();
		for (Entry<Long, HashSet<Long>> supplierItemsPair : supplierItemIdMap.entrySet()) {
			Long supplier = supplierItemsPair.getKey();
			if(supplierReputation.get(supplier) <= maxReputation){
				for (Long itemId : supplierItemsPair.getValue()) {
					ItemDetails itemDetail = itemInfo.get(itemId);
					itemDetail.getPricePerSupplier().remove(supplier);
					itemsRemoved.add(itemId);
				}
				supplierItemIdMap.remove(supplier);
				supplierReputation.remove(supplier);
			}
		}
		return null;
	}

	/*
	 * remove item from storage. Returns the sum of the Longs that are in the
	 * description of the item deleted (or 0, if such an id did not exist).
	 */
	public Long remove(Long id) {
		Long sum = 0L;
		ItemDetails item = itemInfo.get(id);
		if(item != null){
			for (Long desc : item.description) {
				sum += desc;
				HashSet<Long> itemIdSet = desrciptionItemIdMap.get(desc);
				itemIdSet.remove(id);
			}
			itemInfo.remove(id);
		}
		return sum;
	}

	/*
	 * remove from the given id's description those elements that are in the given
	 * array. It is possible that some elements of the array are not part of the
	 * item's description. Return the number of elements that were actually removed
	 * from the description.
	 */
	public int remove(Long id, Long[] arr) {
		int noOfElementsRemoved = 0;
		ItemDetails item = itemInfo.get(id);
		if(item != null) {
			for (Long desc : arr) {
				if(item.getDescription().remove(desc)){
					noOfElementsRemoved++;
					HashSet<Long> itemIdSet = desrciptionItemIdMap.get(desc);
					itemIdSet.remove(id);
				}
			}
		}
		return noOfElementsRemoved;
	}

	/*
	 * remove the elements of the array from the description of all items. Return
	 * the number of items that lost one or more terms from their descriptions.
	 */
	public int removeAll(Long[] arr) {
		
		HashSet<Long> itemsRemoved = new HashSet<>();
		for (Long desc : arr) {
			itemsRemoved = desrciptionItemIdMap.remove(desc);
			if(itemsRemoved != null){
				for (Long item : itemsRemoved) {
					ItemDetails itemDetail = itemInfo.get(item);
					if(itemDetail.getDescription().remove(desc)){
						itemsRemoved.add(item);
					}
				}
			}
		}
		return itemsRemoved.size();
	}
	
	public static <K, V extends Comparable<V>> Map<K, V> sortByValues(final Map<K, V> map, boolean isAsc) {
	    Comparator<K> valueComparator = new Comparator<K>() {
	      public int compare(K k1, K k2) {
	    	int compare;
	    	if(isAsc){
	    		compare = map.get(k1).compareTo(map.get(k2));
	    	}
	    	else{
	    		 compare = map.get(k2).compareTo(map.get(k1));
	    	}
	    	
	        if (compare == 0) 
	          return 1;
	        else 
	          return compare;
	      }
	    };
	 
	    Map<K, V> sortedByValues = new TreeMap<K, V>(valueComparator);
	    sortedByValues.putAll(map);
	    return sortedByValues;
	}
}