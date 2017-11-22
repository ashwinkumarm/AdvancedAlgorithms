package cs6301.g12.Implementation_of_Advanced_Data_Structures_and_Algorithms.lp6;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.TreeSet;

public class MDS2 {

	static HashMap<Long, TreeSet<Long>> productMap;
	static HashMap<Long, TreeSet<Long>> descriptionMap;
	static HashMap<Long, SupplierPair> supplierMap;
	static HashMap<Long, TreeSet<Long>> productSupplierMap;

	public MDS2() {
		productMap = new HashMap<Long, TreeSet<Long>>();
		descriptionMap = new HashMap<Long, TreeSet<Long>>();
		supplierMap = new HashMap<Long, SupplierPair>();
		productSupplierMap = new HashMap<Long, TreeSet<Long>>();
	}

	public static class Pair {
		long id;
		int price;

		public Pair(long id, int price) {
			this.id = id;
			this.price = price;
		}
	}

	public static class SupplierPair {
		float reputation;
		HashMap<Long, Integer> pairMap;

		public SupplierPair(float reputation, HashMap<Long, Integer> pairMap) {
			this.reputation = reputation;
			this.pairMap = pairMap;
		}
	}

	/*
	 * add a new item. If an entry with the same id already exists, the new
	 * description is merged with the existing description of the item. Returns true
	 * if the item is new, and false otherwise.
	 */
	public boolean add(Long id, Long[] description) {
		boolean changeFlag = true;
		TreeSet<Long> descriptionSet;
		if ((descriptionSet = productMap.get(id)) != null)
			changeFlag = false;
		else
			descriptionSet = new TreeSet<Long>();
		descriptionSet.addAll(Arrays.asList(description));
		productMap.put(id, descriptionSet);
		TreeSet<Long> idSet;
		for (Long descItem : description) {
			if ((idSet = descriptionMap.get(descItem)) != null)
				idSet.add(id);
			else
				idSet = new TreeSet<Long>() {
					{
						add(id);
					}
				};
			descriptionMap.put(descItem, idSet);
		}
		return changeFlag;
	}

	/*
	 * add a new supplier (Long) and their reputation (float in [0.0-5.0], single
	 * decimal place). If the supplier exists, their reputation is replaced by the
	 * new value. Return true if the supplier is new, and false otherwise.
	 */
	public boolean add(Long supplier, float reputation) {
		SupplierPair sp;
		if ((sp = supplierMap.get(supplier)) != null) {
			sp.reputation = reputation;
			return false;
		}
		supplierMap.put(supplier, new SupplierPair(reputation, new HashMap<Long, Integer>()));
		return true;
	}

	/*
	 * add products and their prices at which the supplier sells the product. If
	 * there is an entry for the price of an id by the same supplier, then the price
	 * is replaced by the new price. Returns the number of new entries created.
	 */
	public int add(Long supplier, Pair[] idPrice) {
		SupplierPair sp;
		boolean elseFlag = false;
		int newItemsAdded = 0;
		HashMap<Long, Integer> pairMap;
		if ((sp = supplierMap.get(supplier)) != null)
			pairMap = sp.pairMap;
		else {
			pairMap = new HashMap<Long, Integer>();
			elseFlag = true;
		}
		Integer price;
		TreeSet<Long> supplierSet;
		for (Pair pair : idPrice) {
			if ((price = pairMap.get(pair.id)) == null)
				newItemsAdded++;
			pairMap.put(pair.id, pair.price);
			if ((supplierSet = productSupplierMap.get(pair.id)) != null)
				supplierSet.add(supplier);
			else
				supplierSet = new TreeSet<Long>() {
					{
						add(supplier);
					}
				};
			productSupplierMap.put(pair.id, supplierSet);
		}
		if (elseFlag)
			supplierMap.put(supplier, new SupplierPair((float) -1.0, pairMap));
		return newItemsAdded;
	}

	/*
	 * return an array with the description of id. Return null if there is no item
	 * with this id.
	 */
	public Long[] description(Long id) {
		TreeSet<Long> productDescription;
		if ((productDescription = productMap.get(id)) != null)
			return (Long[]) productDescription.toArray();
		return null;
	}

	/*
	 * given an array of Longs, return an array of items whose description contains
	 * one or more elements of the array, sorted by the number of elements of the
	 * array that are in the item's description (non-increasing order).
	 */
	public Long[] findItem(Long[] arr) {
		TreeMap<Long, Integer> itemsMap = new TreeMap<>();
		TreeSet<Long> idSet;
		for (Long descItem : arr) {
			if ((idSet = descriptionMap.get(descItem)) != null) {
				for (Long item : idSet) {
					itemsMap.put(item, itemsMap.getOrDefault(item, 0) + 1);
				}
			}
		}
		return (Long[]) sortByValues(itemsMap).keySet().toArray();
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
		TreeSet<Long> productSet;
		if ((productSet = descriptionMap.get(n)) != null) {
			TreeSet<Long> supplierSet;
			TreeMap<Long, Integer> productPriceMap = new TreeMap<Long, Integer>();
			SupplierPair sp;
			int minPriceSoFar;
			for (Long product : productSet) {
				if ((supplierSet = productSupplierMap.get(product)) != null) {
					minPriceSoFar = Integer.MAX_VALUE;
					for (Long supplier : supplierSet) {
						sp = supplierMap.get(supplier);
						int price = sp.pairMap.get(product);
						if (sp.reputation >= minReputation && minPrice >= price && price <= maxPrice)
							minPriceSoFar = price < minPriceSoFar ? price : minPriceSoFar;
					}
					productPriceMap.put(product, minPriceSoFar);
				}
			}
			return (Long[]) sortByValues(productPriceMap).keySet().toArray();
		}
		return new Long[0];
	}

