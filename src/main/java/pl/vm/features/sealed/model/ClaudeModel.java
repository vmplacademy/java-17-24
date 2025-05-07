package pl.vm.features.sealed.model;

/**
 * Implementation of LLMModel for Anthropic's Claude.
 */
public final class ClaudeModel extends LLMModel {
    private final String modelVariant; // e.g., "opus", "sonnet", "haiku"

    public ClaudeModel(String version, String modelVariant, int maxTokens, double temperature) {
        super(version, maxTokens, temperature);
        this.modelVariant = modelVariant;
    }

    public String getModelVariant() {
        return modelVariant;
    }

    @Override
    public String getModelName() {
        return "Claude-" + modelVariant;
    }
} 