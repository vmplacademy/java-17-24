package pl.vm.features.sealed.model;

import java.util.List;

/**
 * Represents a Retrieval-Augmented Generation (RAG) model.
 * This class represents a model that combines LLM with knowledge base retrieval.
 */
public final class RAGModel {
    private final String name;
    private final String version;
    private final String knowledgeBaseId;
    private final int topKResults;
    private final List<String> allowedSources;

    public RAGModel(String name, String version, String knowledgeBaseId, int topKResults, List<String> allowedSources) {
        this.name = name;
        this.version = version;
        this.knowledgeBaseId = knowledgeBaseId;
        this.topKResults = topKResults;
        this.allowedSources = allowedSources;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
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
        return String.format("RAG %s (v%s) processed input using knowledge base %s, " +
                        "retrieved top %d results from sources: %s. Input: %s",
                name, version, knowledgeBaseId, topKResults,
                String.join(", ", allowedSources), input);
    }
} 