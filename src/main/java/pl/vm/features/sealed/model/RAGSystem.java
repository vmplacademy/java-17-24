package pl.vm.features.sealed.model;

import java.util.List;

/**
 * Represents a Retrieval-Augmented Generation (RAG) system.
 * This is not a model type, but rather an architecture that combines an LLM with a knowledge base.
 */
public class RAGSystem {
    private final LLMModel llmModel;
    private final String knowledgeBaseId;
    private final int topKResults;
    private final List<String> allowedSources;

    public RAGSystem(LLMModel llmModel, String knowledgeBaseId, int topKResults, List<String> allowedSources) {
        this.llmModel = llmModel;
        this.knowledgeBaseId = knowledgeBaseId;
        this.topKResults = topKResults;
        this.allowedSources = allowedSources;
    }

    public LLMModel getLlmModel() {
        return llmModel;
    }

    public String getKnowledgeBaseId() {
        return knowledgeBaseId;
    }

    public int getTopKResults() {
        return topKResults;
    }

    public List<String> getAllowedSources() {
        return allowedSources;
    }

    public String process(String input) {
        // Simulated RAG processing
        // 1. Retrieve relevant documents from knowledge base
        String retrievedDocs = String.format("Retrieved %d documents from knowledge base %s from sources: %s",
                topKResults, knowledgeBaseId, String.join(", ", allowedSources));
        
        // 2. Augment the input with retrieved documents
        String augmentedInput = String.format("%s\n\nContext from knowledge base:\n%s", input, retrievedDocs);
        
        // 3. Process the augmented input with the LLM
        return llmModel.process(augmentedInput);
    }
} 