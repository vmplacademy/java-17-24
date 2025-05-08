package pl.vm.features.sealed.model;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Test class demonstrating the usage of sealed classes in Java.
 * 
 * Sealed Classes (JEP 360, 397, 409) were introduced to provide more control over inheritance hierarchies.
 * This feature allows a class to declare which other classes or interfaces may extend or implement it.
 * 
 * Key aspects of sealed classes demonstrated in this test:
 * 1. Type safety: The compiler ensures that only permitted classes can extend the sealed class
 * 2. Exhaustive pattern matching: When using pattern matching with sealed classes, the compiler can verify
 *    that all possible cases are handled
 * 3. Domain modeling: Sealed classes are particularly useful for modeling domain concepts where the set of
 *    possible subtypes is known and fixed
 * 
 * In this example, we demonstrate sealed classes through the LLM (Large Language Model) hierarchy:
 * - LLMModel is a sealed abstract class that permits only specific implementations
 * - Each implementation (ChatGPT, Gemini, Claude, DeepSeek) represents a concrete model type
 * - The sealed hierarchy ensures that no unexpected model types can be added
 * 
 * Relevant JEPs:
 * - JEP 360: Sealed Classes (Preview) - Initial introduction in Java 15
 * - JEP 397: Sealed Classes (Second Preview) - Refinements in Java 16
 * - JEP 409: Sealed Classes - Finalized in Java 17
 * 
 * @see <a href="https://openjdk.org/jeps/360">JEP 360: Sealed Classes (Preview)</a>
 * @see <a href="https://openjdk.org/jeps/397">JEP 397: Sealed Classes (Second Preview)</a>
 * @see <a href="https://openjdk.org/jeps/409">JEP 409: Sealed Classes</a>
 */
class LLMModelTest {
    private static final Logger logger = LoggerFactory.getLogger(LLMModelTest.class);

    @Test
    void test_chat_gpt_model_processing() {
        // given
        ChatGPTModel model = new ChatGPTModel("1.0", "gpt-4", 2048, 0.7);
        
        // when
        String result = model.process("Hello, world!");
        logger.info("ChatGPT result: {}", result);
        
        // then
        assertEquals("1.0", model.getVersion());
        assertEquals(2048, model.getMaxTokens());
        assertEquals(0.7, model.getTemperature());
        assertEquals("gpt-4", model.getModelVariant());
        assertEquals("ChatGPT-gpt-4", model.getModelName());
        assertTrue(result.contains("ChatGPT-gpt-4"));
        assertTrue(result.contains("maxTokens=2048"));
        assertTrue(result.contains("temperature=0.70"));
    }

    @Test
    void test_gemini_model_processing() {
        // given
        GeminiModel model = new GeminiModel("1.0", "pro", 2048, 0.7);
        
        // when
        String result = model.process("Hello, world!");
        logger.info("Gemini result: {}", result);
        
        // then
        assertEquals("1.0", model.getVersion());
        assertEquals(2048, model.getMaxTokens());
        assertEquals(0.7, model.getTemperature());
        assertEquals("pro", model.getModelSize());
        assertEquals("Gemini-pro", model.getModelName());
        assertTrue(result.contains("Gemini-pro"));
        assertTrue(result.contains("maxTokens=2048"));
        assertTrue(result.contains("temperature=0.70"));
    }

    @Test
    void test_claude_model_processing() {
        // given
        ClaudeModel model = new ClaudeModel("1.0", "opus", 2048, 0.7);
        
        // when
        String result = model.process("Hello, world!");
        logger.info("Claude result: {}", result);
        
        // then
        assertEquals("1.0", model.getVersion());
        assertEquals(2048, model.getMaxTokens());
        assertEquals(0.7, model.getTemperature());
        assertEquals("opus", model.getModelVariant());
        assertEquals("Claude-opus", model.getModelName());
        assertTrue(result.contains("Claude-opus"));
        assertTrue(result.contains("maxTokens=2048"));
        assertTrue(result.contains("temperature=0.70"));
    }

    @Test
    void test_deep_seek_model_processing() {
        // given
        DeepSeekModel model = new DeepSeekModel("1.0", "coder", 2048, 0.7);
        
        // when
        String result = model.process("Hello, world!");
        logger.info("DeepSeek result: {}", result);
        
        // then
        assertEquals("1.0", model.getVersion());
        assertEquals(2048, model.getMaxTokens());
        assertEquals(0.7, model.getTemperature());
        assertEquals("coder", model.getModelVariant());
        assertEquals("DeepSeek-coder", model.getModelName());
        assertTrue(result.contains("DeepSeek-coder"));
        assertTrue(result.contains("maxTokens=2048"));
        assertTrue(result.contains("temperature=0.70"));
    }

    @Test
    void test_rag_system_with_different_models() {
        // given
        ChatGPTModel gptModel = new ChatGPTModel("1.0", "gpt-4", 2048, 0.7);
        RAGSystem gptRag = new RAGSystem(gptModel, "kb-123", 5, List.of("docs", "wiki"));
        
        ClaudeModel claudeModel = new ClaudeModel("1.0", "opus", 2048, 0.7);
        RAGSystem claudeRag = new RAGSystem(claudeModel, "kb-123", 5, List.of("docs", "wiki"));
        
        // when
        String gptResult = gptRag.process("What is Java?");
        String claudeResult = claudeRag.process("What is Java?");
        
        // then
        assertTrue(gptResult.contains("ChatGPT-gpt-4"));
        assertTrue(claudeResult.contains("Claude-opus"));
    }
} 