package utils;

import pojo.Books;

import java.util.*;

public class DataUtils {
    // storing the data of the top selling books bookid->count
    public static HashMap<String, Float> topSellingBooks = new HashMap<String, Float>();
    // storing the data of the top customer emailid->count
    public static HashMap<String, Float> topCustomers = new HashMap<String, Float>();
    // storing the total sales by date date->amount
    public static HashMap<String, Float> salesByDate = new HashMap<String, Float>();
    // book data red-black tree for fast retrieval
    public static Map<String, Books> books = new TreeMap<String, Books>();

    /**
     * function to sort hashmap by values
     *
     * @param hm hash map to be sorted
     * @return
     */
    public static HashMap<String, Float> sortByValue(HashMap<String, Float> hm) {
        // Create a list from elements of HashMap
        List<Map.Entry<String, Float>> list =
                new LinkedList<Map.Entry<String, Float>>(hm.entrySet());
        // Sort the list in reverse order
        Collections.sort(list, Collections.<Map.Entry<String, Float>>reverseOrder(new Comparator<Map.Entry<String, Float>>() {
            public int compare(Map.Entry<String, Float> o1,
                               Map.Entry<String, Float> o2) {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        }));
        // put data from sorted list to hashmap
        HashMap<String, Float> temp = new LinkedHashMap<String, Float>();
        for (Map.Entry<String, Float> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }

}
