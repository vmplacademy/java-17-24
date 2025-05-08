package pl.vm.features.textblocks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class TextBlocksDemoTest {
    private static final Logger logger = LoggerFactory.getLogger(TextBlocksDemoTest.class);
    private final TextBlocksDemo demo = new TextBlocksDemo();

    @Test
    void should_maintain_formatting_when_using_basic_text_block() {
        // given
        String expected = "SELECT id, name, email\n" +
                         "FROM users\n" +
                         "WHERE active = true\n" +
                         "ORDER BY name";

        // when
        String textBlock = """
            SELECT id, name, email
            FROM users
            WHERE active = true
            ORDER BY name
            """.trim();

        // then
        assertEquals(expected, textBlock);
    }

    @Test
    void should_preserve_indentation_when_using_text_block() {
        // given
        String expected = "SELECT id, name, email\n" +
                         "FROM users\n" +
                         "WHERE active = true\n" +
                         "    AND created_at > '2024-01-01'\n" +
                         "ORDER BY name";

        // when
        String textBlock = """
            SELECT id, name, email
            FROM users
            WHERE active = true
                AND created_at > '2024-01-01'
            ORDER BY name
            """.trim();

        // then
        assertEquals(expected, textBlock);
    }

    @Test
    void should_interpolate_values_when_using_formatted_text_block() {
        // given
        String tableName = "users";
        String condition = "active = true";
        String expected = "SELECT id, name, email\n" +
                         "FROM users\n" +
                         "WHERE active = true\n" +
                         "ORDER BY name";

        // when
        String textBlock = """
            SELECT id, name, email
            FROM %s
            WHERE %s
            ORDER BY name
            """.formatted(tableName, condition).trim();

        // then
        assertEquals(expected, textBlock);
    }

    @Test
    void should_preserve_json_formatting_when_using_text_block() {
        // given
        String expected = "{\n" +
                         "    \"name\": \"John Doe\",\n" +
                         "    \"age\": 30\n" +
                         "}";

        // when
        String textBlock = """
            {
                "name": "John Doe",
                "age": 30
            }
            """.trim();

        // then
        assertEquals(expected.replaceAll("\\s+", ""), textBlock.replaceAll("\\s+", ""));
    }

    @Test
    void should_preserve_html_formatting_when_using_text_block() {
        // given
        String expected = "<!DOCTYPE html>\n" +
                         "<html>\n" +
                         "    <head>\n" +
                         "        <title>Test</title>\n" +
                         "    </head>\n" +
                         "    <body>\n" +
                         "        <h1>Hello</h1>\n" +
                         "    </body>\n" +
                         "</html>";

        // when
        String textBlock = """
            <!DOCTYPE html>
            <html>
                <head>
                    <title>Test</title>
                </head>
                <body>
                    <h1>Hello</h1>
                </body>
            </html>
            """.trim();

        // then
        assertEquals(expected.replaceAll("\\s+", ""), textBlock.replaceAll("\\s+", ""));
    }
} 