Long Project 3: Implementing Multi-Dimensional Search

Teammates: 
Phanindra Pydisetty   (pxp180031)
Sahith Reddy Adudodla (sxa180065)
Karttik Reddy Yellu   (kxy170003)
Bharath Rudra         (bxr180008)

I. Files List
 	MDS.java
	Readme.txt
 
II. How to Run the Project
* To compile `javac -d bin -sourcepath src src/pxp180031/LP3Driver.java`
* To Run `java -cp bin pxp180031.LP3Driver <input_path>`

Description:-

In total we have used the following data structures for efficient Implementation

1. HashMap<Long, Money> idPriceMap        - Id and the price of the item is stored in the HashMap
2. HashMap<Long, HashSet<Long>> idDescMap - Description of the respective Id is stored in HashSet and 
										 Id, Description(Key, Value) is stored in HashMap
3. HashMap<Long, HashSet<Long>> descIdMap - Ids that fall into specific description is stored in HashSet and
										 Description, Id (Key, Value) is stored in HashMap
										 
4. TreeSet<Long> ids					  - Ids are stored in Treeset

Number of data structure follows the number above

* Data structure 4 is used for retrieving the subset of ids that are in a given range which will be useful for the efficient implementation of the  method PriceHike

* Data structure 3 for retrieving the ids for a specific description in O(1) which will be useful for the efficient implementation of the MinPrice and MaxPrice

* Data structure 2 for retrieving the description of a id in O(1)

* Data Structure 1 for retrieving price of a id in o(1) 



P.S - We have pushed all our code changes to github (private repo) incrementally and you can verify them here!
Link - https://github.com/Phanindra48/md-search
Commits - https://github.com/Phanindra48/md-search/commits/master






