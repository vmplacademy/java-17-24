package pl.vm.features.sealed.model;

/**
 * Implementation of LLMModel for OpenAI's ChatGPT.
 */
public final class ChatGPTModel extends LLMModel {
    private final String modelVariant; // e.g., "gpt-4", "gpt-3.5-turbo"

    public ChatGPTModel(String version, String modelVariant, int maxTokens, double temperature) {
        super(version, maxTokens, temperature);
        this.modelVariant = modelVariant;
    }

    public String getModelVariant() {
        return modelVariant;
    }

    @Override
    public String getModelName() {
        return "ChatGPT-" + modelVariant;
    }
} 