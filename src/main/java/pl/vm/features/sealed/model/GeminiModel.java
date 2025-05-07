package pl.vm.features.sealed.model;

/**
 * Implementation of LLMModel for Google's Gemini.
 */
public final class GeminiModel extends LLMModel {
    private final String modelSize; // e.g., "pro", "ultra"

    public GeminiModel(String version, String modelSize, int maxTokens, double temperature) {
        super(version, maxTokens, temperature);
        this.modelSize = modelSize;
    }

    public String getModelSize() {
        return modelSize;
    }

    @Override
    public String getModelName() {
        return "Gemini-" + modelSize;
    }
} 