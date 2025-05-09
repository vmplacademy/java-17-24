package pl.vm.features.records;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * Demonstrates the use of Records in Java.
 * This class shows how to process record instances and access their components.
 * 
 * Key aspects demonstrated:
 * 1. Record component access
 * 2. Record validation
 * 3. Record processing in streams
 * 4. Record formatting
 * 
 * @see <a href="https://openjdk.org/jeps/395">JEP 395: Records</a>
 */
public class ModelConfigProcessor {
    
    /**
     * Process a model configuration using record components.
     */
    public String processConfig(ModelConfig config) {
        if (config.streaming()) {
            return "Streaming configuration: " + config.modelName() + " (v" + config.version() + ") with " + config.maxTokens() + " tokens";
        } else if (config.maxTokens() > 4000) {
            return "Large context configuration: " + config.modelName() + " (v" + config.version() + ") with " + config.maxTokens() + " tokens";
        } else if (config.temperature() < 0.3) {
            return "Precise configuration: " + config.modelName() + " (v" + config.version() + ") with temperature " + config.temperature();
        }
        return "Standard configuration: " + config.modelName() + " (v" + config.version() + ")";
    }
    
    /**
     * Process a list of configurations using record components in stream operations.
     */
    public List<String> processConfigs(List<ModelConfig> configs) {
        return configs.stream()
            .map(config -> {
                if (config.streaming()) {
                    return "Streaming: " + config.modelName() + " (" + config.maxTokens() + " tokens)";
                } else if (config.maxTokens() > 4000) {
                    return "Large: " + config.modelName() + " (" + config.maxTokens() + " tokens)";
                } else if (config.temperature() < 0.3) {
                    return "Precise: " + config.modelName() + " (temp: " + config.temperature() + ")";
                }
                return "Standard: " + config.modelName();
            })
            .collect(Collectors.toList());
    }
    
    /**
     * Validate a configuration using record components.
     */
    public String validateConfig(ModelConfig config) {
        if (config.maxTokens() <= 0) {
            return "Invalid configuration: tokens must be positive";
        } else if (config.temperature() < 0.0 || config.temperature() > 1.0) {
            return "Invalid configuration: temperature must be between 0 and 1";
        } else if (config.timeoutSeconds() <= 0) {
            return "Invalid configuration: timeout must be positive";
        }
        return "Valid configuration: " + config.modelName();
    }
    
    /**
     * Get configuration summary using record components.
     */
    public String getConfigSummary(ModelConfig config) {
        return String.format(Locale.US, """
            Model: %s
            Version: %s
            Max Tokens: %d
            Temperature: %.2f
            Streaming: %s
            Timeout: %d seconds
            """,
            config.modelName(),
            config.version(),
            config.maxTokens(),
            config.temperature(),
            config.streaming(),
            config.timeoutSeconds()
        );
    }

    /**
     * Get detailed configuration analysis using all record components.
     */
    public String getDetailedAnalysis(ModelConfig config) {
        String performance = config.maxTokens() > 4000 ? "high" : "standard";
        String precision = config.temperature() < 0.3 ? "high" : "balanced";
        String mode = config.streaming() ? "streaming" : "batch";
        String reliability = config.timeoutSeconds() > 60 ? "high" : "standard";
        
        return String.format(Locale.US, """
            Detailed Analysis for %s (v%s):
            - Performance: %s (tokens: %d)
            - Precision: %s (temperature: %.2f)
            - Mode: %s
            - Reliability: %s (timeout: %d seconds)
            """,
            config.modelName(),
            config.version(),
            performance,
            config.maxTokens(),
            precision,
            config.temperature(),
            mode,
            reliability,
            config.timeoutSeconds()
        );
    }
} 