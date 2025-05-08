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
 * 
 * Key components of a Gatherer:
 * 1. Initializer - Creates the initial state
 * 2. Integrator - Processes each element and updates state
 * 3. Combiner - Combines states for parallel processing
 * 4. Finisher - Finalizes the state and emits remaining results
 * 5. AndThen - Chains multiple gatherers together
 */
public class GatherersDemo {
    private static final Logger logger = LoggerFactory.getLogger(GatherersDemo.class);

    /**
     * Demonstrates basic gatherer operations using built-in gatherers
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
     * Demonstrates a custom gatherer with all key components:
     * - Initializer: Creates a new ArrayList
     * - Integrator: Groups elements into pairs
     * - Combiner: Merges states for parallel processing
     * - Finisher: Emits any remaining elements
     */
    public void demonstrateCustomGatherer() {
        List<String> words = List.of("apple", "banana", "cherry", "date", "elderberry");
        
        // Custom gatherer to group words by pairs
        Gatherer<String, List<String>, List<String>> groupByPairs = Gatherer.<String, List<String>, List<String>>of(
            // Initializer: Creates new state
            ArrayList::new,
            
            // Integrator: Processes each element
            (state, element, downstream) -> {
                state.add(element);
                if (state.size() == 2) {
                    downstream.push(new ArrayList<>(state));
                    state.clear();
                }
                return true;
            },
            
            // Combiner: Merges states for parallel processing
            (state1, state2) -> {
                state1.addAll(state2);
                return state1;
            },
            
            // Finisher: Emits remaining elements
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
     * Demonstrates a stateful gatherer that maintains running calculations:
     * - Initializer: Creates array to store sum and count
     * - Integrator: Updates running average
     * - Combiner: Merges running averages
     * - Finisher: No cleanup needed
     */
    public void demonstrateStatefulGatherer() {
        List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        
        // Gatherer that emits running averages
        Gatherer<Integer, double[], Double> runningAverage = Gatherer.<Integer, double[], Double>of(
            // Initializer: Creates state array [sum, count]
            () -> new double[]{0.0, 0.0},
            
            // Integrator: Updates running average
            (state, element, downstream) -> {
                state[0] += element;
                state[1]++;
                downstream.push(state[0] / state[1]);
                return true;
            },
            
            // Combiner: Merges running averages
            (state1, state2) -> {
                state1[0] += state2[0];
                state1[1] += state2[1];
                return state1;
            },
            
            // Finisher: No cleanup needed
            (state, downstream) -> {}
        );
        
        List<Double> averages = numbers.stream()
            .gather(runningAverage)
            .toList();
        
        logger.info("Running averages: {}", averages);
    }

    /**
     * Demonstrates chaining gatherers using andThen:
     * 1. First gatherer groups elements into pairs
     * 2. Second gatherer filters out pairs with specific criteria
     */
    public void demonstrateAndThenGatherer() {
        List<String> words = List.of("apple", "banana", "cherry", "date", "elderberry");
        
        // First gatherer: Groups into pairs
        Gatherer<String, List<String>, List<String>> pairGatherer = Gatherer.<String, List<String>, List<String>>of(
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
        
        // Second gatherer: Filters pairs where both words start with same letter
        Gatherer<List<String>, Void, List<String>> filterGatherer = Gatherer.<List<String>, Void, List<String>>of(
            () -> null,
            (state, pair, downstream) -> {
                if (pair.get(0).charAt(0) == pair.get(1).charAt(0)) {
                    downstream.push(pair);
                }
                return true;
            },
            (state1, state2) -> null,
            (state, downstream) -> {}
        );
        
        // Chain gatherers using andThen
        List<List<String>> filteredPairs = words.stream()
            .gather(pairGatherer.andThen(filterGatherer))
            .toList();
        
        logger.info("Filtered pairs (same first letter): {}", filteredPairs);
    }

    public static void main(String[] args) {
        GatherersDemo demo = new GatherersDemo();
        
        logger.info("=== Basic Gatherers ===");
        demo.demonstrateBasicGatherers();
        
        logger.info("\n=== Custom Gatherer ===");
        demo.demonstrateCustomGatherer();
        
        logger.info("\n=== Stateful Gatherer ===");
        demo.demonstrateStatefulGatherer();
        
        logger.info("\n=== AndThen Gatherer ===");
        demo.demonstrateAndThenGatherer();
    }
} 