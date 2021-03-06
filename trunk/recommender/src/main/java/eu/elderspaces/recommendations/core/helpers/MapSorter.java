package eu.elderspaces.recommendations.core.helpers;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author serxhiodaja (at) gmail (dot) com
 */

public class MapSorter {
    
    /*
     * Java method to sort Map in Java by value e.g. HashMap or Hashtable throw
     * NullPointerException if Map contains null values It also sort values even
     * if they are duplicates. DESCENDING ORDER
     */
    public static <K extends Comparable, V extends Comparable> Map<K, V> sortByValues(
            final Map<K, V> map) {
    
        final List<Map.Entry<K, V>> entries = new LinkedList<Map.Entry<K, V>>(map.entrySet());
        
        Collections.sort(entries, new Comparator<Map.Entry<K, V>>() {
            
            @Override
            public int compare(final Entry<K, V> o1, final Entry<K, V> o2) {
            
                return -1 * o1.getValue().compareTo(o2.getValue());
            }
        });
        
        // LinkedHashMap will keep the keys in the order they are inserted
        // which is currently sorted on natural ordering
        final Map<K, V> sortedMap = new LinkedHashMap<K, V>();
        
        for (final Map.Entry<K, V> entry : entries) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        
        return sortedMap;
    }
}
