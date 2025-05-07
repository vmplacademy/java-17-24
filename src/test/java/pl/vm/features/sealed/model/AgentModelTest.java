package pl.vm.features.sealed.model;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 * Test class for AgentModel.
 * Demonstrates testing of a final class with immutable properties.
 */
class AgentModelTest {

    @Test
    void test_agent_model_creation() {
        // Given
        String name = "TestAgent";
        String version = "1.0.0";
        String provider = "TestProvider";
        String[] capabilities = {"task1", "task2"};
        boolean isAutonomous = true;

        // When
        AgentModel agent = new AgentModel(name, version, provider, capabilities, isAutonomous);

        // Then
        assertEquals(name, agent.getName());
        assertEquals(version, agent.getVersion());
        assertEquals(provider, agent.getProvider());
        assertArrayEquals(capabilities, agent.getCapabilities());
        assertTrue(agent.isAutonomous());
    }

    @Test
    void test_agent_model_toString() {
        // Given
        String name = "TestAgent";
        String version = "1.0.0";
        String provider = "TestProvider";
        String[] capabilities = {"task1", "task2"};
        boolean isAutonomous = true;

        // When
        AgentModel agent = new AgentModel(name, version, provider, capabilities, isAutonomous);
        String toString = agent.toString();

        // Then
        assertTrue(toString.contains("name='TestAgent'"));
        assertTrue(toString.contains("version='1.0.0'"));
        assertTrue(toString.contains("provider='TestProvider'"));
        assertTrue(toString.contains("capabilities=task1, task2"));
        assertTrue(toString.contains("isAutonomous=true"));
    }
} 