package pl.vm.features.sequenced;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import org.junit.jupiter.api.Test;

class SequencedCollectionsDemoTest {
    
    private final SequencedCollectionsDemo demo = new SequencedCollectionsDemo();
    
    @Test
    void testListOperations() {
        // This test verifies that the list operations don't throw exceptions
        assertDoesNotThrow(() -> demo.demonstrateListOperations());
    }
    
    @Test
    void testSetOperations() {
        // This test verifies that the set operations don't throw exceptions
        assertDoesNotThrow(() -> demo.demonstrateSetOperations());
    }
    
    @Test
    void testMapOperations() {
        // This test verifies that the map operations don't throw exceptions
        assertDoesNotThrow(() -> demo.demonstrateMapOperations());
    }
} 