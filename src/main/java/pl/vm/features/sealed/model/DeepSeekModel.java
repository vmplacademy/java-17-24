package pl.vm.features.sealed.model;

/**
 * Implementation of LLMModel for DeepSeek.
 */
public final class DeepSeekModel extends LLMModel {
    private final String modelVariant; // e.g., "coder", "chat"

    public DeepSeekModel(String version, String modelVariant, int maxTokens, double temperature) {
        super(version, maxTokens, temperature);
        this.modelVariant = modelVariant;
    }

    public String getModelVariant() {
        return modelVariant;
    }

    @Override
    public String getModelName() {
        return "DeepSeek-" + modelVariant;
    }
} 