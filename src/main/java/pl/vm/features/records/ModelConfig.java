package pl.vm.features.records;

/**
 * Record representing configuration for an LLM model.
 * Demonstrates the use of records in Java for immutable data containers.
 * 
 * @see <a href="https://openjdk.org/jeps/440">JEP 440: Record Patterns</a>
 */
public record ModelConfig(
    String modelName,
    String version,
    int maxTokens,
    double temperature,
    boolean streaming,
    int timeoutSeconds
) {
    /**
     * Creates a default configuration with reasonable values.
     */
    public static ModelConfig createDefault() {
        return new ModelConfig(
            "default",
            "1.0",
            2048,
            0.7,
            false,
            30
        );
    }

    /**
     * Creates a configuration optimized for streaming.
     */
    public static ModelConfig createStreamingConfig() {
        return new ModelConfig(
            "streaming",
            "1.0",
            4096,
            0.8,
            true,
            60
        );
    }

    /**
     * Creates a configuration optimized for code generation.
     */
    public static ModelConfig createCodeGenConfig() {
        return new ModelConfig(
            "code-gen",
            "1.0",
            8192,
            0.2,
            false,
            120
        );
    }
} 