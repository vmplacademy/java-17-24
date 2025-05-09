package pl.vm.features.gatherers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Gatherer;
import java.util.stream.Gatherers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class GatherersDemoTest {
    private static final Logger logger = LoggerFactory.getLogger(GatherersDemoTest.class);
    private final GatherersDemo demo = new GatherersDemo();

    @Test
    void should_create_fixed_size_windows_when_using_basic_gatherer() {
        // given
        List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6);

        // when
        List<List<Integer>> windows = numbers.stream()
            .gather(Gatherers.windowFixed(3))
            .toList();

        // then
        assertEquals(2, windows.size());
        assertEquals(List.of(1, 2, 3), windows.get(0));
        assertEquals(List.of(4, 5, 6), windows.get(1));
    }

    @Test
    void should_group_elements_in_pairs_when_using_custom_gatherer() {
        // given
        List<String> words = List.of("apple", "banana", "cherry", "date");

        // when
        List<List<String>> pairs = words.stream()
            .gather(createPairGatherer())
            .toList();

        // then
        assertEquals(2, pairs.size());
        assertEquals(List.of("apple", "banana"), pairs.get(0));
        assertEquals(List.of("cherry", "date"), pairs.get(1));
    }

    @Test
    void should_calculate_running_average_when_using_stateful_gatherer() {
        // given
        List<Integer> numbers = List.of(2, 4, 6);

        // when
        List<Double> averages = numbers.stream()
            .gather(createRunningAverageGatherer())
            .toList();

        // then
        assertEquals(3, averages.size());
        assertEquals(2.0, averages.get(0));
        assertEquals(3.0, averages.get(1));
        assertEquals(4.0, averages.get(2));
    }

    @Test
    void should_batch_elements_in_groups_of_three() {
        // given
        List<String> items = List.of("A", "B", "C", "D", "E", "F", "G");
        
        // when
        List<List<String>> batches = items.stream()
            .gather(createBatchGatherer(3))
            .toList();
        
        // then
        assertEquals(3, batches.size());
        assertEquals(List.of("A", "B", "C"), batches.get(0));
        assertEquals(List.of("D", "E", "F"), batches.get(1));
        assertEquals(List.of("G"), batches.get(2));
    }

    @Test
    void should_handle_empty_list_when_batching() {
        // given
        List<String> items = List.of();
        
        // when
        List<List<String>> batches = items.stream()
            .gather(createBatchGatherer(3))
            .toList();
        
        // then
        assertTrue(batches.isEmpty());
    }

    @Test
    void should_handle_partial_batch_when_batching() {
        // given
        List<String> items = List.of("A", "B");
        
        // when
        List<List<String>> batches = items.stream()
            .gather(createBatchGatherer(3))
            .toList();
        
        // then
        assertEquals(1, batches.size());
        assertEquals(List.of("A", "B"), batches.get(0));
    }

    private Gatherer<String, List<String>, List<String>> createPairGatherer() {
        return Gatherer.<String, List<String>, List<String>>of(
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
    }

    private Gatherer<Integer, double[], Double> createRunningAverageGatherer() {
        
        return Gatherer.<Integer, double[], Double>of(
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
    }

    private Gatherer<String, List<String>, List<String>> createBatchGatherer(int batchSize) {
        return Gatherer.<String, List<String>, List<String>>of(
            ArrayList::new,
            (state, element, downstream) -> {
                state.add(element);
                if (state.size() == batchSize) {
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
    }
} 