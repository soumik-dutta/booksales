package utils;

import pojo.Books;

import java.util.*;

public class DataUtils {
    // storing the data of the top selling books bookid->count
    public static HashMap<String, Integer> topSellingBooks = new HashMap<String, Integer>();
    // storing the data of the top customer emailid->count
    public static HashMap<String, Integer> topCustomers = new HashMap<String, Integer>();
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
    public static HashMap<String, Integer> sortByValue(HashMap<String, Integer> hm) {
        // Create a list from elements of HashMap
        List<Map.Entry<String, Integer>> list =
                new LinkedList<Map.Entry<String, Integer>>(hm.entrySet());
        // Sort the list in reverse order
        Collections.sort(list, Collections.<Map.Entry<String, Integer>>reverseOrder(new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2) {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        }));
        // put data from sorted list to hashmap
        HashMap<String, Integer> temp = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }

}
