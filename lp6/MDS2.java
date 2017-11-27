package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.lp6;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class MDS2 {

	Map<Long, ItemDetails2> itemInfo;
	Map<Long, HashSet<Long>> descriptionItemIdMap;
	Map<Long, SupplierDetails2> supplierInfo;

	public MDS2() {
		itemInfo = new HashMap<>();
		descriptionItemIdMap = new HashMap<>();
		supplierInfo = new HashMap<>();
	}

	public static class Pair {
		long id;
		int price;

		public Pair(long id, int price) {
			this.id = id;
			this.price = price;
		}
	}

	public class ItemDetails2 {
		Long itemId;
		TreeSet<Long> supplierSet;
		HashSet<Long> description;

		public ItemDetails2(Long itemId) {
			this.itemId = itemId;
			supplierSet = new TreeSet<>(new PriceComparator(itemId));
			description = new HashSet<>();
		}

		public void setSupplierSet(Long supplier) {
			supplierSet.add(supplier);
		}

		public void setDescription(Long[] description) {
			this.description.addAll(Arrays.asList(description));
		}

	}

	class PriceComparator implements Comparator<Long> {
		Long itemId;

		public PriceComparator(Long itemId) {
			this.itemId = itemId;
		}

		@Override
		public int compare(Long s1, Long s2) {
			Integer price1 = supplierInfo.get(s1).getItemPriceMap().get(itemId);
			Integer price2 = supplierInfo.get(s2).getItemPriceMap().get(itemId);
			int compare = price1.compareTo(price2);
			if (compare == 0)
				return 1;
			else
				return compare;
		}
	}

	/*
	 * add a new item. If an entry with the same id already exists, the new
	 * description is merged with the existing description of the item. Returns
	 * true if the item is new, and false otherwise.
	 */
	public boolean add(Long id, Long[] description) {
		boolean newItemFlag = false;
		ItemDetails2 item;
		if ((item = itemInfo.get(id)) == null) {
			item = new ItemDetails2(id);
			itemInfo.put(id, item);
			newItemFlag = true;
		}
		item.setDescription(description);
		for (Long desc : description) {
			HashSet<Long> itemIdSet;
			if ((itemIdSet = descriptionItemIdMap.get(desc)) == null) {
				itemIdSet = new HashSet<>();
				descriptionItemIdMap.put(desc, itemIdSet);
			}
			itemIdSet.add(id);
		}
		return newItemFlag;
	}

	/*
	 * add a new supplier (Long) and their reputation (float in [0.0-5.0],
	 * single decimal place). If the supplier exists, their reputation is
	 * replaced by the new value. Return true if the supplier is new, and false
	 * otherwise.
	 */
	public boolean add(Long supplier, float reputation) {
		boolean newSupplierFlag = false;
		SupplierDetails2 supplierDetails;
		if ((supplierDetails = supplierInfo.get(supplier)) == null) {
			supplierDetails = new SupplierDetails2();
			supplierInfo.put(supplier, supplierDetails);
			newSupplierFlag = true;
		}
		supplierDetails.setReputation(reputation);
		return newSupplierFlag;
	}

	/*
	 * add products and their prices at which the supplier sells the product. If
	 * there is an entry for the price of an id by the same supplier, then the
	 * price is replaced by the new price. Returns the number of new entries
	 * created.
	 */
	public int add(Long supplier, Pair[] idPrice) {
		int noOfNewItems = 0;
		SupplierDetails2 supplierDetails;
		if ((supplierDetails = supplierInfo.get(supplier)) == null) {
			supplierDetails = new SupplierDetails2();
			supplierInfo.put(supplier, supplierDetails);
		}
		HashMap<Long, Integer> itemPriceMap = supplierDetails.getItemPriceMap();
		Long id;
		Integer price;
		for (Pair pair : idPrice) {
			id = pair.id;
			price = pair.price;
			if (itemPriceMap.containsKey(id)) {
				itemPriceMap.put(id, price);
			} else {
				noOfNewItems++;
				itemPriceMap.put(id, price);
				itemInfo.get(id).supplierSet.add(supplier);
			}

		}
		return noOfNewItems;
	}

	/*
	 * return an array with the description of id. Return null if there is no
	 * item with this id.
	 */
	public Long[] description(Long id) {
		Long[] descriptions = null;
		ItemDetails2 item;
		if ((item = itemInfo.get(id)) != null) {
			HashSet<Long> descriptionSet = item.description;
			descriptions = descriptionSet.toArray(new Long[descriptionSet.size()]);
		}
		return descriptions;
	}

	/*
	 * given an array of Longs, return an array of items whose description
	 * contains one or more elements of the array, sorted by the number of
	 * elements of the array that are in the item's description (non-increasing
	 * order).
	 */
	public Long[] findItem(Long[] arr) {
		Map<Long, Integer> items = new HashMap<>();
		HashSet<Long> itemsIdSet;
		for (Long desc : arr) {
			if ((itemsIdSet = descriptionItemIdMap.get(desc)) != null) {
				for (Long item : itemsIdSet) {
					items.put(item, items.getOrDefault(item, 0) + 1);
				}
			}
		}
		return sortByValues(items, false).keySet().toArray(new Long[items.size()]);
	}

	/*
	 * given a Long n, return an array of items whose description contains n,
	 * which have one or more suppliers whose reputation meets or exceeds the
	 * given minimum reputation, that sell that item at a price that falls
	 * within the price range [minPrice, maxPrice] given. Items should be sorted
	 * in order of their minimum price charged by a supplier for that item
	 * (non-decreasing order).
	 */
	public Long[] findItem(Long n, int minPrice, int maxPrice, float minReputation) {
		HashMap<Long, Integer> items = new HashMap<>();
		HashSet<Long> itemIdSet;
		ItemDetails2 item;
		Integer minimumPrice, price;
		if ((itemIdSet = descriptionItemIdMap.get(n)) != null) {
			for (Long itemId : itemIdSet) {
				if ((item = itemInfo.get(itemId)) != null) {
					minimumPrice = Integer.MAX_VALUE;
					for (Long supplier : item.supplierSet) {
						SupplierDetails2 supplierDetails = supplierInfo.get(supplier);
						price = supplierDetails.getItemPriceMap().get(itemId);
						Float reputation = supplierDetails.getRepuation();
						if (reputation >= minReputation) {
							if (price >= minPrice && price <= maxPrice) {
								minimumPrice = price;
								break;
							}
						}
					}
					if (minimumPrice != Integer.MAX_VALUE)
						items.put(itemId, minimumPrice);
				}
			}
		}
		return sortByValues(items, true).keySet().toArray(new Long[items.size()]);
	}

	/*
	 * given an id, return an array of suppliers who sell that item, ordered by
	 * the price at which they sell the item (non-decreasing order).
	 */
	public Long[] findSupplier(Long id) {
		Long[] suppliers = null;
		ItemDetails2 item;
		if ((item = itemInfo.get(id)) != null)
			suppliers = item.supplierSet.toArray(new Long[item.supplierSet.size()]);
		return suppliers;
	}

	/*
	 * given an id and a minimum reputation, return an array of suppliers who
	 * sell that item, whose reputation meets or exceeds the given reputation.
	 * The array should be ordered by the price at which they sell the item
	 * (non-decreasing order).
	 */
	public Long[] findSupplier(Long id, float minReputation) {
		LinkedList<Long> suppliers = new LinkedList<>();
		ItemDetails2 item;
		if ((item = itemInfo.get(id)) != null) {
			for (Long supplier : item.supplierSet) {
				Float reputation = supplierInfo.get(supplier).getRepuation();
				if (reputation >= minReputation)
					suppliers.addLast(supplier);
			}
		}
		return suppliers.toArray(new Long[suppliers.size()]);
	}

	/*
	 * find suppliers selling 5 or more products, who have the same identical
	 * profile as another supplier: same reputation, and, sell the same set of
	 * products, at identical prices. This is a rare operation, so do not do
	 * additional work in the other operations so that this operation is fast.
	 * Creative solutions that are elegant and efficient will be awarded
	 * excellence credit. Return array of suppliers satisfying above condition.
	 * Make sure that each supplier appears only once in the returned array.
	 */
	/*--public Long[] identical() {
	
		HashMap<HashSet<Long>, HashSet<Long>> suppliersGroupedByItemIds = (HashMap<HashSet<Long>, HashSet<Long>>) supplierItemIdMap
				.entrySet().stream().filter(x -> x.getValue().size() >= 5)
				.collect(Collectors.groupingBy(Map.Entry::getValue)).values().stream()
				.collect(
						Collectors.toMap(item -> item.get(0).getValue(),
								item -> new HashSet<>(
										item.stream().map(Map.Entry::getKey).collect(Collectors.toList()))))
				.entrySet().stream().filter(x -> x.getValue().size() > 1)
				.collect(Collectors.toMap(p -> p.getKey(), p -> p.getValue()));
				
		HashMap<HashSet<Long>, HashSet<Long>> suppliersWithSameReputation = new HashMap<>();
		
		for (Entry<HashSet<Long>, HashSet<Long>> suppliersItemIdsPair : suppliersGroupedByItemIds.entrySet()) {
			HashSet<Long> itemIdSet = suppliersItemIdsPair.getKey();
			HashSet<Long> supplierIdSet = suppliersItemIdsPair.getValue();
			HashSet<Float> reputationSet = new HashSet<>();
			for (Long supplierId : supplierIdSet) {
				reputationSet.add(supplierReputation.get(supplierId));
			}
			if(reputationSet.size() == 1){
				suppliersWithSameReputation.put(itemIdSet, supplierIdSet);
			}
		}
		
		HashSet<Long> identicalSuppliers = new HashSet<>();
		for (Entry<HashSet<Long>, HashSet<Long>> suppliersItemIdsPair : suppliersWithSameReputation.entrySet()) {
			HashSet<Long> itemIdSet = suppliersItemIdsPair.getKey();
			HashSet<Long> supplierIdSet = suppliersItemIdsPair.getValue();
			boolean identical = true;
			for (Long itemId : itemIdSet) {
				HashSet<Integer> price = new HashSet<>();
				for (Long supplierId : supplierIdSet) {
					ItemDetails itemDetail = itemInfo.get(itemId);
					if(itemDetail != null){
						price.add(itemDetail.getPricePerSupplier().get(supplierId));
					}
				}
				if(price.size() > 1){
					identical = false;
					break;
				}
			}
			if(identical){
				identicalSuppliers.addAll(supplierIdSet);
			}			
		}
		
		return identicalSuppliers.toArray(new Long[identicalSuppliers.size()]);
	}*/

	public Long[] identical() {

		HashMap<Set<Long>, HashSet<Long>> suppliersGroupedByItemIds = new HashMap<>();
		HashSet<Long> supplierSet;
		for (Entry<Long, SupplierDetails2> entry : supplierInfo.entrySet()) {
			if ((supplierSet = suppliersGroupedByItemIds.get(entry.getValue().getItemPriceMap().keySet())) != null) {
				supplierSet.add(entry.getKey());
			} else {
				suppliersGroupedByItemIds.put(entry.getValue().getItemPriceMap().keySet(), new HashSet<Long>() {
					{
						add(entry.getKey());
					}
				});
			}

		}

		HashMap<Set<Long>, HashSet<Long>> identicalSuppliers = new HashMap<>();
		for (Entry<Set<Long>, HashSet<Long>> suppliersItemIdsPair : suppliersGroupedByItemIds.entrySet()) {
			Set<Long> itemIdSet = suppliersItemIdsPair.getKey();
			if (itemIdSet.size() >= 5) {
				HashSet<Long> supplierIdSet = suppliersItemIdsPair.getValue();
				HashSet<Long> supp = new HashSet<Long>();
				for (Long itemId : itemIdSet) {
					for (Long supplierId1 : supplierIdSet) {
						SupplierDetails2 supplierDetails1 = supplierInfo.get(supplierId1);
						for (Long supplierId2 : supplierIdSet) {
							if (supplierId1 != supplierId2) {
								Integer price1, price2;
								SupplierDetails2 supplierDetails2 = supplierInfo.get(supplierId2);
								if (supplierDetails1 != null && supplierDetails2 != null
										&& (price1 = supplierDetails1.getItemPriceMap().get(itemId)) != null
										&& (price2 = supplierDetails2.getItemPriceMap().get(itemId)) != null) {
									if (price1.intValue() == price2.intValue()) {
										supp.add(supplierId2);
										supp.add(supplierId1);
									}
								}

							}
						}
					}
				}

				if (supp.size() > 0) {
					identicalSuppliers.put(itemIdSet, supp);
				}
			}
		}

		HashSet<Long> suppliers = new HashSet<>();
		for (Entry<Set<Long>, HashSet<Long>> suppliersItemIdsPair : identicalSuppliers.entrySet()) {
			HashSet<Long> supp = suppliersItemIdsPair.getValue();
			for (Long s1 : supp) {
				for (Long s2 : supp) {
					if (s1 != s2 && supplierInfo.get(s1).getRepuation() == supplierInfo.get(s2).getRepuation()) {
						suppliers.add(s1);
						suppliers.add(s2);
					}
				}
			}
		}

		return suppliers.toArray(new Long[suppliers.size()]);
	}

	/*
	 * given an array of ids, find the total price of those items, if those
	 * items were purchased at the lowest prices, but only from sellers meeting
	 * or exceeding the given minimum reputation. Each item can be purchased
	 * from a different seller.
	 */
	public int invoice(Long[] arr, float minReputation) {
		int totalPrice = 0;
		for (Long itemId : arr) {
			ItemDetails2 item;
			SupplierDetails2 supplierDetails;
			if ((item = itemInfo.get(itemId)) != null) {
				for (Long supplier : item.supplierSet) {
					supplierDetails = supplierInfo.get(supplier);
					Float reputation = supplierDetails.getRepuation();
					if (reputation >= minReputation) {
						totalPrice += supplierDetails.getItemPriceMap().get(itemId);
						break;
					}
				}
			}
		}
		return totalPrice;
	}

	/*
	 * remove all items, all of whose suppliers have a reputation that is equal
	 * or lower than the given maximum reputation. Returns an array with the
	 * items removed.
	 */
	public Long[] purge(float maxReputation) {
		HashSet<Long> itemsRemoved = new HashSet<>();
		boolean toBeRemoved;
		for (Entry<Long, ItemDetails2> itemIdDetailPair : itemInfo.entrySet()) {
			toBeRemoved = true;
			TreeSet<Long> supplierSet = itemIdDetailPair.getValue().supplierSet;
			for (Long supplier : supplierSet) {
				if (supplierInfo.get(supplier).getRepuation() > maxReputation) {
					toBeRemoved = false;
					break;
				}
			}
			if (toBeRemoved)
				itemsRemoved.add(itemIdDetailPair.getKey());
		}
		for (Long item : itemsRemoved)
			remove(item);
		return itemsRemoved.toArray(new Long[itemsRemoved.size()]);
	}

	/*
	 * remove item from storage. Returns the sum of the Longs that are in the
	 * description of the item deleted (or 0, if such an id did not exist).
	 */
	public Long remove(Long id) {
		Long total = 0L;
		ItemDetails2 item;
		if ((item = itemInfo.remove(id)) != null) {
			for (Long desc : item.description) {
				total += desc;
				descriptionItemIdMap.get(desc).remove(id);
			}
			for (Long supplier : item.supplierSet)
				supplierInfo.get(supplier).getItemPriceMap().remove(id);
		}
		return total;
	}

	/*
	 * remove from the given id's description those elements that are in the
	 * given array. It is possible that some elements of the array are not part
	 * of the item's description. Return the number of elements that were
	 * actually removed from the description.
	 */
	public int remove(Long id, Long[] arr) {
		int noOfElementsRemoved = 0;
		ItemDetails2 item;
		if ((item = itemInfo.get(id)) != null) {
			for (Long desc : arr) {
				if (item.description.remove(desc)) {
					noOfElementsRemoved++;
					descriptionItemIdMap.get(desc).remove(id);
				}
			}
		}
		return noOfElementsRemoved;
	}

	/*
	 * remove the elements of the array from the description of all items.
	 * Return the number of items that lost one or more terms from their
	 * descriptions.
	 */
	public int removeAll(Long[] arr) {
		HashSet<Long> itemsRemoved = new HashSet<>();
		for (Long desc : arr) {
			HashSet<Long> itemIdSet;
			if ((itemIdSet = descriptionItemIdMap.remove(desc)) != null) {
				for (Long item : itemIdSet) {
					ItemDetails2 itemDetail = itemInfo.get(item);
					if (itemDetail.description.remove(desc)) {
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
				if (isAsc) {
					compare = map.get(k1).compareTo(map.get(k2));
				} else {
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