package pl.vm.features.patternmatching;

import pl.vm.features.sealed.model.ChatGPTModel;
import pl.vm.features.sealed.model.ClaudeModel;
import pl.vm.features.sealed.model.DeepSeekModel;
import pl.vm.features.sealed.model.GeminiModel;
import pl.vm.features.sealed.model.LLMModel;

/**
 * Demonstrates the use of Pattern Matching for switch expressions in Java.
 * This feature allows for more concise and type-safe handling of different object types.
 * 
 * Key aspects demonstrated:
 * 1. Type patterns in switch expressions
 * 2. Pattern matching with sealed classes
 * 3. Exhaustiveness checking
 * 4. Guarded patterns
 * 
 * @see <a href="https://openjdk.org/jeps/441">JEP 441: Pattern Matching for switch</a>
 */
public class ModelProcessor {
    
    /**
     * Process a model using pattern matching in switch expression.
     * Demonstrates how to handle different model types in a type-safe way.
     */
    public String processModel(LLMModel model) {
        return switch (model) {
            case ChatGPTModel chatGPT -> {
                String variant = chatGPT.getModelVariant();
                yield "Processing ChatGPT model with variant: " + variant;
            }
            case GeminiModel gemini -> {
                String size = gemini.getModelSize();
                yield "Processing Gemini model with size: " + size;
            }
            case ClaudeModel claude -> {
                String variant = claude.getModelVariant();
                yield "Processing Claude model with variant: " + variant;
            }
            case DeepSeekModel deepSeek -> {
                String variant = deepSeek.getModelVariant();
                yield "Processing DeepSeek model with variant: " + variant;
            }
        };
    }
    
    /**
     * Process a model with additional conditions using guarded patterns.
     * Demonstrates how to combine pattern matching with additional conditions.
     */
    public String processModelWithConditions(LLMModel model) {
        return switch (model) {
            case ChatGPTModel chatGPT when chatGPT.getModelVariant().equals("gpt-4") -> 
                "Processing advanced ChatGPT model (GPT-4)";
            case ChatGPTModel chatGPT when chatGPT.getModelVariant().equals("gpt-3.5-turbo") -> 
                "Processing standard ChatGPT model (GPT-3.5)";
            case GeminiModel gemini when gemini.getModelSize().equals("pro") -> 
                "Processing professional Gemini model";
            case GeminiModel gemini when gemini.getModelSize().equals("ultra") -> 
                "Processing ultra Gemini model";
            case ClaudeModel claude when claude.getModelVariant().equals("opus") -> 
                "Processing Claude Opus model";
            case ClaudeModel claude when claude.getModelVariant().equals("sonnet") -> 
                "Processing Claude Sonnet model";
            case DeepSeekModel deepSeek when deepSeek.getModelVariant().equals("coder") -> 
                "Processing DeepSeek Coder model";
            case DeepSeekModel deepSeek when deepSeek.getModelVariant().equals("chat") -> 
                "Processing DeepSeek Chat model";
            default -> "Processing unknown model type";
        };
    }
    
    /**
     * Get model capabilities using pattern matching.
     * Demonstrates how to extract and use model-specific properties.
     */
    public String getModelCapabilities(LLMModel model) {
        return switch (model) {
            case ChatGPTModel chatGPT -> 
                "ChatGPT capabilities: " + (chatGPT.getModelVariant().equals("gpt-4") ? 
                    "Advanced reasoning, code generation, complex problem solving" : 
                    "General purpose, efficient processing");
            case GeminiModel gemini -> 
                "Gemini capabilities: " + (gemini.getModelSize().equals("pro") ? 
                    "Professional grade, balanced performance" : 
                    "Ultra performance, maximum capabilities");
            case ClaudeModel claude -> 
                "Claude capabilities: " + (claude.getModelVariant().equals("opus") ? 
                    "Advanced reasoning, complex tasks" : 
                    "Efficient processing, general purpose");
            case DeepSeekModel deepSeek -> 
                "DeepSeek capabilities: " + (deepSeek.getModelVariant().equals("coder") ? 
                    "Specialized in code generation and analysis" : 
                    "General purpose chat and assistance");
        };
    }
} 