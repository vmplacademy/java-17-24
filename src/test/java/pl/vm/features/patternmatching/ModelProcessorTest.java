package pl.vm.features.patternmatching;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import pl.vm.features.sealed.model.ChatGPTModel;
import pl.vm.features.sealed.model.ClaudeModel;
import pl.vm.features.sealed.model.DeepSeekModel;
import pl.vm.features.sealed.model.GeminiModel;

/**
 * Test class for ModelProcessor demonstrating pattern matching in switch expressions.
 */
class ModelProcessorTest {

    private final ModelProcessor processor = new ModelProcessor();

    @Test
    void test_processModel_with_chatgpt() {
        // given
        ChatGPTModel model = new ChatGPTModel("1.0", "gpt-4", 2048, 0.7);
        
        // when
        String result = processor.processModel(model);
        
        // then
        assertTrue(result.contains("Processing ChatGPT model"));
        assertTrue(result.contains("gpt-4"));
    }

    @Test
    void test_processModel_with_gemini() {
        // given
        GeminiModel model = new GeminiModel("1.0", "pro", 2048, 0.7);
        
        // when
        String result = processor.processModel(model);
        
        // then
        assertTrue(result.contains("Processing Gemini model"));
        assertTrue(result.contains("pro"));
    }

    @Test
    void test_processModelWithConditions_with_chatgpt_gpt4() {
        // given
        ChatGPTModel model = new ChatGPTModel("1.0", "gpt-4", 2048, 0.7);
        
        // when
        String result = processor.processModelWithConditions(model);
        
        // then
        assertEquals("Processing advanced ChatGPT model (GPT-4)", result);
    }

    @Test
    void test_processModelWithConditions_with_chatgpt_gpt35() {
        // given
        ChatGPTModel model = new ChatGPTModel("1.0", "gpt-3.5-turbo", 2048, 0.7);
        
        // when
        String result = processor.processModelWithConditions(model);
        
        // then
        assertEquals("Processing standard ChatGPT model (GPT-3.5)", result);
    }

    @Test
    void test_getModelCapabilities_with_chatgpt_gpt4() {
        // given
        ChatGPTModel model = new ChatGPTModel("1.0", "gpt-4", 2048, 0.7);
        
        // when
        String result = processor.getModelCapabilities(model);
        
        // then
        assertTrue(result.contains("ChatGPT capabilities"));
        assertTrue(result.contains("Advanced reasoning"));
        assertTrue(result.contains("code generation"));
    }

    @Test
    void test_getModelCapabilities_with_gemini_pro() {
        // given
        GeminiModel model = new GeminiModel("1.0", "pro", 2048, 0.7);
        
        // when
        String result = processor.getModelCapabilities(model);
        
        // then
        assertTrue(result.contains("Gemini capabilities"));
        assertTrue(result.contains("Professional grade"));
        assertTrue(result.contains("balanced performance"));
    }

    @Test
    void test_getModelCapabilities_with_claude_opus() {
        // given
        ClaudeModel model = new ClaudeModel("1.0", "opus", 2048, 0.7);
        
        // when
        String result = processor.getModelCapabilities(model);
        
        // then
        assertTrue(result.contains("Claude capabilities"));
        assertTrue(result.contains("Advanced reasoning"));
        assertTrue(result.contains("complex tasks"));
    }

    @Test
    void test_getModelCapabilities_with_deepseek_coder() {
        // given
        DeepSeekModel model = new DeepSeekModel("1.0", "coder", 2048, 0.7);
        
        // when
        String result = processor.getModelCapabilities(model);
        
        // then
        assertTrue(result.contains("DeepSeek capabilities"));
        assertTrue(result.contains("Specialized in code generation"));
        assertTrue(result.contains("analysis"));
    }
} 