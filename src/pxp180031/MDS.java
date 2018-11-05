/** Starter code for LP3
 *  @author
 *  Phanindra Pydisetty   (pxp180031)
		Sahith Reddy Adudodla (sxa180065)
		Karttik Reddy Yellu   (kxy170003)
		Bharath Rudra         (bxr180008)
 *  
 *  
 */

package pxp180031;

import java.util.*;


public class MDS {
	//Description given in readme.txt for the data structures that are taken for storing the data
	HashMap<Long, Money> idPriceMap;    
	HashMap<Long, HashSet<Long>> idDescMap;
	HashMap<Long, HashSet<Long>> descIdMap;
	TreeSet<Long> ids;

	// Constructors to Initialize the data structures
	public MDS() {
		idPriceMap = new HashMap<>();
		idDescMap = new HashMap<>();
		descIdMap = new HashMap<>();
		ids = new TreeSet<>();
	}

	/*
	 * Public methods of MDS. Do not change their signatures.
	 * __________________________________________________________________ a.
	 * Insert(id,price,list): insert a new item whose description is given in the
	 * list. If an entry with the same id already exists, then its description and
	 * price are replaced by the new values, unless list is null or empty, in which
	 * case, just the price is updated. Returns 1 if the item is new, and 0
	 * otherwise.
	 */
	public int insert(long id, Money price, java.util.List<Long> list) {
		Money m = idPriceMap.put(id, price);				//Inserts and Updates the id and price in idPriceMap
		HashSet<Long> newDesc = new HashSet<>();
		HashSet<Long> temp;
		if (!list.isEmpty()) {											//Add the entries into newDesc only if list is not empty
			for (long listId : list) {								//Iterating over the List entries to add it into newDesc(Hashset)
				newDesc.add(listId);
				HashSet<Long> hSet = descIdMap.get(listId);
				if (hSet == null) {						//If the description is not present, then create a new entry in descIdMap with the given description and id
					temp = new HashSet<>();
					temp.add(id);
					descIdMap.put(listId, temp);
				} else {											//If the description is already present, add the id to the respective description in descIdMap
					hSet.add(id);										
				}
			}
			if (m != null) {								//if the id is already present and description needs to be updated
				HashSet<Long> oldDesc = idDescMap.get(id);
				if (!oldDesc.isEmpty())				
					oldDesc.removeAll(newDesc);	
				for (long k : oldDesc) {
					descIdMap.get(k).remove(id);
				}
			}
			idDescMap.put(id, newDesc);
		}
		if (m == null) {
			ids.add(id);										//if the Id is not present, add the id to Ids and return 1
			return 1;
		} else {
			return 0;												//return 0 if id is present already
		}
	}

	// b. Find(id): return price of item with given id (or 0, if not found).
	public Money find(long id) {
		Money m = idPriceMap.get(id);  //Getting the respective money object from idPriceMap
		if (m != null) {
			return m;                    //return the respective money object if id is present
		}
		return new Money();            //else return empty object 
	}

	/*
	 * c. Delete(id): delete item from storage. Returns the sum of the long ints
	 * that are in the description of the item deleted, or 0, if such an id did not
	 * exist.
	 */
	public long delete(long id) {
		if (!idDescMap.containsKey(id))
			return 0;
		long sum = 0;
		HashSet<Long> desc = idDescMap.get(id);
		for (long k : desc) {
			sum += k;				//Summing up the descriptions of the deleted entries
			descIdMap.get(k).remove(id);//removing the id from each description that contains id in descIdMap
		}
		idDescMap.remove(id);//removing the id from idDescMap
		ids.remove(id);			//removing from Ids
		idPriceMap.remove(id);//removing from idPriceMap
		return sum;
	}

	/*
	 * d. FindMinPrice(n): given a long int, find items whose description contains
	 * that number (exact match with one of the long ints in the item's
	 * description), and return lowest price of those items. Return 0 if there is no
	 * such item.
	 */
	public Money findMinPrice(long n) {   //descIdMap contains the ids of a specific description
		HashSet<Long> hSet = descIdMap.get(n);
		if (hSet == null || hSet.isEmpty()) //If hSet is null or empty then return new money object
			return new Money();
		Iterator<Long> iter = hSet.iterator();//Setting the Iterator
		Money minPrice = idPriceMap.get(iter.next()); //Initializing minPrice to the first id
		while (iter.hasNext()) {				//Iterating through all Ids for finding Min Price
			Money m = idPriceMap.get(iter.next());
			if (minPrice.compareTo(m) == 1) {
				minPrice = m;
			}
		}
		return minPrice;	//Returns the minimum priced money object
	}

