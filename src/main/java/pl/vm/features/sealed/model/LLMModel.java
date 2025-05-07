package pl.vm.features.sealed.model;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 * Base sealed class representing different types of Large Language Models.
 * This class is sealed to ensure that only specific LLM implementations can be created.
 */
public sealed abstract class LLMModel permits ChatGPTModel, GeminiModel, ClaudeModel, DeepSeekModel {
    private final String version;
    private final int maxTokens;
    private final double temperature;
    private static final DecimalFormat DECIMAL_FORMAT;

    static {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
        DECIMAL_FORMAT = new DecimalFormat("0.00", symbols);
    }

    protected LLMModel(String version, int maxTokens, double temperature) {
        this.version = version;
        this.maxTokens = maxTokens;
        this.temperature = temperature;
    }

    public String getVersion() {
        return version;
    }

    public int getMaxTokens() {
        return maxTokens;
    }

    public double getTemperature() {
        return temperature;
    }

    /**
     * Get the name of the model.
     */
    public abstract String getModelName();

    /**
     * Process input data and return a response.
     * Uses Java's String.formatted() method to create a formatted string with model details.
     */
    public String process(String input) {
        return "%s maxTokens=%d, temperature=%s".formatted(getModelName(), maxTokens, DECIMAL_FORMAT.format(temperature));
    }
} 