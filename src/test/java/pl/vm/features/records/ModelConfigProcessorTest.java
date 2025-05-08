package pl.vm.features.records;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 * Test class for ModelConfigProcessor demonstrating record patterns.
 */
class ModelConfigProcessorTest {

    private final ModelConfigProcessor processor = new ModelConfigProcessor();

    @Test
    void test_processConfig_with_streaming() {
        // given
        ModelConfig config = ModelConfig.createStreamingConfig();
        
        // when
        String result = processor.processConfig(config);
        
        // then
        assertTrue(result.contains("Streaming configuration"));
        assertTrue(result.contains("streaming"));
        assertTrue(result.contains("4096"));
    }

    @Test
    void test_processConfig_with_code_gen() {
        // given
        ModelConfig config = ModelConfig.createCodeGenConfig();
        
        // when
        String result = processor.processConfig(config);
        
        // then
        assertTrue(result.contains("Large context configuration"));
        assertTrue(result.contains("8192"));
    }

    @Test
    void test_processConfigs_with_multiple_configs() {
        // given
        List<ModelConfig> configs = List.of(
            ModelConfig.createDefault(),
            ModelConfig.createStreamingConfig(),
            ModelConfig.createCodeGenConfig()
        );
        
        // when
        List<String> results = processor.processConfigs(configs);
        
        // then
        assertEquals(3, results.size());
        assertTrue(results.get(0).contains("Standard"));
        assertTrue(results.get(1).contains("Streaming"));
        assertTrue(results.get(2).contains("Large"));
    }

    @Test
    void test_validateConfig_with_valid_config() {
        // given
        ModelConfig config = ModelConfig.createDefault();
        
        // when
        String result = processor.validateConfig(config);
        
        // then
        assertTrue(result.contains("Valid configuration"));
    }

    @Test
    void test_validateConfig_with_invalid_tokens() {
        // given
        ModelConfig config = new ModelConfig(
            "invalid",
            "1.0",
            -1000,  // invalid tokens
            0.7,
            false,
            30
        );
        
        // when
        String result = processor.validateConfig(config);
        
        // then
        assertTrue(result.contains("Invalid configuration"));
        assertTrue(result.contains("tokens must be positive"));
    }

    @Test
    void test_validateConfig_with_invalid_temperature() {
        // given
        ModelConfig config = new ModelConfig(
            "invalid",
            "1.0",
            2048,
            1.5,  // invalid temperature
            false,
            30
        );
        
        // when
        String result = processor.validateConfig(config);
        
        // then
        assertTrue(result.contains("Invalid configuration"));
        assertTrue(result.contains("temperature must be between 0 and 1"));
    }

    @Test
    void test_getConfigSummary() {
        // given
        ModelConfig config = ModelConfig.createDefault();
        
        // when
        String result = processor.getConfigSummary(config);
        
        // then
        assertTrue(result.contains("Model: default"));
        assertTrue(result.contains("Version: 1.0"));
        assertTrue(result.contains("Max Tokens: 2048"));
        assertTrue(result.contains("Temperature: 0.70"));
        assertTrue(result.contains("Streaming: false"));
        assertTrue(result.contains("Timeout: 30 seconds"));
    }

    @Test
    void test_getDetailedAnalysis_with_code_gen_config() {
        // given
        ModelConfig config = ModelConfig.createCodeGenConfig();
        
        // when
        String result = processor.getDetailedAnalysis(config);
        
        // then
        assertTrue(result.contains("Detailed Analysis for code-gen (v1.0)"));
        assertTrue(result.contains("Performance: high (tokens: 8192)"));
        assertTrue(result.contains("Precision: high (temperature: 0.20)"));
        assertTrue(result.contains("Mode: batch"));
        assertTrue(result.contains("Reliability: high (timeout: 120 seconds)"));
    }

    @Test
    void test_getDetailedAnalysis_with_streaming_config() {
        // given
        ModelConfig config = ModelConfig.createStreamingConfig();
        
        // when
        String result = processor.getDetailedAnalysis(config);
        
        // then
        assertTrue(result.contains("Detailed Analysis for streaming (v1.0)"));
        assertTrue(result.contains("Performance: high (tokens: 4096)"));
        assertTrue(result.contains("Precision: balanced (temperature: 0.80)"));
        assertTrue(result.contains("Mode: streaming"));
        assertTrue(result.contains("Reliability: standard (timeout: 60 seconds)"));
    }
} 