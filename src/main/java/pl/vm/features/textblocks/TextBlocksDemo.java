package pl.vm.features.textblocks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Demonstrates the use of Text Blocks introduced in Java 15.
 * Text Blocks provide a way to write multi-line string literals in a more readable way.
 */
public class TextBlocksDemo {
    private static final Logger logger = LoggerFactory.getLogger(TextBlocksDemo.class);

    /**
     * Demonstrates basic text block usage
     */
    public void demonstrateBasicTextBlocks() {
        // Traditional string concatenation
        String traditional = "SELECT id, name, email\n" +
                           "FROM users\n" +
                           "WHERE active = true\n" +
                           "ORDER BY name";

        // Using text block
        String textBlock = """
            SELECT id, name, email
            FROM users
            WHERE active = true
            ORDER BY name
            """;

        logger.info("Traditional string:\n{}", traditional);
        logger.info("Text block:\n{}", textBlock);
    }

    /**
     * Demonstrates text block with indentation
     */
    public void demonstrateIndentation() {
        String query = """
            SELECT id, name, email
            FROM users
            WHERE active = true
                AND created_at > '2024-01-01'
            ORDER BY name
            """;

        logger.info("Indented query:\n{}", query);
    }

    /**
     * Demonstrates text block with interpolation
     */
    public void demonstrateInterpolation() {
        String tableName = "users";
        String condition = "active = true";
        
        String query = """
            SELECT id, name, email
            FROM %s
            WHERE %s
            ORDER BY name
            """.formatted(tableName, condition);

        logger.info("Interpolated query:\n{}", query);
    }

    /**
     * Demonstrates text block with JSON
     */
    public void demonstrateJson() {
        String json = """
            {
                "name": "John Doe",
                "age": 30,
                "email": "john@example.com",
                "address": {
                    "street": "123 Main St",
                    "city": "New York",
                    "country": "USA"
                },
                "hobbies": [
                    "reading",
                    "gaming",
                    "coding"
                ]
            }
            """;

        logger.info("JSON example:\n{}", json);
    }

    /**
     * Demonstrates text block with HTML
     */
    public void demonstrateHtml() {
        String html = """
            <!DOCTYPE html>
            <html>
                <head>
                    <title>Example Page</title>
                </head>
                <body>
                    <h1>Welcome</h1>
                    <p>This is a paragraph with <strong>bold</strong> text.</p>
                    <ul>
                        <li>Item 1</li>
                        <li>Item 2</li>
                        <li>Item 3</li>
                    </ul>
                </body>
            </html>
            """;

        logger.info("HTML example:\n{}", html);
    }

    public static void main(String[] args) {
        TextBlocksDemo demo = new TextBlocksDemo();
        
        logger.info("=== Basic Text Blocks ===");
        demo.demonstrateBasicTextBlocks();
        
        logger.info("\n=== Text Blocks with Indentation ===");
        demo.demonstrateIndentation();
        
        logger.info("\n=== Text Blocks with Interpolation ===");
        demo.demonstrateInterpolation();
        
        logger.info("\n=== Text Blocks with JSON ===");
        demo.demonstrateJson();
        
        logger.info("\n=== Text Blocks with HTML ===");
        demo.demonstrateHtml();
    }
} 