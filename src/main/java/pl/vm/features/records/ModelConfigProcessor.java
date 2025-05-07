package pl.vm.features.records;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * Demonstrates the use of Record Patterns in Java.
 * This class shows how to use pattern matching with records to extract and process their components.
 * 
 * Key aspects demonstrated:
 * 1. Record pattern matching in switch expressions
 * 2. Nested record patterns
 * 3. Record patterns with guards
 * 4. Record patterns in stream operations
 * 5. Unnamed pattern variables (JEP 443)
 * 
 * @see <a href="https://openjdk.org/jeps/440">JEP 440: Record Patterns</a>
 * @see <a href="https://openjdk.org/jeps/443">JEP 443: Unnamed Patterns and Variables</a>
 */
public class ModelConfigProcessor {
    
    /**
     * Process a model configuration using record patterns in switch expression.
     */
    public String processConfig(ModelConfig config) {
        return switch (config) {
            case ModelConfig(String name, String version, int tokens, _, boolean stream, _) 
                when stream -> "Streaming configuration: " + name + " (v" + version + ") with " + tokens + " tokens";
            case ModelConfig(String name, String version, int tokens, _, _, _) 
                when tokens > 4000 -> "Large context configuration: " + name + " (v" + version + ") with " + tokens + " tokens";
            case ModelConfig(String name, String version, _, double temp, _, _) 
                when temp < 0.3 -> "Precise configuration: " + name + " (v" + version + ") with temperature " + temp;
            case ModelConfig defaultConfig -> "Standard configuration: " + defaultConfig.modelName() + " (v" + defaultConfig.version() + ")";
        };
    }
    
    /**
     * Process a list of configurations using record patterns in stream operations.
     */
    public List<String> processConfigs(List<ModelConfig> configs) {
        return configs.stream()
            .map(config -> switch (config) {
                case ModelConfig(var name, _, var tokens, _, boolean stream, _) 
                    when stream -> "Streaming: " + name + " (" + tokens + " tokens)";
                case ModelConfig(var name, _, var tokens, _, _, _) 
                    when tokens > 4000 -> "Large: " + name + " (" + tokens + " tokens)";
                case ModelConfig(var name, _, _, var temp, _, _) 
                    when temp < 0.3 -> "Precise: " + name + " (temp: " + temp + ")";
                case ModelConfig defaultConfig -> "Standard: " + defaultConfig.modelName();
            })
            .collect(Collectors.toList());
    }
    
    /**
     * Validate a configuration using record patterns.
     */
    public String validateConfig(ModelConfig config) {
        return switch (config) {
            case ModelConfig(_, _, int tokens, _, _, _) 
                when tokens <= 0 -> "Invalid configuration: tokens must be positive";
            case ModelConfig(_, _, _, double temp, _, _) 
                when temp < 0.0 || temp > 1.0 -> "Invalid configuration: temperature must be between 0 and 1";
            case ModelConfig(_, _, _, _, _, int timeout) 
                when timeout <= 0 -> "Invalid configuration: timeout must be positive";
            case ModelConfig defaultConfig -> "Valid configuration: " + defaultConfig.modelName();
        };
    }
    
    /**
     * Get configuration summary using record patterns.
     */
    public String getConfigSummary(ModelConfig config) {
        return switch (config) {
            case ModelConfig(var name, var version, var tokens, var temp, var stream, var timeout) -> 
                String.format(Locale.US, """
                    Model: %s
                    Version: %s
                    Max Tokens: %d
                    Temperature: %.2f
                    Streaming: %s
                    Timeout: %d seconds
                    """, name, version, tokens, temp, stream, timeout);
        };
    }

    /**
     * Get detailed configuration analysis using all properties.
     * Demonstrates a case where all record properties are used in pattern matching.
     */
    public String getDetailedAnalysis(ModelConfig config) {
        return switch (config) {
            case ModelConfig(String name, String version, int tokens, double temp, boolean stream, int timeout) -> {
                String performance = tokens > 4000 ? "high" : "standard";
                String precision = temp < 0.3 ? "high" : "balanced";
                String mode = stream ? "streaming" : "batch";
                String reliability = timeout > 60 ? "high" : "standard";
                
                yield String.format(Locale.US, """
                    Detailed Analysis for %s (v%s):
                    - Performance: %s (tokens: %d)
                    - Precision: %s (temperature: %.2f)
                    - Mode: %s
                    - Reliability: %s (timeout: %d seconds)
                    """, name, version, performance, tokens, precision, temp, mode, reliability, timeout);
            }
        };
    }
} 