package pl.vm.features.patternmatching;

import pl.vm.features.sealed.model.ChatGPTModel;
import pl.vm.features.sealed.model.ClaudeModel;
import pl.vm.features.sealed.model.DeepSeekModel;
import pl.vm.features.sealed.model.GeminiModel;
import pl.vm.features.sealed.model.LLMModel;

/**
 * Demonstrates the use of Pattern Matching for instanceof in Java.
 * This feature allows for more concise and type-safe handling of different object types.
 * 
 * Key aspects demonstrated:
 * 1. Pattern matching with instanceof
 * 2. Type patterns with sealed classes
 * 3. Multiple pattern matching conditions
 * 
 * @see <a href="https://openjdk.org/jeps/394">JEP 394: Pattern Matching for instanceof</a>
 */
public class ModelProcessor {
    
    /**
     * Process a model using pattern matching with instanceof.
     * Demonstrates how to handle different model types in a type-safe way.
     */
    public String processModel(LLMModel model) {
        if (model instanceof ChatGPTModel chatGPT) {
            String variant = chatGPT.getModelVariant();
            return "Processing ChatGPT model with variant: " + variant;
        } else if (model instanceof GeminiModel gemini) {
            String size = gemini.getModelSize();
            return "Processing Gemini model with size: " + size;
        } else if (model instanceof ClaudeModel claude) {
            String variant = claude.getModelVariant();
            return "Processing Claude model with variant: " + variant;
        } else if (model instanceof DeepSeekModel deepSeek) {
            String variant = deepSeek.getModelVariant();
            return "Processing DeepSeek model with variant: " + variant;
        }
        return "Unknown model type";
    }
    
    /**
     * Process a model with additional conditions using pattern matching.
     * Demonstrates how to combine pattern matching with additional conditions.
     */
    public String processModelWithConditions(LLMModel model) {
        if (model instanceof ChatGPTModel chatGPT) {
            if (chatGPT.getModelVariant().equals("gpt-4")) {
                return "Processing advanced ChatGPT model (GPT-4)";
            } else if (chatGPT.getModelVariant().equals("gpt-3.5-turbo")) {
                return "Processing standard ChatGPT model (GPT-3.5)";
            }
        } else if (model instanceof GeminiModel gemini) {
            if (gemini.getModelSize().equals("pro")) {
                return "Processing professional Gemini model";
            } else if (gemini.getModelSize().equals("ultra")) {
                return "Processing ultra Gemini model";
            }
        } else if (model instanceof ClaudeModel claude) {
            if (claude.getModelVariant().equals("opus")) {
                return "Processing Claude Opus model";
            } else if (claude.getModelVariant().equals("sonnet")) {
                return "Processing Claude Sonnet model";
            }
        } else if (model instanceof DeepSeekModel deepSeek) {
            if (deepSeek.getModelVariant().equals("coder")) {
                return "Processing DeepSeek Coder model";
            } else if (deepSeek.getModelVariant().equals("chat")) {
                return "Processing DeepSeek Chat model";
            }
        }
        return "Processing unknown model type";
    }
    
    /**
     * Get model capabilities using pattern matching.
     * Demonstrates how to extract and use model-specific properties.
     */
    public String getModelCapabilities(LLMModel model) {
        if (model instanceof ChatGPTModel chatGPT) {
            return "ChatGPT capabilities: " + (chatGPT.getModelVariant().equals("gpt-4") ? 
                "Advanced reasoning, code generation, complex problem solving" : 
                "General purpose, efficient processing");
        } else if (model instanceof GeminiModel gemini) {
            return "Gemini capabilities: " + (gemini.getModelSize().equals("pro") ? 
                "Professional grade, balanced performance" : 
                "Ultra performance, maximum capabilities");
        } else if (model instanceof ClaudeModel claude) {
            return "Claude capabilities: " + (claude.getModelVariant().equals("opus") ? 
                "Advanced reasoning, complex tasks" : 
                "Efficient processing, general purpose");
        } else if (model instanceof DeepSeekModel deepSeek) {
            return "DeepSeek capabilities: " + (deepSeek.getModelVariant().equals("coder") ? 
                "Specialized in code generation and analysis" : 
                "General purpose chat and assistance");
        }
        return "Unknown model capabilities";
    }
} 