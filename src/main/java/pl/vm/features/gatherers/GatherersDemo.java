package pl.vm.features.gatherers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Gatherer;
import java.util.stream.Gatherers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Demonstrates the use of Gatherers introduced in Java 24.
 * Gatherers provide a way to create custom intermediate operations for streams.
 */
public class GatherersDemo {
    private static final Logger logger = LoggerFactory.getLogger(GatherersDemo.class);

    /**
     * Demonstrates basic gatherer operations
     */
    public void demonstrateBasicGatherers() {
        List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        
        // Using windowFixed gatherer to create sliding windows
        List<List<Integer>> windows = numbers.stream()
            .gather(Gatherers.windowFixed(3))
            .toList();
        
        logger.info("Windows of size 3: {}", windows);
        
        // Using windowSliding gatherer to create overlapping windows
        List<List<Integer>> slidingWindows = numbers.stream()
            .gather(Gatherers.windowSliding(3))
            .toList();
        
        logger.info("Sliding windows of size 3: {}", slidingWindows);
    }

    /**
     * Demonstrates custom gatherer implementation
     */
    public void demonstrateCustomGatherer() {
        List<String> words = List.of("apple", "banana", "cherry", "date", "elderberry");
        
        // Custom gatherer to group words by pairs
        Gatherer<String, List<String>, List<String>> groupByPairs = Gatherer.<String, List<String>, List<String>>of(
            ArrayList::new,
            (state, element, downstream) -> {
                state.add(element);
                if (state.size() == 2) {
                    downstream.push(new ArrayList<>(state));
                    state.clear();
                }
                return true;
            },
            (state1, state2) -> {
                state1.addAll(state2);
                return state1;
            },
            (state, downstream) -> {
                if (!state.isEmpty()) {
                    downstream.push(new ArrayList<>(state));
                }
            }
        );
        
        List<List<String>> groupedWords = words.stream()
            .gather(groupByPairs)
            .toList();
        
        logger.info("Words grouped by pairs: {}", groupedWords);
    }

    /**
     * Demonstrates gatherer with state
     */
    public void demonstrateStatefulGatherer() {
        List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        
        // Gatherer that emits running averages
        Gatherer<Integer, double[], Double> runningAverage = Gatherer.<Integer, double[], Double>of(
            () -> new double[]{0.0, 0.0}, // [sum, count]
            (state, element, downstream) -> {
                state[0] += element;
                state[1]++;
                downstream.push(state[0] / state[1]);
                return true;
            },
            (state1, state2) -> {
                state1[0] += state2[0];
                state1[1] += state2[1];
                return state1;
            },
            (state, downstream) -> {}
        );
        
        List<Double> averages = numbers.stream()
            .gather(runningAverage)
            .toList();
        
        logger.info("Running averages: {}", averages);
    }

    public static void main(String[] args) {
        GatherersDemo demo = new GatherersDemo();
        
        logger.info("=== Basic Gatherers ===");
        demo.demonstrateBasicGatherers();
        
        logger.info("\n=== Custom Gatherer ===");
        demo.demonstrateCustomGatherer();
        
        logger.info("\n=== Stateful Gatherer ===");
        demo.demonstrateStatefulGatherer();
    }
} 