	/*
	 * given an id, return an array of suppliers who sell that item, ordered by the
	 * price at which they sell the item (non-decreasing order).
	 */
	public Long[] findSupplier(Long id) {
		TreeMap<Long, Integer> supplierPriceMap = new TreeMap<Long, Integer>();
		TreeSet<Long> supplierSet;
		if ((supplierSet = productSupplierMap.get(id)) != null) {
			int price;
			for (Long supplier : supplierSet) {
				price = supplierMap.get(supplier).pairMap.get(id);
				supplierPriceMap.put(supplier, price);
			}
			return (Long[]) sortByValues(supplierPriceMap).keySet().toArray();
		}
		return new Long[0];
	}

	/*
	 * given an id and a minimum reputation, return an array of suppliers who sell
	 * that item, whose reputation meets or exceeds the given reputation. The array
	 * should be ordered by the price at which they sell the item (non-decreasing
	 * order).
	 */
	public Long[] findSupplier(Long id, float minReputation) {
		TreeMap<Long, Integer> supplierPriceMap = new TreeMap<Long, Integer>();
		TreeSet<Long> supplierSet;
		if ((supplierSet = productSupplierMap.get(id)) != null) {
			SupplierPair sp;
			int price;
			for (Long supplier : supplierSet) {
				sp = supplierMap.get(supplier);
				if (sp.reputation >= minReputation) {
					price = sp.pairMap.get(id);
					supplierPriceMap.put(supplier, price);
				}
			}
			return (Long[]) sortByValues(supplierPriceMap).keySet().toArray();
		}
		return new Long[0];
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
		TreeSet<Long> supplierSet;
		SupplierPair sp;
		int minPriceSoFar, totalPrice = 0;
		for (Long product : arr) {
			minPriceSoFar = Integer.MAX_VALUE;
			if ((supplierSet = productSupplierMap.get(product)) != null) {
				for (Long supplier : supplierSet) {
					sp = supplierMap.get(supplier);
					if (sp.reputation >= minReputation) {
						int price = sp.pairMap.get(product);
						minPriceSoFar = price < minPriceSoFar ? price : minPriceSoFar;

					}
				}
				totalPrice += minPriceSoFar;
			}
		}
		return totalPrice;
	}

	/*
	 * remove all items, all of whose suppliers have a reputation that is equal or
	 * lower than the given maximum reputation. Returns an array with the items
	 * removed.
	 */
	public Long[] purge(float maxReputation) {
		SupplierPair sp;
		HashSet<Long> removedProductSet = new HashSet<Long>();
		for (Entry<Long, SupplierPair> entry : supplierMap.entrySet()) {
			sp = entry.getValue();
			if (sp.reputation <= maxReputation) {
				supplierMap.remove(entry.getKey());
				removedProductSet.addAll(sp.pairMap.keySet());
			}
		}
		return (Long[]) removedProductSet.toArray();
	}

	/*
	 * remove item from storage. Returns the sum of the Longs that are in the
	 * description of the item deleted (or 0, if such an id did not exist).
	 */
	public Long remove(Long id) {
		Long totalDescription = 0L;
		TreeSet<Long> descriptionSet, supplierSet;
		if ((descriptionSet = productMap.remove(id)) != null) {
			TreeSet<Long> productSet;
			for (Long description : descriptionSet) {
				totalDescription += description;
				productSet = descriptionMap.get(description);
				productSet.remove(id);
				descriptionMap.put(description, productSet);
			}
			supplierSet = productSupplierMap.get(id);
			SupplierPair sp;
			for (Long supplier : supplierSet) {
				sp = supplierMap.get(supplier);
				sp.pairMap.remove(id);
			}
		}
		return totalDescription;
	}

	/*
	 * remove from the given id's description those elements that are in the given
	 * array. It is possible that some elements of the array are not part of the
	 * item's description. Return the number of elements that were actually removed
	 * from the description.
	 */
	public int remove(Long id, Long[] arr) {
		TreeSet<Long> descriptionSet, productSet;
		boolean removedFlag;
		int elementsRemoved = 0;
		if ((descriptionSet = productMap.get(id)) != null) {
			for (Long description : arr) {
				removedFlag = descriptionSet.remove(description);
				if (removedFlag) {
					elementsRemoved++;
					productSet = descriptionMap.get(description);
					productSet.remove(id);
					descriptionMap.put(description, productSet);
				}
			}
			if (descriptionSet.size() > 0)
				productMap.put(id, descriptionSet);
		}
		return elementsRemoved;
	}

	/*
	 * remove the elements of the array from the description of all items. Return
	 * the number of items that lost one or more terms from their descriptions.
	 */
	public int removeAll(Long[] arr) {
		TreeSet<Long> descriptionSet, productSet;
		boolean removedFlag;
		int elementsAffected, maxElementsAffected = 0;
		for (Long description : arr) {
			if ((productSet = descriptionMap.remove(description)) != null) {
				elementsAffected = 0;
				for (Long product : productSet) {
					descriptionSet = productMap.get(product);
					removedFlag = descriptionSet.remove(description);
					if (removedFlag) {
						elementsAffected++;
						if (descriptionSet.size() > 0)
							productMap.put(product, descriptionSet);
					}
				}
				maxElementsAffected = elementsAffected > maxElementsAffected ? elementsAffected : maxElementsAffected;
			}
		}
		return maxElementsAffected;
	}

	public static <K, V extends Comparable<V>> Map<K, V> sortByValues(final Map<K, V> map) {
		Comparator<K> valueComparator = new Comparator<K>() {
			public int compare(K k1, K k2) {
				int compare = map.get(k2).compareTo(map.get(k1));
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