	/*
	 * e. FindMaxPrice(n): given a long int, find items whose description contains
	 * that number, and return highest price of those items. Return 0 if there is no
	 * such item.
	 */
	public Money findMaxPrice(long n) {
		HashSet<Long> hSet = descIdMap.get(n);  //descIdMap contains the ids of a specific description
		if (hSet == null || hSet.isEmpty())
			return new Money();
		Iterator<Long> iter = hSet.iterator();  //Setting the Iterator
		Money maxPrice = idPriceMap.get(iter.next()); //Initializing maxPrice to the first id
		while (iter.hasNext()) {								//Iterating through all Ids for finding Max Price
			Money m = idPriceMap.get(iter.next());
			if (maxPrice.compareTo(m) == -1) {
				maxPrice = m;
			}
		}
		return maxPrice;												//Returns the maximum priced money object
	}

	/*
	 * f. FindPriceRange(n,low,high): given a long int n, find the number of items
	 * whose description contains n, and in addition, their prices fall within the
	 * given range, [low, high].
	 */
	public int findPriceRange(long n, Money low, Money high) {
		HashSet<Long> hSet = descIdMap.get(n);    //descIdMap contains the Ids of a specific description
		int sum = 0;
		for (long k : hSet) {
			Money m = idPriceMap.get(k);						//Iterating through all Ids for range check
			if (m.compareTo(low) >= 0 && m.compareTo(high) <= 0) { //Checking if the item price falls in the given range
				sum += 1;
			}
		}
		return sum; //Returning the total sum of items present in the given range w.r.to the given description
	}

	/*
	 * g. PriceHike(l,h,r): increase the price of every product, whose id is in the
	 * range [l,h] by r%. Discard any fractional pennies in the new prices of items.
	 * Returns the sum of the net increases of the prices.
	 */
	public Money priceHike(long l, long h, double rate) {
		TreeSet<Long> hSet = (TreeSet<Long>) ids.subSet(l, true, h, true);    // hSet contains the subset of ids between l and h 
		long oldPriceInCents, newPriceInCents, diff;
		long net = 0;
		for (long k : hSet) {
			Money m = idPriceMap.get(k);
			oldPriceInCents = m.d * 100 + m.c;
			diff = (long) (oldPriceInCents * (0.01 * rate)); // diff for the difference between newPriceInCents and oldPriceInCents
			newPriceInCents = oldPriceInCents + diff;
			Money priceObj = new Money(newPriceInCents / 100, (int) (newPriceInCents % 100));
			net += diff;																		 //Sum the net differences between newPriceInCents and oldPriceInCents
			idPriceMap.put(k, priceObj);      							 //Stores the Increased price of the Id
		}

		long netIncreaseInDollars = net / 100;             //Dividing Total net by 100 gives dollars
		return new Money(netIncreaseInDollars, 0);         //Returns the Money object with net increase of prices in dollars
	}

	/*
	 * h. RemoveNames(id, list): Remove elements of list from the description of id.
	 * It is possible that some of the items in the list are not in the id's
	 * description. Return the sum of the numbers that are actually deleted from the
	 * description of id. Return 0 if there is no such id.
	 */
	public long removeNames(long id, java.util.List<Long> list) {
		HashSet<Long> desc = idDescMap.get(id);
		if (desc == null || list.isEmpty())  //If List is empty or the description is null for a given id return 0
			return 0;
		long sum = 0;					
		HashSet<Long> hSet = new HashSet<>(list);
		hSet.retainAll(desc); // hSet contains common elements of list and listIds of the given id
		for (long k : hSet) {
			sum += k;
			desc.remove(k); // removing names(descriptions) from idDescMap
			descIdMap.get(k).remove(id);// removing id from the descriptions that are removed
		}
		return sum;
	}

	// Do not modify the Money class in a way that breaks LP3Driver.java
	public static class Money implements Comparable<Money> {
		long d;
		int c;

		public Money() {
			d = 0;
			c = 0;
		}

		public Money(long d, int c) {
			this.d = d;
			this.c = c;
		}

		public Money(String s) {
			String[] part = s.split("\\.");
			int len = part.length;
			if (len < 1) {
				d = 0;
				c = 0;
			} else if (part.length == 1) {
				d = Long.parseLong(s);
				c = 0;
			} else {
				d = Long.parseLong(part[0]);
				c = Integer.parseInt(part[1]);
			}
		}

		public long dollars() {
			return d;
		}

		public int cents() {
			return c;
		}

		public int compareTo(Money other) { 
			if (!(other instanceof Money))
				return -1;

			if (this.amountInCents() > other.amountInCents())
				return 1;
			else if (this.amountInCents() < other.amountInCents())
				return -1;
			else
				return 0;
		}

		private long amountInCents() {  //Returns the total number of cents for the respective item
			return d * 100 + c;
		}

		public String toString() {
			return d + String.format(".%02d", c);
		}
	}

}
