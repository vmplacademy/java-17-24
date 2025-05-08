package pl.vm.features.sequenced;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.SequencedCollection;
import java.util.SequencedMap;
import java.util.SequencedSet;
import java.util.logging.Logger;

/**
 * Demonstrates the use of Sequenced Collections introduced in Java 21.
 * This feature provides new interfaces and methods for working with collections
 * that have a defined encounter order.
 */
public class SequencedCollectionsDemo {
    private static final Logger logger = Logger.getLogger(SequencedCollectionsDemo.class.getName());
    
    /**
     * Demonstrates operations on a SequencedCollection (List)
     */
    public void demonstrateListOperations() {
        List<String> list = new ArrayList<>(List.of("First", "Second", "Third", "Fourth", "Last"));
        
        // New methods for accessing first and last elements
        logger.info("First element: " + list.getFirst());
        logger.info("Last element: " + list.getLast());
        
        // New methods for adding elements at the beginning or end
        list.addFirst("New First");
        list.addLast("New Last");
        
        // New methods for removing first or last elements
        String removedFirst = list.removeFirst();
        String removedLast = list.removeLast();
        
        logger.info("Removed first: " + removedFirst);
        logger.info("Removed last: " + removedLast);
        
        // Get reversed view of the collection
        SequencedCollection<String> reversed = list.reversed();
        logger.info("Reversed list: " + reversed);
    }
    
    /**
     * Demonstrates operations on a SequencedSet (LinkedHashSet)
     */
    public void demonstrateSetOperations() {
        SequencedSet<String> set = new LinkedHashSet<>(List.of("First", "Second", "Third", "Last"));
        
        // New methods for accessing first and last elements
        logger.info("First element: " + set.getFirst());
        logger.info("Last element: " + set.getLast());
        
        // New methods for adding elements at the beginning or end
        set.addFirst("New First");
        set.addLast("New Last");
        
        // New methods for removing first or last elements
        String removedFirst = set.removeFirst();
        String removedLast = set.removeLast();
        
        logger.info("Removed first: " + removedFirst);
        logger.info("Removed last: " + removedLast);
        
        // Get reversed view of the set
        SequencedSet<String> reversed = set.reversed();
        logger.info("Reversed set: " + reversed);
    }
    
    /**
     * Demonstrates operations on a SequencedMap (LinkedHashMap)
     */
    public void demonstrateMapOperations() {
        SequencedMap<String, Integer> map = new LinkedHashMap<>();
        map.put("First", 1);
        map.put("Second", 2);
        map.put("Third", 3);
        map.put("Last", 4);
        
        // New methods for accessing first and last entries
        logger.info("First entry: " + map.firstEntry());
        logger.info("Last entry: " + map.lastEntry());
        
        // New methods for accessing first and last keys
        logger.info("First key: " + map.firstEntry().getKey());
        logger.info("Last key: " + map.lastEntry().getKey());
        
        // New methods for accessing first and last values
        logger.info("First value: " + map.firstEntry().getValue());
        logger.info("Last value: " + map.lastEntry().getValue());
        
        // New methods for adding entries at the beginning or end
        map.putFirst("New First", 0);
        map.putLast("New Last", 5);
        
        // New methods for removing first or last entries
        Map.Entry<String, Integer> removedFirst = map.pollFirstEntry();
        Map.Entry<String, Integer> removedLast = map.pollLastEntry();
        
        logger.info("Removed first: " + removedFirst);
        logger.info("Removed last: " + removedLast);
        
        // Get reversed view of the map
        SequencedMap<String, Integer> reversed = map.reversed();
        logger.info("Reversed map: " + reversed);
    }
    
    public static void main(String[] args) {
        SequencedCollectionsDemo demo = new SequencedCollectionsDemo();
        
        logger.info("=== List Operations ===");
        demo.demonstrateListOperations();
        
        logger.info("\n=== Set Operations ===");
        demo.demonstrateSetOperations();
        
        logger.info("\n=== Map Operations ===");
        demo.demonstrateMapOperations();
    }
} 